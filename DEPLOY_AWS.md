# Deployment a AWS con Docker

## 🚀 Opciones de Deploy en AWS

### Opción 1: ECS (Elastic Container Service) - ⭐ RECOMENDADO

**Ventajas:**
- Gestión de contenedores fácil
- Auto-scaling
- Integrado con otros servicios AWS
- Costo optimizado

**Pasos:**

1. **Crear ECR (Elastic Container Registry)**
   ```bash
   # Crear repositorio para cada imagen
   aws ecr create-repository --repository-name edufuturo-frontend --region us-east-1
   aws ecr create-repository --repository-name edufuturo-backend --region us-east-1
   ```

2. **Build y Push de imágenes**
   ```bash
   # Login a ECR
   aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin YOUR_ACCOUNT_ID.dkr.ecr.us-east-1.amazonaws.com
   
   # Frontend
   cd frontend
   docker build -t edufuturo-frontend:latest .
   docker tag edufuturo-frontend:latest YOUR_ACCOUNT_ID.dkr.ecr.us-east-1.amazonaws.com/edufuturo-frontend:latest
   docker push YOUR_ACCOUNT_ID.dkr.ecr.us-east-1.amazonaws.com/edufuturo-frontend:latest
   
   # Backend
   cd ../backend
   docker build -t edufuturo-backend:latest .
   docker tag edufuturo-backend:latest YOUR_ACCOUNT_ID.dkr.ecr.us-east-1.amazonaws.com/edufuturo-backend:latest
   docker push YOUR_ACCOUNT_ID.dkr.ecr.us-east-1.amazonaws.com/edufuturo-backend:latest
   ```

3. **Crear RDS para SQL Server**
   ```bash
   # Desde AWS Console: RDS → Create Database → SQL Server
   # O con AWS CLI:
   aws rds create-db-instance \
     --db-instance-identifier edufuturo-db \
     --engine sqlserver-ex \
     --db-instance-class db.t3.micro \
     --master-username sa \
     --master-user-password EduFuturo@2024! \
     --allocated-storage 20
   ```

4. **Crear ECS Cluster**
   ```bash
   aws ecs create-cluster --cluster-name edufuturo-cluster
   ```

5. **Crear Task Definition**
   - Especificar imágenes ECR
   - Configurar variables de entorno
   - Definir puertos
   - Asignar roles IAM

6. **Crear ECS Service**
   - Load balancer (ALB)
   - Auto-scaling
   - Health checks

---

### Opción 2: App Runner - 🎯 MÁS SIMPLE

**Ventajas:**
- Deploy automático desde repositorio
- Sin gestión de infraestructura
- Ideal para startups
- Menos configuración

**Pasos:**

```bash
# 1. Conectar repositorio GitHub a App Runner
# AWS Console → App Runner → Create Service

# 2. Seleccionar Dockerfile en cada carpeta

# 3. Configurar variables de entorno

# 4. Deploy automático
```

---

### Opción 3: EC2 + Docker Compose - 💻 MANUAL

**Ventajas:**
- Control total
- Flexible
- Menor costo con instancias pequeñas

**Pasos:**

```bash
# 1. Lanzar instancia EC2 (Amazon Linux 2 o Ubuntu)
aws ec2 run-instances --image-id ami-0c55b159cbfafe1f0 --instance-type t3.medium

# 2. SSH a la instancia
ssh -i your-key.pem ec2-user@your-instance-ip

# 3. Instalar Docker
sudo yum update -y
sudo yum install docker -y
sudo systemctl start docker
sudo usermod -aG docker ec2-user

# 4. Instalar Docker Compose
sudo curl -L "https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose

# 5. Clonar repositorio
git clone https://github.com/your-repo/edufuturo.git
cd edufuturo/backend

# 6. Actualizar docker-compose.yml para RDS
# Cambiar sqlserver service a referencia de RDS endpoint

# 7. Build y run
docker-compose up -d
```

---

## 🔐 Consideraciones de Seguridad para Producción

### 1. Variables de Entorno
```bash
# NO hardcodear credenciales en docker-compose
# Usar AWS Secrets Manager:

# Backend
SPRING_DATASOURCE_PASSWORD=${DB_PASSWORD}
SPRING_DATASOURCE_URL=${DB_URL}

# O usar .env file (NO en Git):
# .env
DB_PASSWORD=tu_password_segura
DB_URL=jdbc:sqlserver://your-rds-endpoint:1433...
```

### 2. Networking
```yaml
# Solo exponer puerto 80 en ALB, no 8080 directamente
# Backend en red privada
# RDS en subnet privada
```

### 3. Database
```sql
-- Cambiar contraseña por defecto
ALTER LOGIN sa WITH PASSWORD='NewSecurePassword!2024';

-- Crear usuario específico para la app (NO usar sa)
CREATE LOGIN EduFuturoUser WITH PASSWORD='SecureAppPassword!2024';
CREATE USER EduFuturoUser FOR LOGIN EduFuturoUser;
GRANT DB_OWNER TO EduFuturoUser;
```

### 4. SSL/TLS
```yaml
# Agregar certificado ACM en ALB
# Forzar HTTPS
```

---

## 📊 Comparativa de Opciones

| Aspecto | ECS | App Runner | EC2 |
|--------|-----|-----------|-----|
| **Complejidad** | Media | Baja | Alta |
| **Costo** | Medio | Bajo-Medio | Bajo-Medio |
| **Auto-scaling** | ✅ Sí | ✅ Sí | ❌ Manual |
| **Mantenimiento** | AWS | AWS | Tuyo |
| **Ideal para** | Producción | Startups | Control total |

---

## 🔍 Monitoreo en Producción

### CloudWatch
```bash
# Logs del aplicativo
aws logs tail /ecs/edufuturo-backend --follow

# Métricas personalizadas
```

### Health Checks
- Frontend: `/health`
- Backend: `/api/dashboard`
- Database: Query simple

---

## 🚀 Comando Rápido para Desarrollo Local

```bash
# Desde backend/ (donde está docker-compose.yml)
docker-compose up --build

# Esperar a que todo esté healthy
# Frontend: http://localhost
# Backend: http://localhost:8080/api
# SQL Server: localhost:1433
```

---

## 📝 Checklist Pre-Deploy a Producción

- [ ] Actualizar contraseña de SA en RDS
- [ ] Configurar variables de entorno con Secrets Manager
- [ ] Testear conectividad frontend → backend → RDS
- [ ] Configurar dominio en Route53 o CloudFront
- [ ] Habilitar HTTPS/SSL en ALB
- [ ] Configurar backups de RDS
- [ ] Habilitar CloudWatch logs
- [ ] Configurar alertas
- [ ] Testear failover/recovery
- [ ] Documentar proceso de deployment

---

**¡Listo para escalar en la nube!** ☁️🚀
