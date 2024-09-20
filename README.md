# CRUD-App
# Device Management System

This project provides a RESTful API for managing devices. It allows users to perform CRUD operations on device entities, as well as search for devices by brand.

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [API Endpoints](#api-endpoints)
- [Testing](#testing)

## Features

- Create new devices
- Retrieve device information by ID
- List all devices
- Update device information (full and partial updates)
- Delete devices
- Search devices by brand

## Technologies Used

- Java 17
- Spring Boot 3.3.3
- Spring Data JPA
- H2 Database (for development and testing)
- Lombok
- JUnit 5 and Mockito for testing
- Maven

## Project Structure

The project follows the below application structure:

```
src
├── main
│   ├── java
│   │   └── com
│   │       └── challenge
│   │           └── burcakkocak
│   │               ├── controller
│   │               ├── entity
│   │               │   └── dto
│   │               ├── exception
│   │               ├── mapper
│   │               ├── repo
│   │               └── service
│   └── resources
│       └── application.properties
└── test
    └── java
        └── com
            └── challenge
                └── burcakkocak
                    ├── controller
                    └── service
```

## Getting Started

1. Clone the repository
2. Ensure you have Java 17 and Maven installed
3. Navigate to the project root directory
4. Run `mvn clean install` to build the project
5. Run `mvn spring-boot:run` to start the application

The application will start on `http://localhost:8080`.

## API Endpoints

- `POST /device`: Add a new device
- `GET /device/{id}`: Get a device by ID
- `GET /device`: List all devices
- `PUT /device/{id}`: Update a device (full update)
- `PATCH /device/{id}`: Update a device partially
- `DELETE /device/{id}`: Delete a device
- `GET /device/search?brand=<brand>`: Search devices by brand

## API Request Examples

Here are examples of how to use the API endpoints:

### Add a new device

```bash
curl -X POST http://localhost:8080/device \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Smartphone X",
    "brand": "TechCorp",
    "creationTime": "2023-09-18T10:00:00"
  }'
```

### Get a device by ID

```bash
curl -X GET http://localhost:8080/device/1
```

### List all devices

```bash
curl -X GET http://localhost:8080/device
```

### Update a device (full update)

```bash
curl -X PUT http://localhost:8080/device/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Smartphone X Pro",
    "brand": "TechCorp",
    "creationTime": "2023-09-18T11:00:00"
  }'
```

### Update a device partially

```bash
curl -X PATCH http://localhost:8080/device/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Smartphone X Pro Max"
  }'
```

### Delete a device

```bash
curl -X DELETE http://localhost:8080/device/1
```

### Search devices by brand

```bash
curl -X GET "http://localhost:8080/device/search?brand=TechCorp"
```

## Testing

The project includes unit tests for both the controller and service layers. To run the tests, use the following command:

```
mvn test
```

## Logging

Logging is implemented throughout the application using SLF4J. Log levels can be configured in the application.properties file.
