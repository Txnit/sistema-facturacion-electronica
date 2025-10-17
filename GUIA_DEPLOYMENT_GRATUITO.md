# 🌐 Guía para Compartir tu Sistema en Desarrollo - GRATIS

## 🎯 Opciones Gratuitas para Deployment

### 1. 🚀 **Railway** (RECOMENDADO)
- ✅ **Gratuito**: 500 horas/mes + $5 crédito mensual
- ✅ **Fácil**: Deploy directo desde GitHub
- ✅ **Java/Spring Boot**: Soporte nativo
- ✅ **Base de datos**: PostgreSQL gratuita incluida

### 2. 🔷 **Render** 
- ✅ **Gratuito**: Plan libre permanente
- ✅ **Auto-sleep**: Se duerme después de 15 min inactividad
- ✅ **Java**: Soporte completo
- ✅ **PostgreSQL**: Base de datos gratuita

### 3. ☁️ **Heroku** (Alternativo)
- ⚠️ **Limitado**: Ya no es completamente gratuito
- ✅ **Conocido**: Muy popular
- ✅ **Ecosistema**: Muchos add-ons

### 4. 🌊 **Fly.io**
- ✅ **Gratuito**: Allowance mensual
- ✅ **Global**: Deploy en múltiples regiones
- ✅ **Docker**: Usa contenedores

---

## 🚀 OPCIÓN RECOMENDADA: Railway

### ¿Por qué Railway?
- 🎁 **500 horas gratuitas** cada mes
- 💰 **$5 USD crédito** mensual gratis
- 🔄 **Auto-deploy** desde GitHub
- 🗄️ **PostgreSQL** incluida
- 🌐 **URL pública** automática
- 🔐 **Variables de entorno** fáciles

### Pasos para Deploy en Railway:

#### 1. Preparar el Código
```bash
# Agregar archivo Procfile
echo "web: java -jar target/sistema-facturacion-electronica-1.0.0.jar" > Procfile

# Agregar configuración para producción
# application-prod.yml
```

#### 2. Subir a GitHub
```bash
git init
git add .
git commit -m "Sistema listo para Railway"
git push origin main
```

#### 3. Deploy en Railway
1. Ir a [railway.app](https://railway.app)
2. Conectar con GitHub
3. Seleccionar tu repositorio
4. ¡Deploy automático!

---

## 🛠 Configuración para Producción

### Archivo `application-prod.yml`:
```yaml
server:
  port: ${PORT:8080}

spring:
  datasource:
    url: ${DATABASE_URL}
    username: ${DB_USER}
    password: ${DB_PASSWORD}
  
  jpa:
    database-platform: org.hibernate.dialect.PostgreSQLDialect
    hibernate:
      ddl-auto: update

logging:
  level:
    com.facturacion.electronica: INFO
```

### Variables de Entorno en Railway:
```
PORT=8080
SPRING_PROFILES_ACTIVE=prod
DATABASE_URL=postgresql://...
```

---

## 🔐 Sistema de Usuarios y Contraseñas

### Opción 1: Usuario Demo Fijo
```java
// En SecurityConfig.java
@Bean
public UserDetailsService userDetailsService() {
    UserDetails user = User.builder()
        .username("demo")
        .password(passwordEncoder().encode("demo123"))
        .roles("USER")
        .build();
    
    UserDetails admin = User.builder()
        .username("admin")
        .password(passwordEncoder().encode("admin123"))
        .roles("ADMIN")
        .build();
        
    return new InMemoryUserDetailsManager(user, admin);
}
```

### Opción 2: Variables de Entorno
```java
@Value("${app.demo.username:demo}")
private String demoUsername;

@Value("${app.demo.password:demo123}")
private String demoPassword;
```

---

## 📋 Checklist Pre-Deploy

### ✅ Preparación del Código:
- [ ] Perfil de producción configurado
- [ ] Base de datos PostgreSQL configurada
- [ ] Puerto dinámico configurado
- [ ] Logs optimizados
- [ ] Variables de entorno configuradas

### ✅ Archivos Necesarios:
- [ ] `Procfile` creado
- [ ] `application-prod.yml` configurado
- [ ] `pom.xml` optimizado para producción
- [ ] `.gitignore` actualizado

### ✅ Seguridad:
- [ ] Usuarios demo configurados
- [ ] Contraseñas seguras
- [ ] Variables sensibles en entorno
- [ ] CORS configurado para dominio público

---

## 🎯 Resultado Final

### Lo que obtienes:
✅ **URL pública**: `https://tu-app.up.railway.app`
✅ **Acceso 24/7**: Disponible desde cualquier lugar
✅ **Usuarios demo**: Para que otros prueben
✅ **Base de datos**: PostgreSQL en la nube
✅ **HTTPS**: Certificado SSL automático
✅ **Logs**: Monitoreo en tiempo real

### Para compartir:
```
🌐 Sistema de Facturación Electrónica
📱 URL: https://tu-sistema.up.railway.app
👤 Usuario: demo
🔑 Contraseña: demo123

👨‍💼 Admin:
👤 Usuario: admin  
🔑 Contraseña: admin123
```

---

## 💡 Tips Importantes

### 🔋 Para Maximizar Tiempo Gratuito:
- Usa **sleep mode** cuando no uses la app
- Optimiza el **uso de memoria**
- Configura **auto-scaling** conservador

### 🔒 Seguridad Básica:
- Cambia contraseñas por defecto
- Usa **variables de entorno** para secretos
- Implementa **rate limiting** básico

### 📊 Monitoreo:
- Railway dashboard para **métricas**
- Logs en tiempo real
- Alertas por email

---

## 🚦 Próximos Pasos

1. **Configurar** archivos de producción
2. **Subir** código a GitHub  
3. **Conectar** Railway con repositorio
4. **Configurar** variables de entorno
5. **Probar** URL pública
6. **Compartir** credenciales con testers

¿Quieres que configuremos Railway paso a paso? 🚀