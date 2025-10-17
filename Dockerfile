# Dockerfile optimizado para Render.com
FROM openjdk:21-jdk-slim as builder

WORKDIR /app

# Copiar Maven wrapper y pom.xml
COPY mvnw .
COPY pom.xml .
COPY .mvn/ .mvn/

# Hacer ejecutable
RUN chmod +x ./mvnw

# Descargar dependencias (para cache)
RUN ./mvnw dependency:go-offline -B

# Copiar código fuente
COPY src/ ./src/

# Compilar aplicación
RUN ./mvnw clean package -DskipTests

# Etapa de runtime
FROM openjdk:21-jre-slim

WORKDIR /app

# Variables de entorno para Render
ENV SPRING_PROFILES_ACTIVE=prod
ENV SERVER_PORT=8080

# Copiar JAR compilado
COPY --from=builder /app/target/sistema-facturacion-electronica-1.0.0.jar app.jar

# Exponer puerto
EXPOSE 8080

# Comando de inicio optimizado para Render
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dserver.port=${PORT:-8080}", "-jar", "app.jar"]