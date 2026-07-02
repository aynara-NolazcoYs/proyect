# EduFuturo Backend - Estructura del Proyecto

## 📁 Organización de Carpetas (Estructura Clásica/Plana)

```
backend/
│
├── src/main/java/com/edufuturo/
│   ├── EduFuturoApplication.java      # Punto de entrada Spring Boot + CORS config
│   │
│   ├── model/                         # 📊 ENTITIES - Entidades JPA
│   │   ├── Student.java               # Tabla: Students
│   │   ├── Promoter.java              # Tabla: Promoters
│   │   └── Enrollment.java            # Tabla: Enrollments
│   │
│   ├── dto/                           # 📮 DATA TRANSFER OBJECTS
│   │   ├── StudentDTO.java            # DTO para transferencia de datos
│   │   ├── PromoterDTO.java
│   │   ├── EnrollmentDTO.java
│   │   └── DashboardDTO.java          # DTO para dashboard (estadísticas)
│   │
│   ├── repository/                    # 🗄️ DATA ACCESS LAYER - Spring Data JPA
│   │   ├── StudentRepository.java     # Acceso a datos de Student
│   │   ├── PromoterRepository.java    # Acceso a datos de Promoter
│   │   └── EnrollmentRepository.java  # Acceso a datos de Enrollment
│   │
│   ├── service/                       # ⚙️ BUSINESS LOGIC LAYER - Servicios
│   │   ├── StudentService.java        # Lógica de negocio para Students
│   │   ├── PromoterService.java       # Lógica de negocio para Promoters
│   │   └── EnrollmentService.java     # Lógica de negocio para Enrollments
│   │
│   └── rest/                          # 🔌 REST API CONTROLLERS
│       ├── StudentController.java     # Endpoints: GET/POST/PUT/DELETE /students
│       ├── PromoterController.java    # Endpoints: GET/POST/PUT/DELETE /promoters
│       ├── EnrollmentController.java  # Endpoints: GET/POST/PUT/DELETE /enrollments
│       └── DashboardController.java   # Endpoint: GET /dashboard
│
├── src/main/resources/
│   ├── application.properties         # Configuración por defecto (dev)
│   ├── application-dev.properties     # Perfil: desarrollo (logging detallado)
│   ├── application-prod.properties    # Perfil: producción (logging mínimo)
│   ├── schema.sql                     # DDL para crear tablas (opcional)
│   └── data.sql                       # DML para insertar datos iniciales
│
├── pom.xml                            # Maven: dependencias y configuración build
├── docker-compose.yml                 # SQL Server 2022 en contenedor
├── start-db.sh                        # Script para iniciar BD (Linux/Mac)
├── start-db.bat                       # Script para iniciar BD (Windows)
└── mvnw / mvnw.cmd                    # Maven Wrapper (sin necesidad de instalar Maven)
```

## 🔄 Flujo de Datos

```
REST API Request
     ↓
[REST Controller] - Recibe peticiones HTTP
     ↓
[Service] - Procesa lógica de negocio
     ↓
[Repository] - Consulta/Modifica BD (JPA/Hibernate)
     ↓
[Entity/Model] - Entidad JPA mapeada a tabla SQL
     ↓
[Database] - SQL Server
     ↓
Respuesta DTO → REST Controller → HTTP Response
```

## 📋 Capas de la Aplicación

### 1️⃣ REST Layer (`rest/`)
- **Responsabilidad**: Exponer endpoints HTTP
- **Archivos**: `*Controller.java`
- **Anotaciones clave**: `@RestController`, `@RequestMapping`, `@GetMapping`, `@PostMapping`
- **Retorna**: `ResponseEntity<DTO>`

**Ejemplo:**
```java
@RestController
@RequestMapping("/students")
public class StudentController {
    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents() { ... }
}
```

### 2️⃣ Service Layer (`service/`)
- **Responsabilidad**: Lógica de negocio
- **Archivos**: `*Service.java`
- **Anotaciones clave**: `@Service`, `@Autowired`
- **Métodos**: CRUD (Create, Read, Update, Delete)

**Ejemplo:**
```java
@Service
public class StudentService {
    @Autowired
    private StudentRepository repo;
    
    public StudentDTO getById(Long id) { ... }
}
```

### 3️⃣ Repository Layer (`repository/`)
- **Responsabilidad**: Acceso a datos
- **Archivos**: `*Repository.java`
- **Herencia**: `extends JpaRepository<Entity, ID>`
- **Métodos**: Heredados (save, delete, findById) + Custom queries

**Ejemplo:**
```java
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByDni(String dni);
}
```

### 4️⃣ Model Layer (`model/`)
- **Responsabilidad**: Mapeo a BD
- **Archivos**: `*.java` (Entities)
- **Anotaciones clave**: `@Entity`, `@Table`, `@Column`, `@Id`
- **Mapeo**: Clase Java ↔ Tabla SQL

**Ejemplo:**
```java
@Entity
@Table(name = "Students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nombre")
    private String nombre;
}
```

### 5️⃣ DTO Layer (`dto/`)
- **Responsabilidad**: Transferencia de datos API
- **Archivos**: `*DTO.java`
- **Características**: Lombok (`@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`)
- **Uso**: Separar modelo de BD del modelo de API

**Ejemplo:**
```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
    private Long id;
    private String nombre;
    private String apellido;
}
```

## 🔐 CORS Habilitado

Todos los controllers heredan CORS habilitado en `EduFuturoApplication.java`:
```java
registry.addMapping("/api/**")
    .allowedOrigins("http://localhost:4200", "http://localhost:3000")
    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
```

## 🚀 Endpoints Disponibles

### Students
```
GET    /api/students              # Listar todos
GET    /api/students/{id}         # Obtener por ID
POST   /api/students              # Crear nuevo
PUT    /api/students/{id}         # Actualizar
DELETE /api/students/{id}         # Eliminar
```

### Promoters
```
GET    /api/promoters             # Listar todos
GET    /api/promoters/{id}        # Obtener por ID
POST   /api/promoters             # Crear nuevo
PUT    /api/promoters/{id}        # Actualizar
DELETE /api/promoters/{id}        # Eliminar
```

### Enrollments
```
GET    /api/enrollments           # Listar todos
GET    /api/enrollments/{id}      # Obtener por ID
GET    /api/enrollments/dni/{dni} # Buscar por DNI
POST   /api/enrollments           # Crear nuevo
PUT    /api/enrollments/{id}      # Actualizar
DELETE /api/enrollments/{id}      # Eliminar
```

### Dashboard
```
GET    /api/dashboard             # Obtener estadísticas
```

## ⚙️ Configuración

### Perfiles Disponibles

**Development** (por defecto):
```bash
mvn spring-boot:run
# O con perfil explícito:
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"
```
- SQL más detallado: `show-sql=true`
- Logging completo para debugging
- Connexión: localhost:1433

**Production**:
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=prod"
```
- SQL minimizado
- Logging esencial
- Variables de entorno para credenciales

## 🗄️ Tablas de BD

### Students
| Campo | Tipo | Constraints |
|-------|------|---|
| id | BIGINT | PK, IDENTITY |
| nombre | NVARCHAR(100) | NOT NULL |
| apellido | NVARCHAR(100) | NOT NULL |
| dni | NVARCHAR(20) | NOT NULL, UNIQUE |
| correo | NVARCHAR(150) | NOT NULL, UNIQUE |
| fecha_registro | DATETIME | |

### Promoters
| Campo | Tipo | Constraints |
|-------|------|---|
| id | BIGINT | PK, IDENTITY |
| dni | NVARCHAR(20) | NOT NULL, UNIQUE |
| nombreCompleto | NVARCHAR(150) | NOT NULL |
| sede | NVARCHAR(50) | NOT NULL |
| email | NVARCHAR(150) | NOT NULL, UNIQUE |
| fecha_registro | DATETIME | |

### Enrollments
| Campo | Tipo | Constraints |
|-------|------|---|
| id | BIGINT | PK, IDENTITY |
| nombre | NVARCHAR(100) | NOT NULL |
| apellido | NVARCHAR(100) | NOT NULL |
| dni | NVARCHAR(20) | NOT NULL |
| sede | NVARCHAR(50) | NOT NULL |
| promotor | NVARCHAR(150) | NOT NULL |
| programa | NVARCHAR(150) | NOT NULL |
| modalidad | NVARCHAR(50) | NOT NULL |
| correo | NVARCHAR(150) | NOT NULL |
| fecha_registro | DATETIME | |

---

**Esta arquitectura clásica es fácil de entender, mantener y escalar.** ✅
