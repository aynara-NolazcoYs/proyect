#!/bin/bash

# Script para hacer build y push a AWS ECR
# Uso: ./push-to-ecr.sh

set -e

# Variables
AWS_REGION=${AWS_REGION:-us-east-1}
AWS_ACCOUNT_ID=${AWS_ACCOUNT_ID:-your_account_id}
ECR_REGISTRY="${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com"

# Colores
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${YELLOW}🐳 EduFuturo - Docker Push a ECR${NC}"
echo ""

# 1. Validar variables
if [ "$AWS_ACCOUNT_ID" == "your_account_id" ]; then
  echo -e "${RED}❌ Error: Configura AWS_ACCOUNT_ID${NC}"
  echo "Uso: AWS_ACCOUNT_ID=123456789 AWS_REGION=us-east-1 ./push-to-ecr.sh"
  exit 1
fi

echo -e "${YELLOW}📝 Configuración:${NC}"
echo "  AWS Region: $AWS_REGION"
echo "  AWS Account: $AWS_ACCOUNT_ID"
echo "  ECR Registry: $ECR_REGISTRY"
echo ""

# 2. Login a ECR
echo -e "${YELLOW}🔐 Login a ECR...${NC}"
aws ecr get-login-password --region $AWS_REGION | docker login --username AWS --password-stdin $ECR_REGISTRY
echo -e "${GREEN}✓ Login exitoso${NC}"
echo ""

# 3. Crear repositorios si no existen
echo -e "${YELLOW}📦 Creando repositorios ECR...${NC}"
for repo in edufuturo-frontend edufuturo-backend; do
  if aws ecr describe-repositories --repository-names $repo --region $AWS_REGION 2>/dev/null; then
    echo -e "${GREEN}✓ Repositorio $repo ya existe${NC}"
  else
    echo "  Creando $repo..."
    aws ecr create-repository --repository-name $repo --region $AWS_REGION --image-scan-on-push --encryptionConfiguration encryptionType=AES
    echo -e "${GREEN}✓ Repositorio $repo creado${NC}"
  fi
done
echo ""

# 4. Build Frontend
echo -e "${YELLOW}🔨 Building Frontend...${NC}"
cd frontend
docker build -t edufuturo-frontend:latest -t $ECR_REGISTRY/edufuturo-frontend:latest .
echo -e "${GREEN}✓ Frontend build completado${NC}"
cd ..
echo ""

# 5. Push Frontend
echo -e "${YELLOW}📤 Pushing Frontend a ECR...${NC}"
docker push $ECR_REGISTRY/edufuturo-frontend:latest
echo -e "${GREEN}✓ Frontend pushed${NC}"
echo ""

# 6. Build Backend
echo -e "${YELLOW}🔨 Building Backend...${NC}"
cd backend
docker build -t edufuturo-backend:latest -t $ECR_REGISTRY/edufuturo-backend:latest .
echo -e "${GREEN}✓ Backend build completado${NC}"
cd ..
echo ""

# 7. Push Backend
echo -e "${YELLOW}📤 Pushing Backend a ECR...${NC}"
docker push $ECR_REGISTRY/edufuturo-backend:latest
echo -e "${GREEN}✓ Backend pushed${NC}"
echo ""

# 8. Resumen
echo -e "${GREEN}✅ Proceso completado!${NC}"
echo ""
echo -e "${YELLOW}📍 URLs de las imágenes:${NC}"
echo "  Frontend: $ECR_REGISTRY/edufuturo-frontend:latest"
echo "  Backend: $ECR_REGISTRY/edufuturo-backend:latest"
echo ""
echo -e "${YELLOW}🚀 Próximos pasos:${NC}"
echo "  1. Crear ECS Cluster"
echo "  2. Crear Task Definitions"
echo "  3. Crear ECS Services"
echo "  4. Configurar Load Balancer"
echo ""
echo "Ver DEPLOY_AWS.md para más detalles"
