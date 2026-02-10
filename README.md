# AgroSense AI - Backend Local Setup

## Prerequisites

* **Java 17** or higher
* **Maven**
* **MySQL Database**

## Configuration

1. **Database Setup**

   * Ensure your local MySQL server is running.
   * Create a database named `agrosense_db`.

2. **Application Properties**

   * The application relies on `src/main/resources/application.properties`.
   * For local development, ensure `spring.profiles.active=local` is set (or manually uncomment local settings).

   **Local Credentials (Example):**

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/agrosense_db
   spring.datasource.username=root
   spring.datasource.password=YOUR_PASSWORD
   ```

3. **Environment Variables (.env)**

   * Create a `.env` file in the root directory if it doesn't exist.
   * Required variables:

     ```
     OPENWEATHER_API_KEY=your_openweather_api_key
     ```

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

* **Vibhath Kalsara**
* **Isuru Adikaram**
* **Ashen Randira**
* **Dileepa Prabhath**
* **Chanuka Ushan**
