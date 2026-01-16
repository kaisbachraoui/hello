# Project Security Findings Hub

A full-stack web application to manage projects, standardized risks, and project security findings.
It provides a centralized Risk Library, links each Finding to exactly one Risk entry, and delivers
reporting dashboards and exports.

## Architecture
- **Frontend:** Angular 17+ standalone components, Angular Material, RxJS, SCSS
- **Backend:** Java 21, Spring Boot 3, Spring Security (JWT), Spring Data JPA, MapStruct, Flyway
- **Database:** PostgreSQL
- **API:** REST with OpenAPI/Swagger
- **Local Dev:** Docker Compose (postgres + backend + frontend)

## Database Schema (Tables & Relationships)
- **user_accounts** (id, username, password_hash)
- **user_roles** (user_id → user_accounts.id, role)
- **risks** (risk_id PK, domain, title, description, typical_finding_examples, annex_a_mapping)
- **projects** (id PK, name, description, business_owner, technical_owner, start_date, go_live_date, status)
- **findings** (id PK, project_id → projects.id, risk_id → risks.risk_id, title, description, severity, likelihood, impact, risk_level, status, owner, due_date, created_at)
- **finding_control_tags** (finding_id → findings.id, control_tag)
- **finding_comments** (id PK, finding_id → findings.id, author, comment, created_at)
- **finding_history** (id PK, finding_id → findings.id, actor, from_status, to_status, action, details, created_at)
- **evidence** (id PK, finding_id → findings.id, filename, content_type, size, storage_path, url, created_at)

## Backend API Endpoints (REST)
### Auth
- `POST /api/auth/login`

### Risks
- `GET /api/risks?domain=&keyword=`
- `GET /api/risks/{riskId}`
- `POST /api/risks`
- `PUT /api/risks/{riskId}`
- `DELETE /api/risks/{riskId}`
- `POST /api/risks/import` (multipart .xlsx)

### Projects
- `GET /api/projects`
- `GET /api/projects/{id}`
- `POST /api/projects`
- `PUT /api/projects/{id}`
- `DELETE /api/projects/{id}`

### Findings
- `GET /api/findings?projectId=&status=`
- `GET /api/findings/{id}`
- `POST /api/findings`
- `PUT /api/findings/{id}`
- `DELETE /api/findings/{id}`
- `GET /api/findings/{findingId}/comments`
- `POST /api/findings/{findingId}/comments`
- `GET /api/findings/{findingId}/history`
- `POST /api/findings/{findingId}/history`
- `GET /api/findings/{findingId}/evidence`
- `POST /api/findings/{findingId}/evidence`

### Reporting
- `GET /api/reports/dashboard`
- `GET /api/reports/projects/{projectId}/findings/export`

## Frontend Structure (Angular Standalone Components)
- **Pages**
  - `LoginComponent`
  - `DashboardComponent`
  - `RisksComponent`
  - `ProjectsComponent`
  - `FindingsComponent`
  - `FindingDetailsComponent`
- **Services**
  - `AuthService` (JWT handling)
  - `ApiService` (REST calls)
  - `authInterceptor` (attach JWT)

## Local Development
### Prerequisites
- Docker + Docker Compose

### Run Stack
```bash
docker-compose up --build
```

### Default Admin User
- **Username:** `admin`
- **Password:** `admin123`

### Useful Links
- Frontend: <http://localhost:4200>
- Backend API: <http://localhost:8080>
- Swagger UI: <http://localhost:8080/swagger-ui.html>

## Notes
- Backend uses UTC for timestamps; frontend displays in local time.
- Evidence files are stored on disk under `storage/evidence` in the backend container.
- Pagination and sorting are available on list endpoints via Spring `Pageable` query params.
