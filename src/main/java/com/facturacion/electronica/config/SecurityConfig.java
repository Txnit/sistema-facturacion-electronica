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
                .requestMatchers("/h2-console/**").hasRole("ADMIN") // Solo admin puede ver H2
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated() // REQUIERE AUTENTICACIÓN PARA TODO
            )
            
            // Usar solo HTTP Basic Auth (sin formulario para evitar loops)
            .httpBasic(basic -> {})
            
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
}