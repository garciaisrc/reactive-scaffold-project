FROM eclipse-temurin:17-jdk-alpine

# Crear un usuario sin privilegios
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

WORKDIR /app

# Copiar el jar generado por Gradle
COPY build/libs/*.jar Authetication.jar

# Variables JVM
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=70 -Djava.security.egd=file:/dev/./urandom"

# Permisos
RUN chown appuser:appgroup Authetication.jar

USER appuser

# Exponer el puerto de tu microservicio
EXPOSE 8081

# Ejecutar el jar
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar Authetication.jar"]