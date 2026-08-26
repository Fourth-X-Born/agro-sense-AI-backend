# AgroSense AI - Backend Local Setup

## Prerequisites

* **Java 17** or higher
* **Maven**
* **MySQL Database**

## Configuration

1. **Database Setup**

   * Ensure your local MySQL server is running.
   * Create a database named `agrosense_db`.

2. **Environment Variables (.env)**

   * All secrets (DB credentials, API keys) are read from environment variables — nothing is hardcoded in `application.properties`.
   * Copy `.env.example` to `.env` in the project root and fill in real values:

     ```env
     DB_URL=jdbc:mysql://localhost:3306/agrosense_db
     DB_USERNAME=root
     DB_PASSWORD=your_local_mysql_password

     WEATHER_API_KEY=your_openweathermap_api_key

     CLOUDINARY_CLOUD_NAME=your_cloudinary_cloud_name
     CLOUDINARY_API_KEY=your_cloudinary_api_key
     CLOUDINARY_API_SECRET=your_cloudinary_api_secret
     ```

   * `.env` is gitignored and loaded automatically on startup (see `AgrosenseBackendApplication.main`) — no extra setup needed beyond creating the file.

## Running the Application

1. **Build the Project**

   ```bash
   mvn clean install
   ```

2. **Run the Application**

   ```bash
   mvn spring-boot:run
   ```

3. **Access the API**

   * Server runs on: `http://localhost:8080`
   * Swagger UI: `http://localhost:8080/swagger-ui.html` (if enabled)

## Team

* **Name: Fourth X Born**

* **Number: DEV - 55**

## Members

* **Vibhath Kalsara**
* **Isuru Adikaram**
* **Ashen Randira**
* **Dileepa Prabhath**
* **Chanuka Ushan**
