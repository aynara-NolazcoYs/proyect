# EduFuturo Backend - Spring Boot + SQL Server

## 📋 Descripción

Backend API para el sistema de gestión educativo EduFuturo construido con **Spring Boot 3.0+** y **SQL Server**.

## 🏗️ Arquitectura

```
backend/src/main/java/com/edufuturo/
├── EduFuturoApplication.java      # Aplicación principal
├── config/                         # Configuración global (CORS, etc.)
├── features/
│   ├── students/                  # Feature: Gestión de Estudiantes
│   │   ├── entity/                # Entidad Student
│   │   ├── repository/            # StudentRepository
│   │   ├── service/               # StudentService
│   │   ├── controller/            # StudentController
│   │   └── dto/                   # StudentDTO
│   ├── promoters/                 # Feature: Gestión de Promotores
│   │   ├── entity/                # Entidad Promoter
│   │   ├── repository/            # PromoterRepository
│   │   ├── service/               # PromoterService
│   │   ├── controller/            # PromoterController
│   │   └── dto/                   # PromoterDTO
│   ├── enrollments/               # Feature: Gestión de Matrículas
│   │   ├── entity/                # Entidad Enrollment
│   │   ├── repository/            # EnrollmentRepository
│   │   ├── service/               # EnrollmentService
│   │   ├── controller/            # EnrollmentController
│   │   └── dto/                   # EnrollmentDTO
│   └── dashboard/                 # Feature: Dashboard
│       ├── controller/            # DashboardController
│       └── dto/                   # DashboardDTO
└── resources/
    └── application.properties     # Configuración
```

## 🚀 Tecnologías

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **SQL Server 2022**
- **Lombok**
- **Maven**
- **Docker**

## 📦 Requisitos

- Java 17+
- Maven 3.8+
- Docker & Docker Compose
- SQL Server (en contenedor)

## 🔧 Instalación

### 1. Iniciar la base de datos (SQL Server)

```bash
docker-compose up -d
```

Esto levantará SQL Server en `localhost:1433` con:
- Usuario: `sa`
- Contraseña: `EduFuturo@2024`

### 2. Compilar el proyecto

```bash
mvn clean install
```

### 3. Ejecutar la aplicación

```bash
mvn spring-boot:run
```

La aplicación estará disponible en: **http://localhost:8080/api**

## 📚 Endpoints API

### Students
- `GET /api/students` - Listar estudiantes
- `GET /api/students/{id}` - Obtener estudiante por ID
- `POST /api/students` - Crear estudiante
- `PUT /api/students/{id}` - Actualizar estudiante
- `DELETE /api/students/{id}` - Eliminar estudiante

### Promoters
- `GET /api/promoters` - Listar promotores
- `GET /api/promoters/{id}` - Obtener promotor por ID
- `POST /api/promoters` - Crear promotor
- `PUT /api/promoters/{id}` - Actualizar promotor
- `DELETE /api/promoters/{id}` - Eliminar promotor

### Enrollments
- `GET /api/enrollments` - Listar matrículas
- `GET /api/enrollments/{id}` - Obtener matrícula por ID
- `GET /api/enrollments/dni/{dni}` - Obtener matrículas por DNI
- `POST /api/enrollments` - Crear matrícula
- `PUT /api/enrollments/{id}` - Actualizar matrícula
- `DELETE /api/enrollments/{id}` - Eliminar matrícula

### Dashboard
- `GET /api/dashboard` - Obtener estadísticas del dashboard

## 🗄️ Base de Datos

### Tablas automáticamente creadas:
- **Students** - Registros de estudiantes
- **Promoters** - Registros de promotores
- **Enrollments** - Registros de matrículas

## ⚙️ Configuración

Archivo: `src/main/resources/application.properties`

```properties
# Base de Datos
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=EduFuturoDB;...
spring.datasource.username=sa
spring.datasource.password=EduFuturo@2024

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update

# Server
server.port=8080
server.servlet.context-path=/api

# CORS
spring.web.cors.allowed-origins=http://localhost:4200,http://localhost:3000
```

## 🧪 Testing

```bash
mvn test
```

## 📝 Ejemplo de Request

### Crear Estudiante

```bash
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Juan",
    "apellido": "Pérez",
    "dni": "12345678",
    "correo": "juan@example.com"
  }'
```

## 🤝 Integración con Frontend

El frontend Angular en `/frontend` se conecta a este backend mediante:

**Base URL**: `http://localhost:8080/api`

**CORS está habilitado** para `localhost:4200` y `localhost:3000`

## 📦 Build para Producción

```bash
mvn clean package
```

Genera JAR en: `target/edufuturo-backend-1.0.0.jar`

## 🐳 Docker (Opcional)

Para dockerizar la aplicación:

```dockerfile
FROM openjdk:17-jdk-slim
COPY target/edufuturo-backend-1.0.0.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

## 📄 Licencia

Proyecto educativo - EduFuturo

## 👨‍💻 Desarrollo

La arquitectura sigue el patrón **Feature-Based**:
- Cada feature es independiente
- Services manejan lógica de negocio
- Controllers exponen endpoints REST
- DTOs para comunicación con cliente
- Repositories manejan acceso a datos
