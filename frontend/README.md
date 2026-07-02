# EduFuturo Frontend - Feature-Based Architecture

## Estructura del Proyecto

```
frontend/
├── src/
│   ├── app/
│   │   ├── core/                           # Servicios globales y singleton
│   │   │   └── services/
│   │   │       ├── api.service.ts          # Servicio HTTP base
│   │   │       └── index.ts
│   │   │
│   │   ├── shared/                         # Componentes y utilidades compartidas
│   │   │   ├── components/                 # Componentes reutilizables
│   │   │   ├── directives/                 # Directivas personalizadas
│   │   │   ├── pipes/                      # Pipes personalizados
│   │   │   └── models/
│   │   │       ├── student.model.ts
│   │   │       ├── promoter.model.ts
│   │   │       ├── enrollment.model.ts
│   │   │       ├── dashboard.model.ts
│   │   │       └── index.ts
│   │   │
│   │   ├── features/                       # Módulos de features por dominio
│   │   │   ├── students/
│   │   │   │   ├── components/
│   │   │   │   │   └── students-list.component.ts/html
│   │   │   │   ├── services/
│   │   │   │   │   └── student.service.ts
│   │   │   │   ├── models/
│   │   │   │   ├── pages/
│   │   │   │   │   └── dashboard.component.ts/html
│   │   │   │   └── students.module.ts
│   │   │   │
│   │   │   ├── promoters/
│   │   │   │   ├── components/
│   │   │   │   │   └── promoters-list.component.ts/html
│   │   │   │   ├── services/
│   │   │   │   │   └── promoter.service.ts
│   │   │   │   ├── models/
│   │   │   │   ├── pages/
│   │   │   │   └── promoters.module.ts
│   │   │   │
│   │   │   └── enrollments/
│   │   │       ├── components/
│   │   │       │   └── enrollment-form.component.ts/html
│   │   │       ├── services/
│   │   │       │   └── enrollment.service.ts
│   │   │       ├── models/
│   │   │       ├── pages/
│   │   │       └── enrollments.module.ts
│   │   │
│   │   ├── app.component.ts/html
│   │   ├── app.module.ts
│   │   └── app-routing.module.ts
│   │
│   ├── assets/
│   ├── environments/
│   │   ├── environment.ts
│   │   └── environment.prod.ts
│   ├── index.html
│   ├── main.ts
│   └── styles.css
│
├── angular.json
├── package.json
├── tsconfig.json
├── tsconfig.app.json
├── .gitignore
├── .editorconfig
└── README.md
```

## Características de la Arquitectura

### 1. **Core Module** (`/app/core`)
- Contiene servicios singleton que se usan globalmente
- `ApiService`: Centraliza todas las llamadas HTTP al backend
- Se importa solo en `AppModule`

### 2. **Shared Module** (`/app/shared`)
- Modelos (interfaces) para tipo seguridad
- Componentes reutilizables
- Pipes y directivas personalizadas
- Importable en cualquier feature module

### 3. **Features** (`/app/features`)
Módulos independientes por dominio de negocio:

#### **Students Feature**
- Gestión de estudiantes
- Listado de estudiantes
- Dashboard principal

#### **Promoters Feature**
- Gestión de promotores
- Listado de promotores
- Selección de promotores en formularios

#### **Enrollments Feature**
- Gestión de matrículas
- Formulario de registro
- Envío de datos al backend

## Ventajas de esta Arquitectura

✅ **Escalabilidad**: Fácil agregar nuevas features sin afectar existentes
✅ **Mantenibilidad**: Cada feature es autocontenida y modular
✅ **Reutilización**: Core y Shared centralizan código común
✅ **Separación de responsabilidades**: Servicios, componentes y modelos separados
✅ **Testabilidad**: Cada módulo puede testearse independientemente
✅ **Lazy Loading**: Posibilidad de cargar features bajo demanda

## Instalación y Ejecución

### Requisitos
- Node.js 18+ 
- Angular CLI 17+

### Instalación
```bash
cd frontend
npm install
```

### Desarrollo
```bash
npm start
# Acceder en http://localhost:4200
```

### Build Producción
```bash
npm run build
```

## Módulos Importados

- **BrowserModule**: Para soporte del navegador
- **HttpClientModule**: Para llamadas HTTP
- **ReactiveFormsModule**: Para formularios reactivos
- **FormsModule**: Para formularios template-driven
- **RouterModule**: Para navegación

## Próximos Pasos

1. Instalar dependencias: `npm install`
2. Configurar la URL del API en `environment.ts` y `environment.prod.ts`
3. Ejecutar el servidor de desarrollo: `npm start`
4. Agregar más features siguiendo el mismo patrón

## Convenciones de Código

- Nombres de componentes: `NombreComponent`
- Nombres de archivos: `nombre.component.ts`
- Servicios: `NombreService` en `nombre.service.ts`
- Modelos/Interfaces: `NombreModel` en `nombre.model.ts`
- Métodos privados: prefijo `_` o palabra clave `private`
