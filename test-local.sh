#!/bin/bash

# Script para probar el build y despliegue localmente

echo "🚀 Iniciando prueba de build local..."

# Verificar que Maven esté instalado
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven no está instalado"
    exit 1
fi

# Verificar que Docker esté instalado
if ! command -v docker &> /dev/null; then
    echo "❌ Docker no está instalado"
    exit 1
fi

echo "✅ Herramientas verificadas (Maven y Docker)"

# Limpiar y compilar con Maven
echo "📦 Compilando con Maven..."
mvn clean package -DskipTests

if [ $? -ne 0 ]; then
    echo "❌ Error en la compilación con Maven"
    exit 1
fi

echo "✅ Compilación exitosa"

# Verificar que el JAR fue creado
JAR_FILE=$(find target -name "*.jar" -not -name "*-sources.jar" | head -1)
if [ -z "$JAR_FILE" ]; then
    echo "❌ No se encontró el archivo JAR"
    exit 1
fi

echo "✅ JAR encontrado: $JAR_FILE"

# Build de la imagen Docker
echo "🐳 Construyendo imagen Docker..."
docker build -t springboot-auth0-ms:test .

if [ $? -ne 0 ]; then
    echo "❌ Error en el build de Docker"
    exit 1
fi

echo "✅ Imagen Docker creada exitosamente"

# Ejecutar contenedor para probar
echo "🧪 Ejecutando contenedor de prueba..."
docker run -d --name test-container -p 8080:8080 \
  -e AUTH0_DOMAIN=dev-zew5tm6okcs44u67.us.auth0.com \
  -e AUTH0_CLIENT_SECRET=test-secret \
  springboot-auth0-ms:test

sleep 10

# Verificar health check
echo "🏥 Verificando health check..."
if curl -f http://localhost:8080/actuator/health > /dev/null 2>&1; then
    echo "✅ Health check exitoso"
else
    echo "❌ Health check falló"
    docker logs test-container
    docker stop test-container && docker rm test-container
    exit 1
fi

# Limpiar
echo "🧹 Limpiando contenedor de prueba..."
docker stop test-container && docker rm test-container

echo "🎉 ¡Prueba local completada exitosamente!"
echo "📝 El proyecto está listo para desplegarse en Azure Container Apps"
