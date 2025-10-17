# 🔐 Credenciales de Acceso - Sistema de Facturación Electrónica

## 👤 Usuarios Demo para Testing

### 🟢 Usuario Normal (Demo)
```
👤 Usuario: demo
🔑 Contraseña: demo123
🎯 Rol: USER
📝 Descripción: Para pruebas generales del sistema
```

### 🔴 Usuario Administrador
```
👤 Usuario: admin
🔑 Contraseña: admin123
🎯 Rol: ADMIN + USER
📝 Descripción: Acceso completo al sistema
```

---

## 🌐 URLs de Acceso

### 🏠 Aplicación Principal
```
https://tu-sistema.up.railway.app
```

### 📊 API Documentation (Swagger)
```
https://tu-sistema.up.railway.app/swagger-ui.html
```

### 🗄️ Base de Datos (H2 Console) - Solo en desarrollo
```
https://tu-sistema.up.railway.app/h2-console
```

---

## 🧪 Endpoints para Probar

### 🔍 Consulta RUC (Público)
```http
GET https://tu-sistema.up.railway.app/api/consulta-ruc/20568849559
```

### 👥 Gestión de Clientes
```http
GET https://tu-sistema.up.railway.app/api/clientes
POST https://tu-sistema.up.railway.app/api/clientes
```

### 📄 Gestión de Comprobantes
```http
GET https://tu-sistema.up.railway.app/api/comprobantes
POST https://tu-sistema.up.railway.app/api/comprobantes
```

---

## 🛠️ Cómo Probar

### 1. 🌐 Acceso Web
1. Abrir: `https://tu-sistema.up.railway.app`
2. Si se pide login, usar credenciales de arriba
3. Navegar por la interfaz web

### 2. 📱 API Testing con Postman/Insomnia
```bash
# Headers
Authorization: Basic ZGVtbzpkZW1vMTIz  # demo:demo123 en base64
Content-Type: application/json
```

### 3. 🔍 Consulta RUC Rápida
```bash
curl -X GET "https://tu-sistema.up.railway.app/api/consulta-ruc/20568849559"
```

---

## ⚠️ Notas Importantes

### 🔒 Seguridad
- ❌ **NO** usar estas credenciales en producción real
- ✅ Solo para **testing** y **demos**
- 🔄 Cambiar credenciales antes de uso productivo

### 🚀 Performance
- ⏰ Primera carga puede tardar **30-60 segundos** (cold start)
- 💤 Se duerme después de **15 minutos** sin actividad
- 🔄 Se reactiva automáticamente con nueva solicitud

### 💾 Base de Datos
- 🗄️ **H2 en memoria** para desarrollo
- 🔄 **PostgreSQL** para Railway/producción
- ⚠️ Datos se **resetean** en cada deploy

---

## 📞 Soporte

Si encuentras problemas:
1. 🔄 **Refrescar** la página
2. ⏰ **Esperar** 60 segundos si es primera carga
3. 📧 **Contactar** al desarrollador
4. 📋 **Reportar** errores con detalles

---

✨ **¡Sistema listo para compartir y probar!** ✨