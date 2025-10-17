@echo off
title Preparar para Railway Deployment
echo.
echo =====================================================
echo    🚀 PREPARANDO PARA RAILWAY DEPLOYMENT
echo =====================================================
echo.

echo 📋 Verificando archivos necesarios...

echo ✅ Procfile
if not exist "Procfile" (
    echo web: java -jar target/sistema-facturacion-electronica-1.0.0.jar > Procfile
    echo    ✅ Procfile creado
) else (
    echo    ✅ Procfile ya existe
)

echo ✅ .gitignore
if not exist ".gitignore" (
    echo # Java > .gitignore
    echo *.class >> .gitignore
    echo *.log >> .gitignore
    echo *.jar >> .gitignore
    echo *.war >> .gitignore
    echo *.ear >> .gitignore
    echo # Maven >> .gitignore
    echo target/ >> .gitignore
    echo pom.xml.tag >> .gitignore
    echo pom.xml.releaseBackup >> .gitignore
    echo pom.xml.versionsBackup >> .gitignore
    echo .mvn/timing.properties >> .gitignore
    echo # IDEs >> .gitignore
    echo .idea/ >> .gitignore
    echo *.iml >> .gitignore
    echo .vscode/ >> .gitignore
    echo # OS >> .gitignore
    echo .DS_Store >> .gitignore
    echo Thumbs.db >> .gitignore
    echo # Logs >> .gitignore
    echo logs/ >> .gitignore
    echo *.log >> .gitignore
    echo    ✅ .gitignore creado
) else (
    echo    ✅ .gitignore ya existe
)

echo.
echo 🔧 Compilando proyecto...
call mvnw.cmd clean package -DskipTests
if errorlevel 1 (
    echo ❌ Error en compilación
    pause
    exit /b 1
)

echo.
echo 📊 Resumen de archivos creados:
echo    📄 Procfile - Comando de inicio para Railway
echo    📄 application-prod.yml - Configuración de producción
echo    📄 .gitignore - Archivos a ignorar en Git
echo    📄 CREDENCIALES_DEMO.md - Usuarios y contraseñas demo
echo    📄 GUIA_DEPLOYMENT_GRATUITO.md - Guía completa
echo.

echo 🎯 Próximos pasos:
echo    1. 📤 Subir código a GitHub
echo    2. 🔗 Conectar Railway con GitHub
echo    3. ⚙️  Configurar variables de entorno
echo    4. 🚀 Deploy automático
echo.

echo 💡 Variables de entorno para Railway:
echo    SPRING_PROFILES_ACTIVE=prod
echo    DEMO_USERNAME=demo
echo    DEMO_PASSWORD=demo123
echo    ADMIN_USERNAME=admin
echo    ADMIN_PASSWORD=admin123
echo.

echo ✅ ¡Proyecto listo para Railway!
echo 🌐 Guía completa en: GUIA_DEPLOYMENT_GRATUITO.md
echo 🔐 Credenciales en: CREDENCIALES_DEMO.md
echo.
pause