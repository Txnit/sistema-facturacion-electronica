# Sistema de Facturación Electrónica

## Descripción
Sistema completo de facturación electrónica para integración con SUNAT, desarrollado en Java con Spring Boot.

## Características Principales

### ✅ Autonomía Operacional
- Sistema independiente con gestión completa de comprobantes
- Procesamiento local y envío automático a SUNAT

### ✅ Manejo Centralizado
- Gestión unificada de todos los Comprobantes de Pago Electrónicos (CdPE)
- Control centralizado del estado de envíos a SUNAT

### ✅ Generación de Comprobantes
- Facturas electrónicas
- Boletas de venta
- Notas de crédito y débito
- Operaciones al contado conforme a normativa SUNAT

### ✅ Firma Digital
- Firma de documentos digitales con certificados válidos
- Validación de integridad de documentos

## Tecnologías Utilizadas

- **Java 17** - Lenguaje principal
- **Spring Boot 3.1** - Framework de aplicación
- **Spring Data JPA** - Persistencia de datos
- **Spring Security** - Seguridad
- **PostgreSQL/H2** - Base de datos
- **BouncyCastle** - Firma digital
- **OpenAPI 3** - Documentación de API

## Estructura del Proyecto

```
src/
├── main/
│   ├── java/com/facturacion/electronica/
│   │   ├── config/          # Configuraciones
│   │   ├── controller/      # Controladores REST
│   │   ├── entity/          # Entidades JPA
│   │   ├── service/         # Servicios de negocio
│   │   ├── repository/      # Repositorios de datos
│   │   ├── dto/             # DTOs para transferencia de datos
│   │   ├── util/            # Utilidades
│   │   └── sunat/           # Integración SUNAT
│   └── resources/
│       ├── application.yml  # Configuración de aplicación
│       └── templates/       # Plantillas XML
└── test/                    # Pruebas unitarias
```

## Instalación y Configuración

### Prerrequisitos
- Java 17 o superior
- Maven 3.6+
- PostgreSQL (opcional, incluye H2 para desarrollo)

### Instalación
```bash
# Clonar el proyecto
git clone <repository-url>
cd sistema-facturacion-electronica

# Compilar el proyecto
mvn clean compile

# Ejecutar pruebas
mvn test

# Ejecutar la aplicación
mvn spring-boot:run
```

### Configuración
1. Configurar base de datos en `application.yml`
2. Configurar certificados para firma digital
3. Configurar credenciales SUNAT

## API Documentation
Una vez ejecutada la aplicación, la documentación de la API estará disponible en:
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

## Endpoints Principales

- `POST /api/comprobantes` - Crear comprobante
- `GET /api/comprobantes/{id}` - Obtener comprobante
- `POST /api/comprobantes/{id}/enviar` - Enviar a SUNAT
- `GET /api/comprobantes/{id}/estado` - Consultar estado SUNAT

## Licencia
Este proyecto está bajo la Licencia MIT.

## Soporte
Para soporte técnico, contactar al equipo de desarrollo.