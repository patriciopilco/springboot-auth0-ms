# Usar imagen base de Eclipse Temurin 17 (más segura y actualizada)
FROM eclipse-temurin:17-jre-alpine

# Crear un usuario no-root para ejecutar la aplicación e instalar wget para health check
RUN apk add --no-cache wget && \
    addgroup -g 1001 -S appgroup && \
    adduser -u 1001 -S appuser -G appgroup

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el archivo JAR de la aplicación
COPY target/springboot-auth0-ms-*.jar app.jar

# Cambiar la propiedad del archivo JAR al usuario no-root
RUN chown appuser:appgroup app.jar

# Cambiar al usuario no-root
USER appuser

# Exponer el puerto en el que la aplicación se ejecutará
EXPOSE 8080

# Variables de entorno por defecto
ENV JAVA_OPTS=""
ENV SERVER_PORT=8080
ENV SPRING_PROFILES_ACTIVE=prod

# Comando para ejecutar la aplicación
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar app.jar"]

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:${SERVER_PORT}/actuator/health || exit 1

# Etiquetas de metadata
LABEL maintainer="xpoch"
LABEL version="0.0.1-SNAPSHOT"
LABEL description="SpringBoot Auth0 Microservice"
