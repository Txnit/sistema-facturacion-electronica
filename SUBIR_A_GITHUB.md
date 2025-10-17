# 📤 CREAR REPOSITORIO EN GITHUB - Guía Paso a Paso

## 🎯 Opción 1: Crear desde GitHub Web (RECOMENDADO)

### 1️⃣ Ir a GitHub
1. **Abrir**: https://github.com
2. **Hacer clic** en el botón **"+"** (arriba derecha)
3. **Seleccionar**: "New repository"

### 2️⃣ Configurar Repositorio
```
Repository name: sistema-facturacion-electronica
Description: 🧾 Sistema de Facturación Electrónica - Java 21 + Spring Boot - SUNAT Perú
✅ Public (para Railway gratuito)
❌ Add a README file (ya tienes uno)
❌ Add .gitignore (ya tienes uno)
❌ Choose a license (opcional)
```

### 3️⃣ Crear y Conectar
Después de crear, GitHub te mostrará comandos. **Usar estos**:

```bash
git remote add origin https://github.com/TU_USUARIO/sistema-facturacion-electronica.git
git branch -M main
git push -u origin main
```

---

## 🎯 Opción 2: GitHub CLI (Si tienes GitHub CLI)

### 1️⃣ Instalar GitHub CLI
```bash
# Windows con Chocolatey
choco install gh

# O descargar desde: https://cli.github.com
```

### 2️⃣ Login y Crear
```bash
# Login
gh auth login

# Crear repositorio
gh repo create sistema-facturacion-electronica --public --description "🧾 Sistema de Facturación Electrónica - Java 21 + Spring Boot - SUNAT Perú"

# Push automático
git push -u origin main
```

---

## 🚀 Comandos Listos para Copiar

### Después de crear el repo en GitHub:

```bash
# Agregar remote (CAMBIAR TU_USUARIO por tu username de GitHub)
git remote add origin https://github.com/TU_USUARIO/sistema-facturacion-electronica.git

# Cambiar a main branch
git branch -M main

# Subir código
git push -u origin main
```

---

## ✅ Verificación

### Después del push, deberías ver:
- ✅ 58 archivos subidos
- ✅ README.md visible con descripción
- ✅ Código Java organizado
- ✅ Archivos de deployment (Procfile, etc.)

---

## 🎯 Próximo Paso: Railway

### Una vez en GitHub:
1. **Ir a**: https://railway.app
2. **Conectar** con GitHub
3. **Seleccionar** tu repositorio
4. **Deploy automático** 🚀

---

## 🔧 Comandos de Utilidad

### Ver remotes:
```bash
git remote -v
```

### Cambiar URL del remote:
```bash
git remote set-url origin https://github.com/TU_USUARIO/nuevo-nombre.git
```

### Force push (solo si es necesario):
```bash
git push -f origin main
```

---

## 📋 Checklist Pre-Push

- ✅ Repositorio Git inicializado
- ✅ Archivos agregados y commitados
- ✅ README.md actualizado
- ✅ .gitignore configurado
- ✅ Procfile para Railway
- ✅ Credenciales demo configuradas

---

¡Todo listo para GitHub! 🎉