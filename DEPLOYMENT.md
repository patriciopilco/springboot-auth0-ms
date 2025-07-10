# Despliegue en Azure Container Apps

Este proyecto está configurado para desplegarse automáticamente en Azure Container Apps usando GitHub Actions.

## Configuración de Secrets en GitHub

Para que el despliegue funcione correctamente, necesitas configurar los siguientes secrets en tu repositorio de GitHub:

### Secrets de Azure (requeridos)
- `AUTH0_AZURE_CLIENT_ID`: Client ID de la aplicación de Azure AD
- `AUTH0_AZURE_TENANT_ID`: Tenant ID de Azure AD
- `AUTH0_AZURE_SUBSCRIPTION_ID`: ID de suscripción de Azure

### Secrets de Auth0 (opcionales - pueden usar valores por defecto)
- `AUTH0_DOMAIN`: Dominio de Auth0 (por defecto: dev-zew5tm6okcs44u67.us.auth0.com)
- `AUTH0_ISSUER_URI`: URI del issuer de Auth0 (por defecto: https://dev-zew5tm6okcs44u67.us.auth0.com)
- `AUTH0_CLIENT_ID`: Client ID de Auth0 (por defecto: SZQ8Yqtdn5YdKt2SRVZuvRWWDOwTl3J0)
- `AUTH0_CLIENT_SECRET`: Client Secret de Auth0
- `AUTH0_AUDIENCE`: Audience de Auth0 (por defecto: https://yard.backend)

## Configuración en Azure

1. **Crear Container App Environment**: Asegúrate de tener un Container App Environment en el resource group `apps-github-actions`
2. **Configurar OIDC**: Configura la autenticación OIDC entre GitHub y Azure
3. **Permisos**: El service principal debe tener permisos para crear y actualizar Container Apps

## Proceso de Despliegue

El despliegue se ejecuta automáticamente cuando:
- Se hace push a la rama `main`
- Se ejecuta manualmente desde GitHub Actions

### Pasos del despliegue:
1. Checkout del código
2. Configuración de JDK 17
3. Build con Maven (genera el JAR)
4. Login en Azure
5. Build y push de la imagen Docker
6. Despliegue en Container Apps

## Configuración Local para Desarrollo

Para ejecutar localmente:

```bash
# Compilar el proyecto
mvn clean package

# Ejecutar con Docker
docker build -t springboot-auth0-ms .
docker run -p 8080:8080 \
  -e AUTH0_DOMAIN=tu-dominio.auth0.com \
  -e AUTH0_CLIENT_SECRET=tu-client-secret \
  springboot-auth0-ms
```

## Variables de Entorno

La aplicación utiliza las siguientes variables de entorno:
- `SERVER_PORT`: Puerto del servidor (por defecto: 8080)
- `AUTH0_DOMAIN`: Dominio de Auth0
- `AUTH0_ISSUER_URI`: URI del issuer de Auth0
- `AUTH0_CLIENT_ID`: Client ID de Auth0
- `AUTH0_CLIENT_SECRET`: Client Secret de Auth0
- `AUTH0_AUDIENCE`: Audience de Auth0
