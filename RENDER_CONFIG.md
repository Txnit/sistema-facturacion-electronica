# 🛠️ CONFIGURACIÓN EXACTA PARA RENDER.COM

## 📋 Settings del Web Service:

### Basic Info:
- **Name**: `sistema-facturacion-electronica`
- **Region**: `Ohio (US East)` (más rápido para Latinoamérica)
- **Branch**: `main`
- **Root Directory**: (dejar vacío)

### Build & Deploy:
- **Runtime**: `Docker` (se detecta automáticamente)
- **Build Command**: 
```bash
./mvnw clean package -DskipTests
```

- **Start Command**:
```bash
java -jar target/sistema-facturacion-electronica-1.0.0.jar
```

## ⚙️ Environment Variables:

**Agregar estas variables exactamente:**

| Key | Value |
|-----|-------|
| `SPRING_PROFILES_ACTIVE` | `prod` |
| `DEMO_USERNAME` | `demo` |
| `DEMO_PASSWORD` | `demo123` |
| `ADMIN_USERNAME` | `admin` |
| `ADMIN_PASSWORD` | `admin123` |
| `PORT` | `8080` |

## 🔧 Advanced Settings (Opcional):
- **Instance Type**: `Free`
- **Auto-Deploy**: `Yes` ✅
- **Pull Request Previews**: `No` (para plan gratuito)

---

## 🎯 PROCESO COMPLETO:

### Paso 1: Basic Settings
- Name: `sistema-facturacion-electronica`
- Environment: `Node.js` → **CAMBIAR A**: `Docker`
- Region: `Ohio`
- Branch: `main`

### Paso 2: Build Settings
- Build Command: `./mvnw clean package -DskipTests`
- Start Command: `java -jar target/sistema-facturacion-electronica-1.0.0.jar`

### Paso 3: Environment Variables (MUY IMPORTANTE)
Agregar las 5 variables de arriba una por una

### Paso 4: Deploy
- **Create Web Service** → ¡Automático!

---

## ✅ Resultado Esperado:

### URL Final:
```
https://sistema-facturacion-electronica.onrender.com
```

### Credenciales para compartir:
```
🌐 URL: https://sistema-facturacion-electronica.onrender.com
👤 Usuario: demo
🔑 Contraseña: demo123

👨‍💼 Admin: admin / admin123
```

### Primera carga:
- ⏰ **30-60 segundos** (cold start)
- 💤 Se duerme tras **15 minutos** sin uso
- 🔄 Se activa automáticamente con nueva visita

---

## 🚨 NOTAS IMPORTANTES:

1. **Environment**: Cambiar a "Docker" si sale "Node.js"
2. **Build time**: Primer deploy puede tardar 5-10 minutos
3. **Logs**: Revisar si hay errores en el dashboard
4. **Health check**: Render verificará que puerto 8080 responda

---

¡Tu sistema estará listo para compartir en pocos minutos! 🎉