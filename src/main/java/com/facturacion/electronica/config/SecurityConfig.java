package com.facturacion.electronica.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;

/**
 * Configuración de seguridad para el sistema de facturación electrónica
 * Incluye usuarios demo para testing en producción
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${app.demo.username:demo}")
    private String demoUsername;
    
    @Value("${app.demo.password:demo123}")
    private String demoPassword;
    
    @Value("${app.admin.username:admin}")
    private String adminUsername;
    
    @Value("${app.admin.password:admin123}")
    private String adminPassword;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Deshabilitar CSRF para facilitar las pruebas de API
            .csrf(AbstractHttpConfigurer::disable)
            
            // Configurar autenticación básica simple
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login.html", "/login").permitAll() // Permitir acceso a login
                .requestMatchers("/h2-console/**").hasRole("ADMIN") // Solo admin puede ver H2
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated() // REQUIERE AUTENTICACIÓN PARA TODO
            )
            
            // Usar solo HTTP Basic Auth con página de login personalizada
            .httpBasic(basic -> basic
                .authenticationEntryPoint((request, response, authException) -> {
                    response.sendRedirect("/login.html?expired=true");
                })
            )
            
            // Configuración de logout
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            
            // Configuración de sesión con timeout
            .sessionManagement(session -> session
                .maximumSessions(1) // Una sesión por usuario
                .maxSessionsPreventsLogin(false) // Nueva sesión invalida la anterior
                .sessionRegistry(sessionRegistry())
            )
            
            // Configuración para H2 Console
            .headers(headers -> headers
                .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
            );

        return http.build();
    }
    
    @Bean
    public UserDetailsService userDetailsService() {
        // Usuario demo para pruebas
        UserDetails demoUser = User.builder()
            .username(demoUsername)
            .password(passwordEncoder().encode(demoPassword))
            .roles("USER")
            .build();
        
        // Usuario administrador
        UserDetails adminUser = User.builder()
            .username(adminUsername)
            .password(passwordEncoder().encode(adminPassword))
            .roles("USER", "ADMIN")
            .build();
        
        return new InMemoryUserDetailsManager(demoUser, adminUser);
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
}