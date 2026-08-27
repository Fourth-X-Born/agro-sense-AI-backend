# AgroSense AI — Backend

Spring Boot REST API powering **AgroSense AI**, a farming intelligence platform built for Sri Lankan farmers. It serves weather-driven crop risk analysis, market prices, crop cultivation guides, fertilizer recommendations, and a full admin management panel, all behind JWT-secured endpoints.

## What it does

- **Farmer accounts** — registration/login, two-step profile completion (district + crop selection), profile photo upload via Cloudinary
- **Weather-driven crop risk analysis** — a rule-based scoring engine combines live temperature/humidity data (OpenWeatherMap) with crop-specific sensitivity factors to produce a risk score, level, explanation, and actionable recommendations; history is saved per farmer
- **Weather & alerts** — current conditions and forecasts per district, with severity-classified alerts
- **Market prices** — crop/district-filterable pricing data, fully admin-managed (no hardcoded values)
- **Crop guide** — growth stages, DO/DON'T guidelines, and crop details per crop
- **Fertilizer recommendations** — crop-specific type, dosage, and application guidance
- **Contact/support** — public contact form, admin-side inbox with status tracking
- **Admin panel API** — CRUD for crops, districts, market prices, fertilizers, crop guides, growth stages, plus read-only farmer listing and contact message management

## Tech stack

- **Java 17**, **Spring Boot 4** (Web, Data JPA, Security, Validation)
- **MySQL** (via `mysql-connector-j`)
- **JWT authentication** — [`jjwt`](https://github.com/jwtk/jjwt), stateless sessions, role-based route protection (`FARMER` / `ADMIN` / `SUPER_ADMIN`)
- **Cloudinary** — profile photo storage
- **springdoc-openapi** — Swagger UI for interactive API docs
- **Lombok**, **dotenv-java** (loads a local `.env` into the JVM at startup)

## Architecture

Standard layered structure: `Controller → Service (interface) → ServiceImpl → Repository → Entity`, with DTOs shaping every request/response and a single `@RestControllerAdvice` (`GlobalExceptionHandler`) for consistent error responses. Security is handled by a `JwtAuthenticationFilter` registered ahead of Spring Security's default filter chain, validating Bearer tokens and populating the security context; `SecurityConfig` then applies route-level rules (see below).

### API overview

| Controller | Base path | Access |
| --- | --- | --- |
| `AuthController` | `/api/auth` | Public (register/login) |
| `AdminAuthController` | `/api/admin/auth` | Public (register/login) |
| `HealthController` | `/api/health` | Public |
| `ContactController` | `/api/contact` | Public (POST) |
| `CropController`, `DistrictController`, `WeatherController`, `MarketPriceController`, `CropGuideController`, `FertilizerController`, `WeatherAlertController` | various | Public (GET — reference/read data) |
| `ProfileController` | `/api/profile` | Requires a **farmer** token — id is taken from the token itself, not the request |
| `RiskController` | `/api/risk` | Requires a **farmer** token — same |
| `AdminController` | `/api/admin` | Requires an **admin/super-admin** token |

## Getting started

### Prerequisites

- **Java 17+**
- **Maven**
- A **MySQL** database (local, or a free-tier cloud instance — e.g. Aiven, PlanetScale)

### 1. Environment variables

All secrets are read from environment variables — nothing is hardcoded in `application.properties`. Copy `.env.example` to `.env` in the project root and fill in real values:

```env
DB_URL=jdbc:mysql://localhost:3306/agrosense_db
DB_USERNAME=root
DB_PASSWORD=your_local_mysql_password

WEATHER_API_KEY=your_openweathermap_api_key

CLOUDINARY_CLOUD_NAME=your_cloudinary_cloud_name
CLOUDINARY_API_KEY=your_cloudinary_api_key
CLOUDINARY_API_SECRET=your_cloudinary_api_secret

JWT_SECRET=a_long_random_base64_string   # e.g. openssl rand -base64 64
JWT_EXPIRATION_MS=86400000
```

`.env` is gitignored and loaded automatically at startup (see `AgrosenseBackendApplication.main`) — no extra setup needed beyond creating the file.

### 2. Build & run

```bash
mvn clean install
mvn spring-boot:run
```

### 3. Explore the API

- Server: `http://localhost:8080`
- **Swagger UI**: `http://localhost:8080/swagger-ui/index.html` — browse and try every endpoint interactively. For protected routes, log in via `/api/auth/login` or `/api/admin/auth/login` to get a JWT, then click **"Authorize"** in Swagger and paste `Bearer <token>`.

### Deployment

Configured for deployment on Railway (`Procfile`, `application-railway.properties` — reads DB credentials from Railway's auto-injected `MYSQL*` variables). Any platform that can run a Spring Boot jar with environment variables works equally well.

## Team

**Fourth X Born** — DEV-55

- Vibhath Kalsara
- Isuru Adikaram
- Ashen Randira
- Dileepa Prabhath
- Chanuka Ushan
