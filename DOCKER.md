# 🐳 Docker & Containerización - EduFuturo

## ¿Docker Compose SOLO vs Dockerfiles + Docker Compose?

### ❌ Docker Compose SOLO (Actual antes)
```yaml
services:
  sqlserver:  # ← Solo la BD
```
**Problema:** Frontend y Backend corren localmente con `npm start` y `mvn spring-boot:run`
**Consecuencia:** No puedes desplegarlo en la nube tal cual

---

### ✅ Dockerfiles + Docker Compose (ACTUAL - Ahora)
```yaml
services:
  frontend:    # ← Dockerfile + Node + Nginx
  backend:     # ← Dockerfile + Java + Maven
  sqlserver:   # ← Imagen SQL Server
```
**Ventaja:** Cada servicio en su propio contenedor
**Resultado:** Listo para AWS, Azure, Google Cloud, etc.

---

## 📁 Archivos Creados

### Frontend
```
frontend/
├── Dockerfile          # Multi-stage: Build (Node) → Runtime (Nginx)
├── .dockerignore       # Excluye node_modules, dist, .git
├── nginx.conf          # Configuración de servidor web
└── package.json        # Dependencias Node/Angular
```

**¿Qué hace el Dockerfile del frontend?**
1. **Build stage**: Compila Angular con Node 18
2. **Runtime stage**: Sirve archivos estáticos con Nginx
3. **Proxy**: `/api/*` → redirige al backend
4. **SPA routing**: Maneja rutas de Angular

### Backend
```
backend/
├── Dockerfile          # Multi-stage: Build (Maven) → Runtime (Java)
├── .dockerignore       # Excluye target, .git, Maven cache
├── docker-compose.yml  # Orquesta los 3 servicios
├── pom.xml            # Dependencias Maven
└── src/               # Código Java
```

**¿Qué hace el Dockerfile del backend?**
1. **Build stage**: Compila con Maven (crea JAR)
2. **Runtime stage**: Ejecuta JAR en Java 17
3. **Perfil**: Usa `prod` automáticamente
4. **Health check**: Verifica `/api/dashboard`

### docker-compose.yml (Orquestador)
Coordina los 3 servicios con networking automático:
- **Frontend** en puerto 80 (Nginx proxy)
- **Backend** en puerto 8080
- **SQL Server** en puerto 1433

---

## 🚀 Usar Localmente

### Opción 1: Con Docker Compose (RECOMENDADO para probar)

```bash
cd backend

# Build e inicia los 3 servicios
docker-compose up --build

# Esperar mensajes de "healthy" para cada servicio

# Acceder:
# - Frontend:  http://localhost          (Puerto 80)
# - Backend:   http://localhost:8080/api (Puerto 8080)
# - Database:  localhost:1433            (Puerto 1433)
```

**Beneficios:**
✅ Una sola línea para todo
✅ Networking automático entre servicios
✅ Datos persistentes en volumen

### Opción 2: Sin Docker (Desarrollo local tradicional)

```bash
# Terminal 1: SQL Server
cd backend
docker-compose up sqlserver

# Terminal 2: Backend
cd backend
mvn spring-boot:run

# Terminal 3: Frontend
cd frontend
npm start
```

---

## 🔧 Estructura de Red en Docker Compose

```
┌─────────────────────────────────────────────┐
│         Docker Network: edufuturo-network   │
├─────────────────────────────────────────────┤
│                                             │
│  ┌──────────────┐  ┌──────────────┐       │
│  │ frontend:80  │  │ backend:8080 │       │
│  │ (Nginx)      │  │ (Java)       │       │
│  └──────────────┘  └──────────────┘       │
│         │                   │              │
│         └───────┬───────────┘              │
│                 ↓                          │
│         ┌──────────────────┐              │
│         │ sqlserver:1433   │              │
│         │ (SQL Server DB)  │              │
│         └──────────────────┘              │
│                                           │
└─────────────────────────────────────────────┘

Dentro de los contenedores:
- Frontend: puede conectar a backend:8080
- Backend: puede conectar a sqlserver:1433
- Los puertos están expuestos al host (80, 8080, 1433)
```

---

## 📊 Tamaño de Imágenes

| Imagen | Tamaño | Base |
|--------|--------|------|
| Frontend | ~100MB | node:18-alpine + nginx:alpine |
| Backend | ~350MB | maven:3.9 (build) + eclipse-temurin:17-jre (runtime) |
| SQL Server | ~3.5GB | mcr.microsoft.com/mssql |

**Total:** ~3.9 GB (Download uno sola vez)

---

## 🔍 Debugging en Docker

### Ver logs
```bash
# Todos los servicios
docker-compose logs -f

# Solo un servicio
docker-compose logs -f backend
docker-compose logs -f frontend
docker-compose logs -f sqlserver
```

### Conectarse a un contenedor
```bash
# Shell en backend
docker-compose exec backend bash

# SQL Server
docker-compose exec sqlserver /opt/mssql-tools/bin/sqlcmd -U sa -P EduFuturo@2024
```

### Ver procesos corriendo
```bash
docker-compose ps
```

### Reiniciar un servicio
```bash
docker-compose restart backend
```

---

## 🚨 Problemas Comunes

### ❌ "backend:8080 connection refused"
```bash
# El backend no está healthy aún
# Espera a que muestre ✓ healthy

docker-compose ps
# STATE: Up (healthy) = OK
```

### ❌ "Cannot connect to database"
```bash
# SQL Server tarda en iniciar (40+ segundos)
# Reinicia solo el backend
docker-compose restart backend
```

### ❌ "Port 80 already in use"
```bash
# Otra app usa puerto 80
# Opción 1: Cambiar en docker-compose.yml (81:80)
# Opción 2: Detener otra app (docker ps)
```

### ❌ "Permission denied in .dockerignore"
```bash
chmod 644 frontend/.dockerignore
chmod 644 backend/.dockerignore
```

---

## 📦 Deployment a AWS (Desde Docker)

### Opción A: ECS (Elastic Container Service)
```bash
# 1. Push imágenes a ECR
./push-to-ecr.sh

# 2. Crear ECS cluster desde AWS Console
# 3. Crear task definition
# 4. Crear service
```

### Opción B: App Runner (Simple)
```bash
# 1. Conectar GitHub a App Runner
# 2. Seleccionar carpeta (frontend o backend)
# 3. Auto-deploy en cada push
```

### Opción C: EC2 + Docker Compose
```bash
# 1. Lanzar instancia EC2
# 2. Instalar Docker + Docker Compose
# 3. Git clone + docker-compose up
# 4. Asignar IP Elástica + Dominio
```

Ver [DEPLOY_AWS.md](../DEPLOY_AWS.md) para detalles completos.

---

## ✅ Checklist Pre-Producción

- [ ] Build de imágenes exitoso: `docker-compose build`
- [ ] Los 3 servicios inician: `docker-compose up`
- [ ] Health checks pasan (esperar ~60s)
- [ ] Frontend accesible en localhost
- [ ] Backend responde en localhost:8080/api/dashboard
- [ ] Datos persisten en BD (reiniciar DB no pierde datos)
- [ ] CORS funciona (frontend puede llamar backend)
- [ ] Variables de entorno configuradas
- [ ] Credenciales NO en docker-compose.yml
- [ ] `.dockerignore` en ambas carpetas

---

## 📚 Recursos

- [Docker Compose Docs](https://docs.docker.com/compose/)
- [Docker Best Practices](https://docs.docker.com/develop/dev-best-practices/)
- [AWS ECS Guide](https://docs.aws.amazon.com/ecs/)
- [Nginx Proxy Configuration](https://nginx.org/en/docs/)

---

**¡Tu app está lista para la nube!** ☁️🐳🚀
