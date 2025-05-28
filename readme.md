# Clients Manager CRUD

A web project using Java with Spring Boot, Thymeleaf, and JPA for managing clients.

## Features

- List all clients.
- Create new clients with data validation.
- Edit client details.
- Delete clients.
- Prevent duplicate emails.
- Simple web interface using Thymeleaf and Bootstrap.

## Technologies

- Java 24
- Spring Boot
- Spring Data JPA
- Thymeleaf
- Bootstrap 5
- Relational database (configure in `application.properties`)

## How to use

1. Configure your database in `application.properties`.
2. Run the application.
3. Open `http://localhost:8080/clients` to manage clients via the web UI.

## Notes

- Create and edit operations use DTOs for validation.
- Email must be unique and cannot be duplicated.

---
Basic project for learning and CRUD client management.
