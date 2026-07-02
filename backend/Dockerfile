# Build stage
FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /app

# Copiar pom.xml
COPY pom.xml .

# Descargar dependencias (layer caching)
RUN mvn dependency:go-offline -B

# Copiar código fuente
COPY src ./src

# Build del JAR
RUN mvn clean package -DskipTests -q

# Runtime stage
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copiar JAR del builder
COPY --from=builder /app/target/edufuturo-backend-1.0.0.jar app.jar

# Exponer puerto
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=15s --retries=3 \
  CMD wget --quiet --tries=1 --spider http://localhost:8080/api/dashboard || exit 1

# Comando para iniciar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]
