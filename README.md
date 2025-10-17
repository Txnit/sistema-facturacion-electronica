# 🧾 Sistema de Facturación Electrónica - SUNAT# Sistema de Facturación Electrónica



## 🚀 Inicio Rápido## Descripción

Sistema completo de facturación electrónica para integración con SUNAT, desarrollado en Java con Spring Boot.

### Opción 1: Acceso Directo (MÁS FÁCIL)

1. **Haz doble clic** en el archivo `INICIAR_SISTEMA.bat`## Características Principales

2. **Espera** que se abra automáticamente en tu navegador

3. **¡Listo!** Ya puedes usar el sistema### ✅ Autonomía Operacional

- Sistema independiente con gestión completa de comprobantes

### Opción 2: Acceso Manual- Procesamiento local y envío automático a SUNAT

1. **Abre** tu navegador web

2. **Ve a**: `http://localhost:8080/portal.html`### ✅ Manejo Centralizado

3. **Selecciona** la función que quieras usar- Gestión unificada de todos los Comprobantes de Pago Electrónicos (CdPE)

- Control centralizado del estado de envíos a SUNAT

## 🌐 Enlaces Principales

### ✅ Generación de Comprobantes

| Función | URL | Descripción |- Facturas electrónicas

|---------|-----|-------------|- Boletas de venta

| **Portal Principal** | `http://localhost:8080/portal.html` | Página de inicio con todas las opciones |- Notas de crédito y débito

| **Consulta RUC** | `http://localhost:8080/` | Formulario principal para consultar RUC |- Operaciones al contado conforme a normativa SUNAT

| **Diagnóstico** | `http://localhost:8080/diagnostico.html` | Verificar que todo funcione |

| **Prueba Simple** | `http://localhost:8080/prueba-simple.html` | Test básico de funcionalidad |### ✅ Firma Digital

- Firma de documentos digitales con certificados válidos

## 🔍 Cómo Consultar un RUC- Validación de integridad de documentos



### Método 1: Desde el Portal## Tecnologías Utilizadas

1. Abre `http://localhost:8080/portal.html`

2. Haz clic en "🚀 Consultar RUC"- **Java 17** - Lenguaje principal

3. Ingresa un RUC de 11 dígitos- **Spring Boot 3.1** - Framework de aplicación

4. Haz clic en "🔍 Consultar RUC"- **Spring Data JPA** - Persistencia de datos

5. Los campos se autocompletarán automáticamente- **Spring Security** - Seguridad

- **PostgreSQL/H2** - Base de datos

### Método 2: Consulta Directa- **BouncyCastle** - Firma digital

1. Ve a `http://localhost:8080/?ruc=20100070970`- **OpenAPI 3** - Documentación de API

2. El sistema consultará automáticamente ese RUC

## Estructura del Proyecto

### Método 3: Pruebas Rápidas

- **Rimac Seguros**: `http://localhost:8080/?ruc=20100070970````

- **BCP**: `http://localhost:8080/?ruc=20100017491`src/

- **Supermercados Peruanos**: `http://localhost:8080/?ruc=20100130204`├── main/

- **Saga Falabella**: `http://localhost:8080/?ruc=20100008805`│   ├── java/com/facturacion/electronica/

│   │   ├── config/          # Configuraciones

## 🧪 RUCs de Prueba│   │   ├── controller/      # Controladores REST

│   │   ├── entity/          # Entidades JPA

| RUC | Empresa | URL Directa |│   │   ├── service/         # Servicios de negocio

|-----|---------|-------------|│   │   ├── repository/      # Repositorios de datos

| `20100070970` | Rimac Seguros y Reaseguros | [Probar](http://localhost:8080/?ruc=20100070970) |│   │   ├── dto/             # DTOs para transferencia de datos

| `20100017491` | Banco de Crédito del Perú | [Probar](http://localhost:8080/?ruc=20100017491) |│   │   ├── util/            # Utilidades

| `20100130204` | Supermercados Peruanos | [Probar](http://localhost:8080/?ruc=20100130204) |│   │   └── sunat/           # Integración SUNAT

| `20100008805` | Saga Falabella | [Probar](http://localhost:8080/?ruc=20100008805) |│   └── resources/

| `20568849559` | Corporación Servicios S.A. | [Probar](http://localhost:8080/?ruc=20568849559) |│       ├── application.yml  # Configuración de aplicación

│       └── templates/       # Plantillas XML

## ⚡ Acceso Súper Rápido└── test/                    # Pruebas unitarias

```

### Para Consultar RUC Inmediatamente:

```## Instalación y Configuración

http://localhost:8080/?ruc=TU_RUC_AQUI

```### Prerrequisitos

Ejemplo: `http://localhost:8080/?ruc=20100070970`- Java 17 o superior

- Maven 3.6+

### Para Ir Directo a Gestión de Clientes:- PostgreSQL (opcional, incluye H2 para desarrollo)

```

http://localhost:8080/?accion=clientes### Instalación

``````bash

# Clonar el proyecto

### Para Ir Directo a Consulta RUC:git clone <repository-url>

```cd sistema-facturacion-electronica

http://localhost:8080/?accion=consulta-ruc

```# Compilar el proyecto

mvn clean compile

## 🔧 Solución de Problemas

# Ejecutar pruebas

### Si no funciona:mvn test

1. **Verifica** que veas `http://localhost:8080` en la URL (NO `file://`)

2. **Ejecuta** `INICIAR_SISTEMA.bat` para reiniciar# Ejecutar la aplicación

3. **Ve al diagnóstico**: `http://localhost:8080/diagnostico.html`mvn spring-boot:run

```

### Si aparece "Failed to fetch":

- **Causa**: Estás abriendo el archivo directamente en lugar del servidor### Configuración

- **Solución**: Usa `http://localhost:8080/` en lugar de abrir el archivo HTML1. Configurar base de datos en `application.yml`

2. Configurar certificados para firma digital

## 📁 Estructura de Archivos3. Configurar credenciales SUNAT



```## API Documentation

sistema-facturacion-electronica/Una vez ejecutada la aplicación, la documentación de la API estará disponible en:

├── INICIAR_SISTEMA.bat          ← ¡HAZ DOBLE CLIC AQUÍ!- Swagger UI: http://localhost:8080/swagger-ui.html

├── README.md                    ← Esta guía- OpenAPI JSON: http://localhost:8080/v3/api-docs

└── src/main/resources/static/

    ├── portal.html              ← Portal principal## Endpoints Principales

    ├── index.html               ← Formulario de consulta RUC

    ├── diagnostico.html         ← Herramientas de diagnóstico- `POST /api/comprobantes` - Crear comprobante

    ├── prueba-simple.html       ← Pruebas básicas- `GET /api/comprobantes/{id}` - Obtener comprobante

    └── test-fetch.html          ← Pruebas avanzadas- `POST /api/comprobantes/{id}/enviar` - Enviar a SUNAT

```- `GET /api/comprobantes/{id}/estado` - Consultar estado SUNAT



## ✅ Características## Licencia

Este proyecto está bajo la Licencia MIT.

- ✅ **Consulta real SUNAT** (no simulada)

- ✅ **Autocompletado automático** de formularios## Soporte

- ✅ **Base de datos real** de empresas peruanasPara soporte técnico, contactar al equipo de desarrollo.
- ✅ **Interfaz amigable** y fácil de usar
- ✅ **Acceso directo** por URL con parámetros
- ✅ **Diagnósticos integrados** para verificar funcionamiento
- ✅ **Múltiples páginas de prueba** para diferentes necesidades

## 🎯 Para Empezar AHORA MISMO

1. **Haz doble clic** en `INICIAR_SISTEMA.bat`
2. **Espera** que se abra el navegador
3. **Prueba** con el RUC `20100070970` (Rimac Seguros)
4. **¡Disfruta!** del autocompletado automático

---

**¡Tu sistema está listo para usar! 🎉**