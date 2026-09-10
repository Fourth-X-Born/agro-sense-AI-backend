# AgroSense - Backend

**Live Web:** `https://agrosense-web.netlify.app`

Spring Boot REST API powering AgroSense, an agricultural decision-support web platform built for Sri Lankan farmers. It serves weather-driven crop risk analysis, market prices, crop cultivation guides, fertilizer recommendations, and a full admin management panel, all behind JWT-secured endpoints.

**Live API Endpoint:** `https://agro-sense-backend-km1l.onrender.com/api`

## What it does

- **Farmer accounts**: registration/login, two-step profile completion (district + crop selection), profile photo upload via Cloudinary
- **Weather-driven crop risk analysis**: a rule-based scoring engine combines live temperature/humidity data (OpenWeatherMap) with crop-specific sensitivity factors to produce a risk score, level, explanation, and actionable recommendations. History is saved per farmer.
- **Weather & alerts**: current conditions and forecasts per district, with severity-classified alerts
- **Market prices**: crop/district-filterable pricing data, fully admin-managed (no hardcoded values)
- **Crop guide**: growth stages, DO/DON'T guidelines, and crop details per crop
- **Fertilizer recommendations**: crop-specific type, dosage, and application guidance
- **Contact/support**: public contact form, admin-side inbox with status tracking
- **Admin panel API**: CRUD for crops, districts, market prices, fertilizers, crop guides, and growth stages, plus read-only farmer listing and contact message management

## Tech stack

- **Java 17**, **Spring Boot 3** (Web, Data JPA, Security, Validation)
- **MySQL** (via `mysql-connector-j`)
- **JWT authentication**: [`jjwt`](https://github.com/jwtk/jjwt), stateless sessions, role-based route protection (`FARMER` / `ADMIN` / `SUPER_ADMIN`)
- **Jakarta Bean Validation**: Strict data integrity using standard annotations (`@NotBlank`, `@Email`, `@Pattern`) with a unified field-level error response format mapped by `GlobalExceptionHandler`.
- **Docker**: Fully containerized with a multi-stage `Dockerfile` for streamlined builds and deployments.
- **Cloudinary** for profile photo storage
- **springdoc-openapi** for Swagger UI
- **Lombok**, **dotenv-java** (loads a local `.env` file into the JVM at startup)

## Architecture

Standard layered structure: `Controller -> Service (interface) -> ServiceImpl -> Repository -> Entity`, with DTOs shaping every request/response and a single `@RestControllerAdvice` (`GlobalExceptionHandler`) for consistent error responses. Security is handled by a `JwtAuthenticationFilter` registered ahead of Spring Security's default filter chain, validating Bearer tokens and populating the security context. `SecurityConfig` then applies route-level rules, summarized below.

### API overview

| Controller | Base path | Access |
| --- | --- | --- |
| `AuthController` | `/api/auth` | Public (register/login) |
| `AdminAuthController` | `/api/admin/auth` | Public (register/login) |
| `HealthController` | `/api/health` | Public |
| `ContactController` | `/api/contact` | Public (POST) |
| `CropController`, `DistrictController`, `WeatherController`, `MarketPriceController`, `CropGuideController`, `FertilizerController`, `WeatherAlertController` | various | Public (GET, reference/read data) |
| `ProfileController` | `/api/profile` | Requires a farmer token. The farmer id is taken from the token itself, not the request. |
| `RiskController` | `/api/risk` | Requires a farmer token. Same as above. |
| `AdminController` | `/api/admin` | Requires an admin/super-admin token |

## Getting started

### Prerequisites

- Java 17 or higher
- Maven
- A MySQL database (local, or a free-tier cloud instance such as Aiven or PlanetScale)

### 1. Set up your environment variables

All secrets are read from environment variables. Nothing is hardcoded in `application.properties`.

1. Copy `.env.example` to a new file named `.env` in the project root (next to `pom.xml`).
2. Open `.env.example` for the full list of required variables and what each one is for (database connection, OpenWeatherMap key, Cloudinary credentials, JWT secret).
3. Fill in real values in your `.env`.

`.env` is gitignored and loaded automatically at startup (see `AgrosenseBackendApplication.main`). No extra setup is needed beyond creating the file.

### 2. Build and run

```bash
mvn clean install
mvn spring-boot:run
```

### 3. Explore the API

- Server: `http://localhost:8080`
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`. Browse and try every endpoint interactively. For protected routes, log in via `/api/auth/login` or `/api/admin/auth/login` to get a JWT, then click "Authorize" in Swagger and paste `Bearer <token>`.

### Deployment

Configured for containerized deployment on platforms like Render or Railway. 
It includes a multi-stage `Dockerfile` that builds the application using Maven and runs it using the lightweight OpenJDK slim runtime.

To deploy on Render, create a new **Web Service**, select **Docker** as the environment, and configure the following environment variables in the Render dashboard:
`DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `WEATHER_API_KEY`, `CLOUDINARY_*`, `JWT_SECRET`.

## Team

Fourth X Born, DEV-55

- Vibhath Kalsara
- Isuru Adikaram
- Ashen Randira
- Dileepa Prabhath
- Chanuka Bandara
