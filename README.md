# Employee Success & Expense Management System

A portfolio-ready full-stack application with employee profiles, leave workflows, expense/Finance approval, JWT authentication, role authorization, REST APIs, MySQL, exception handling, logging, and JUnit tests.

## Architecture

React/Vite UI -> Axios + JWT -> Spring Boot REST API -> Spring Data JPA -> MySQL

Roles: EMPLOYEE submits leave and expenses; MANAGER reviews leave; FINANCE reviews expenses; ADMIN creates users and can review both.

## Prerequisites

- Java 21
- Maven 3.6.3+
- Node.js 22 LTS
- Docker Desktop (recommended for MySQL) or MySQL 8.x
- Git and VS Code

Recommended VS Code extensions: Extension Pack for Java, Spring Boot Extension Pack, ESLint, and Docker.

## Run locally

### 1. Start MySQL

From the project root:

```bash
docker compose up -d
```

Or create a local MySQL database named `employee_success` and override `DB_USERNAME` and `DB_PASSWORD`.

### 2. Start backend

```bash
cd backend
mvn spring-boot:run
```

API: `http://localhost:8080/api`

### 3. Start frontend in a second VS Code terminal

```bash
cd frontend
npm install
npm run dev
```

UI: `http://localhost:5173`

## Demo accounts

All use password `Password@123`.

| Role | Email |
|---|---|
| Employee | employee@demo.com |
| Manager | manager@demo.com |
| Finance | finance@demo.com |
| Admin | admin@demo.com |

## Test

```bash
cd backend
mvn test
```

## API summary

- `POST /api/auth/login` public login
- `POST /api/leaves` submit leave
- `GET /api/leaves/mine` employee leave history
- `GET /api/leaves` Manager/Admin queue
- `PATCH /api/leaves/{id}/decision` Manager/Admin approval
- `POST /api/expenses` submit expense
- `GET /api/expenses/mine` employee expense history
- `GET /api/expenses` Finance/Admin queue
- `PATCH /api/expenses/{id}/decision` Finance/Admin approval
- `GET/POST /api/users` Admin user management

## GitHub upload

Create an empty GitHub repository, then from this project root run:

```bash
git init -b main
git add .
git commit -m "Initial commit: employee success and expense system"
git remote add origin https://github.com/YOUR_USERNAME/employee-success-expense-system.git
git push -u origin main
```

Do not commit real passwords, JWT secrets, `.env`, or production database URLs.

## Deployment option

### Railway: backend + MySQL

1. Create a Railway project from the GitHub repository.
2. Add a MySQL service.
3. Add a backend service with root directory `backend`.
4. Build command: `mvn clean package -DskipTests`.
5. Start command: `java -jar target/employee-success-api-1.0.0.jar`.
6. Set `DB_URL=jdbc:mysql://${MYSQLHOST}:${MYSQLPORT}/${MYSQLDATABASE}`, `DB_USERNAME=${MYSQLUSER}`, `DB_PASSWORD=${MYSQLPASSWORD}`, `JWT_SECRET` to a random 64+ character value, and `FRONTEND_URL` to the deployed frontend URL.

### Render: frontend

Create a Static Site from the same repository with root directory `frontend`, build command `npm install && npm run build`, publish directory `dist`, and environment variable `VITE_API_URL=https://YOUR_BACKEND_DOMAIN/api`. Then update the backend `FRONTEND_URL` and redeploy.

## Interview explanation

**Problem:** Manual leave and expense processing is slow and lacks visibility. This application centralizes employee requests and enforces separate approval responsibilities.

**Backend:** Spring Boot exposes stateless REST APIs. Spring Security authenticates email/password, BCrypt verifies hashes, and the API issues a signed JWT. A request filter validates the Bearer token. Method-level role checks restrict Manager, Finance, and Admin operations. Spring Data JPA maps users, leave requests, and expenses to MySQL tables. Bean Validation and a global exception handler give consistent errors.

**Frontend:** React components implement login, dashboard, submission forms, approval queues, and user administration. Axios attaches the JWT to outgoing requests. Role-aware rendering shows only the workflows each user needs. Vite provides the development server and production build.

**Database:** `users` has one-to-many relationships with leave requests and expenses. Status values are PENDING, APPROVED, or REJECTED. Foreign keys preserve ownership and relational consistency.

**Testing:** JUnit, Spring Boot Test, MockMvc, and Spring Security Test verify API behavior. Postman can test login, authorization, validation, and workflows manually.

**Production improvements:** refresh tokens, email notifications, receipt upload to object storage, pagination, audit trail, Flyway migrations, OpenAPI/Swagger, Testcontainers, CI with GitHub Actions, rate limiting, and cloud secret management.
