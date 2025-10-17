# Dockerfile simple para Render.com
FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

# Copiar todos los archivos del proyecto
COPY . .

# Hacer Maven wrapper ejecutable
RUN chmod +x ./mvnw

# Instalar dependencias y compilar
RUN ./mvnw clean package -DskipTests

# Exponer puerto
EXPOSE 8080

# Variables de entorno
ENV SPRING_PROFILES_ACTIVE=prod
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Comando de inicio
CMD ["java", "-Dserver.port=${PORT:-8080}", "-jar", "target/sistema-facturacion-electronica-1.0.0.jar"]