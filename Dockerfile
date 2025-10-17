# Usar imagen base de Java 17
FROM openjdk:17-jdk-slim

# Establecer directorio de trabajo
WORKDIR /app

# Copiar archivos del proyecto
COPY pom.xml .
COPY src ./src

# Instalar Maven
RUN apt-get update && apt-get install -y maven

# Compilar la aplicación
RUN mvn clean package -DskipTests

# Exponer el puerto 8080
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "target/sistema-facturacion-electronica-1.0.0.jar"]