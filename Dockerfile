# Dockerfile simple para Render.com
FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

# Instalar Maven directamente
RUN apt-get update && \
    apt-get install -y maven && \
    rm -rf /var/lib/apt/lists/*

# Copiar archivos del proyecto
COPY pom.xml .
COPY src ./src

# Compilar con Maven directo
RUN mvn clean package -DskipTests

# Exponer puerto
EXPOSE 8080

# Variables de entorno
ENV SPRING_PROFILES_ACTIVE=prod
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Comando de inicio
CMD ["sh", "-c", "java $JAVA_OPTS -Dserver.port=${PORT:-8080} -Dspring.profiles.active=prod -jar target/sistema-facturacion-electronica-1.0.0.jar"]