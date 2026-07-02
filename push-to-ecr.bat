@echo off
REM Script para hacer build y push a AWS ECR (Windows)
REM Uso: push-to-ecr.bat your_account_id us-east-1

setlocal enabledelayedexpansion

REM Variables
set AWS_ACCOUNT_ID=%1
set AWS_REGION=%2
if "!AWS_REGION!"=="" set AWS_REGION=us-east-1

if "!AWS_ACCOUNT_ID!"=="" (
  echo Error: Proporciona AWS_ACCOUNT_ID
  echo Uso: push-to-ecr.bat your_account_id [region]
  exit /b 1
)

set ECR_REGISTRY=!AWS_ACCOUNT_ID!.dkr.ecr.!AWS_REGION!.amazonaws.com

echo.
echo 🐳 EduFuturo - Docker Push a ECR
echo.
echo Configuración:
echo   AWS Region: !AWS_REGION!
echo   AWS Account: !AWS_ACCOUNT_ID!
echo   ECR Registry: !ECR_REGISTRY!
echo.

REM Login a ECR
echo 🔐 Login a ECR...
for /f "tokens=*" %%i in ('aws ecr get-login-password --region !AWS_REGION!') do (
  docker login --username AWS --password %%i !ECR_REGISTRY!
)
echo ✓ Login exitoso
echo.

REM Build Frontend
echo 🔨 Building Frontend...
cd frontend
docker build -t edufuturo-frontend:latest -t !ECR_REGISTRY!/edufuturo-frontend:latest .
echo ✓ Frontend build completado
cd ..
echo.

REM Push Frontend
echo 📤 Pushing Frontend a ECR...
docker push !ECR_REGISTRY!/edufuturo-frontend:latest
echo ✓ Frontend pushed
echo.

REM Build Backend
echo 🔨 Building Backend...
cd backend
docker build -t edufuturo-backend:latest -t !ECR_REGISTRY!/edufuturo-backend:latest .
echo ✓ Backend build completado
cd ..
echo.

REM Push Backend
echo 📤 Pushing Backend a ECR...
docker push !ECR_REGISTRY!/edufuturo-backend:latest
echo ✓ Backend pushed
echo.

echo.
echo ✅ Proceso completado!
echo.
echo 📍 URLs de las imágenes:
echo   Frontend: !ECR_REGISTRY!/edufuturo-frontend:latest
echo   Backend: !ECR_REGISTRY!/edufuturo-backend:latest
echo.
echo Ver DEPLOY_AWS.md para más detalles
