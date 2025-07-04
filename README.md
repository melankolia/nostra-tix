# Nostra Tix - Movie Ticket Booking System

A Spring Boot application for movie ticket booking with PostgreSQL database and MinIO object storage.

## Prerequisites

Before running this application, make sure you have the following installed:

- **Java 17 or higher**
- **Maven 3.6+**
- **Docker and Docker Compose**
- **Git**

## Quick Start

### 1. Clone the Repository

```bash
git clone https://github.com/melankolia/nostra-tix.git
cd nostra-tix
```

### 2. Start Infrastructure Services

First, start the required infrastructure services (PostgreSQL database and MinIO object storage) using Docker Compose:

```bash
sudo docker compose up
```

This command will start:
- **PostgreSQL Database** on port 5433 (with pre-seeded data)
- **MinIO Object Storage** on port 9000 (API) and 9001 (Web UI)

The database will be automatically initialized with sample data including movies, theaters, schedules, and other necessary data for testing the application.

Wait for both services to be fully started before proceeding to the next step.

### 3. Run the Spring Boot Application

In a new terminal window, run the Spring Boot application using Maven:

```bash
mvn spring-boot:run
```

The application will start on port 8080 by default.

## Application Access

Once both steps are completed successfully:

- **Main Application**: http://localhost:8080
- **MinIO Web UI**: http://localhost:9001
- **API Documentation**: http://localhost:8080/swagger-ui.html (if Swagger is configured)

## Project Structure

```
nostra-tix/
├── src/main/java/com/tix/nostra/nostra_tix/
│   ├── controller/          # REST API controllers
│   ├── service/            # Business logic services
│   ├── repository/         # Data access layer
│   ├── domain/            # Entity models
│   ├── dto/               # Data Transfer Objects
│   ├── security/          # Authentication & authorization
│   ├── config/            # Configuration classes
│   └── util/              # Utility classes
├── src/main/resources/
│   ├── application.yml    # Application configuration
│   └── db/migration/      # Database migration scripts
├── docker-compose.yml     # Infrastructure services
└── pom.xml               # Maven dependencies
```

## Features

- **User Management**: Registration, authentication, and user profiles
- **Movie Management**: CRUD operations for movies
- **Booking System**: Ticket booking with seat selection
- **Payment Integration**: Payment processing for bookings
- **File Storage**: MinIO integration for file uploads
- **Security**: JWT-based authentication and authorization

## Configuration

The application configuration is located in `src/main/resources/application.yml`. Key configurations include:

- Database connection settings
- MinIO object storage configuration
- JWT token settings
- Email service configuration

## Stopping the Application

To stop the application:

1. **Stop Spring Boot**: Press `Ctrl+C` in the terminal running the Maven command
2. **Stop Infrastructure**: Run `sudo docker compose down` to stop PostgreSQL and MinIO