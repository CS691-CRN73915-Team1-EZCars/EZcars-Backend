# EZCars Backend Overview

This is the backend service for the EZCars car rental platform, built with Spring Boot. It provides RESTful APIs to support the frontend application, handling user authentication, vehicle management, booking processes, and administrative functions.

## Technologies

- **Java 17**
- **Spring Boot 3.x**
- **Spring Security**
- **MySQL**
- **Maven**

## Prerequisites

Before you begin, ensure you have met the following requirements:

- **Java Development Kit (JDK) 17 or later**
- **Maven 3.6+**

## Setup and Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/CS691-CRN73915-Team1-EZCars/EZcars-Backend.git
   ```

2. **Navigate to the project directory:**
   ```bash
   cd EZCars-backend
   ```

3. **Configure the database connection** in `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/ezcars
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

4. **Build the project:**
   ```bash
   mvn clean install
   ```

5. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

6. **Access the server:**
   The server will start on [http://localhost:8082](http://localhost:8082).

---

## Happy Coding with EZCars Backend!

