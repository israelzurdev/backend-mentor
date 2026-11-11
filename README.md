# Backend Mentor

Plataforma de retos prácticos de arquitectura e ingeniería backend. Los usuarios resuelven especificaciones técnicas en su entorno local utilizando su propio entorno de desarrollo, Docker y suites de pruebas automatizadas, enviando la solución mediante un repositorio público de GitHub para su registro y seguimiento.

---

## Arquitectura y Stack Tecnológico

### Backend (`/api`)
- **Runtime:** Java 21 (LTS)
- **Framework:** Spring Boot 3.3.6
- **Acceso a datos:** Spring Data JPA / Hibernate 6.5
- **Base de datos:** PostgreSQL 16
- **Control de versiones de base de datos:** Flyway (migraciones reproducibles `V1..V3`)
- **Validación:** Jakarta Bean Validation (validación por contratos en records DTO)
- **Documentación OpenAPI:** Springdoc OpenAPI 2.6 / Swagger UI

### Frontend (`/web`)
- **Framework:** Angular 18 (Standalone Components, Signals, Router Input Binding)
- **Estilos:** Tailwind CSS
- **Procesamiento de Markdown:** marked (sanitizado e integrado mediante pipe standalone)
- **Cliente HTTP:** HttpClient con tipado estricto TypeScript

---

## Estructura del Repositorio

```text
backend-mentor/
├── api/                                 # API REST Spring Boot
│   ├── src/main/java/com/backendmentor/
│   │   ├── challenge/                   # Módulo de retos técnicos
│   │   ├── submission/                  # Módulo de envíos y validación de repositorios
│   │   ├── shared/                      # Manejo global de excepciones (RFC-7807) y config
│   │   └── ApiApplication.java
│   └── src/main/resources/
│       ├── db/migration/                # Scripts de migración Flyway
│       └── application.yml
├── web/                                 # Aplicación web Angular
│   └── src/app/
│       ├── core/                        # Modelos de dominio, servicios y utilidades
│       └── features/challenges/         # Vistas de catálogo y detalle interactivo
├── docker-compose.yml                   # Configuración del contenedor local de PostgreSQL
└── README.md
```

---

## Entorno Local

### Requisitos previos
- JDK 21
- Node.js 20+ y npm
- Docker Engine y Docker Compose

### 1. Base de datos
Iniciar el contenedor de PostgreSQL definido en `docker-compose.yml`:

```bash
docker compose up -d
```

### 2. Backend (Spring Boot)
Compilar y arrancar el servidor:

```bash
cd api
./mvnw spring-boot:run
```

- Endpoint base: `http://localhost:8080/api/v1`
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`

### 3. Frontend (Angular)
Instalar dependencias y levantar el servidor de desarrollo:

```bash
cd web
npm install
npm start
```

- Aplicación accesible en: `http://localhost:4200`

---

## Endpoints de la API

| Método | Ruta | Descripción | Códigos HTTP |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/v1/challenges` | Lista el catálogo de retos disponibles | `200` |
| `GET` | `/api/v1/challenges/{slug}` | Obtiene el detalle técnico y enunciado de un reto | `200`, `404` |
| `POST` | `/api/v1/challenges/{slug}/submissions` | Registra una solución verificando la URL del repositorio | `201`, `400`, `404` |
| `GET` | `/api/v1/challenges/{slug}/submissions` | Obtiene el historial de entregas de un reto específico | `200`, `404` |

---

## Criterios de Entrega de Soluciones

Las soluciones enviadas a través de la plataforma deben satisfacer las siguientes condiciones:
1. Repositorio público accesible en GitHub.
2. Archivo `README.md` con instrucciones de arranque en local, perfiles y puertos utilizados.
3. Cobertura de pruebas unitarias y de integración que verifiquen los criterios del reto.
4. Ausencia de secretos o credenciales en el código fuente (gestión mediante variables de entorno).

---

## Estado del Proyecto

- [x] Catálogo y detalle de retos técnicos.
- [x] Renderizado de especificaciones en Markdown.
- [x] Registro y persistencia de entregas con validación de URL de GitHub.
- [x] Listado reactivo de soluciones enviadas por reto.
- [ ] Integración de autenticación con Clerk (OAuth2 / GitHub).
- [ ] Módulo de evaluación técnica automatizada.