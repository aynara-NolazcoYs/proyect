# EduFuturo Backend

Backend API con Spring Boot 3.0 + SQL Server

## Inicio Rápido

### 1. Iniciar SQL Server (Docker)
```bash
docker-compose up -d
```

### 2. Compilar
```bash
mvn clean install
```

### 3. Ejecutar
```bash
mvn spring-boot:run
```

**API disponible en**: `http://localhost:8080/api`

## Endpoints Principales

- **Students**: `GET/POST /api/students`
- **Promoters**: `GET/POST /api/promoters`
- **Enrollments**: `GET/POST /api/enrollments`
- **Dashboard**: `GET /api/dashboard`

Ver [README.md](README.md) para documentación completa.
