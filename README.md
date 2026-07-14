# 💰 Finance Tracker Backend

A RESTful Finance Tracker backend built using **Spring Boot** that helps users manage their personal finances.

The goal of this project is not only to build a finance management application but also to understand how real-world backend applications are designed using layered architecture, REST APIs, Spring Boot, Hibernate, and MySQL.

This project is being developed step-by-step while following industry-standard backend development practices.

---

## 🚀 Current Status

### ✅ Completed
- User Management Module
- REST API Development
- CRUD Operations
- Request Validation
- Global Exception Handling
- DTO Architecture
- Generic API Response
- MySQL Integration
- Hibernate & Spring Data JPA

### 🚧 In Progress
- Transaction Module

### 📌 Planned
- Categories
- Monthly Reports
- Dashboard
- JWT Authentication
- Password Encryption (BCrypt)
- Docker Deployment

---

# ✨ Features

### User Management

- Register a new user
- Get all users
- Get user by ID
- Update user details
- Delete user

### Validation

- Request validation using Bean Validation
- Duplicate email checking
- Proper error messages

### Exception Handling

- Centralized Global Exception Handler
- Consistent API responses

### Security Improvements

- DTOs prevent exposing sensitive information
- Password is never returned in API responses

---

# 🛠 Tech Stack

### Backend

- Java 20
- Spring Boot
- Spring MVC
- Spring Data JPA
- Hibernate

### Database

- MySQL

### Build Tool

- Maven

### API Testing

- Postman

---

# 🏗 Architecture

The project follows a layered architecture.

```
Client
   │
HTTP Request
   │
   ▼
Controller
   │
   ▼
Service
   │
   ▼
Repository
   │
   ▼
Hibernate (JPA)
   │
   ▼
MySQL
```

### Layer Responsibilities

### Controller

- Handles HTTP requests
- Validates incoming requests
- Calls the Service layer
- Returns API responses

---

### Service

- Contains business logic
- Performs validation
- Checks business rules
- Coordinates application flow

---

### Repository

- Communicates with the database
- Uses Spring Data JPA
- No SQL queries are written manually

---

### Entity

Represents database tables.

---

### DTO

Used for transferring data between the backend and client without exposing database entities.

---

# 📂 Project Structure

```
src
└── main
    ├── java
    │
    └── com.anurag.financetracker
        │
        ├── controller
        ├── service
        ├── repository
        ├── entity
        ├── dto
        ├── exception
        └── FinanceTrackerBackendApplication.java
        │
        └── resources
            └── application.properties
```

---

# 🌐 REST APIs

| Method | Endpoint | Description |
|---------|----------|-------------|
| POST | `/users/register` | Register a new user |
| GET | `/users` | Get all users |
| GET | `/users/{id}` | Get user by ID |
| PUT | `/users/{id}` | Update user |
| DELETE | `/users/{id}` | Delete user |

---

# 📌 Sample Response

```json
{
    "success": true,
    "message": "User fetched successfully",
    "data": {
        "id": 1,
        "name": "Anurag",
        "email": "anurag@gmail.com"
    }
}
```

---

# ⚙️ Getting Started

## 1. Clone the repository

```bash
git clone https://github.com/YOUR_USERNAME/FinanceTracker.git
```

## 2. Open the backend project

Open the project using:

- IntelliJ IDEA
- Spring Tool Suite (STS)
- Eclipse

---

## 3. Configure MySQL

Update the database configuration inside:

```
src/main/resources/application.properties
```

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/finance_tracker

spring.datasource.username=YOUR_USERNAME

spring.datasource.password=YOUR_PASSWORD
```

---

## 4. Run the project

Using Maven:

```bash
mvn spring-boot:run
```

Or simply run

```
FinanceTrackerBackendApplication.java
```

---

# 📖 What I Learned While Building This Project

- Spring Boot fundamentals
- Layered Architecture
- REST API Design
- Spring MVC
- Dependency Injection
- Spring Data JPA
- Hibernate ORM
- DTO Design Pattern
- Validation
- Global Exception Handling
- Generic API Responses
- Java Streams
- Method References
- Java Generics

---

# 🛣 Roadmap

- [x] User Module
- [ ] Transaction Module
- [ ] Categories
- [ ] Monthly Reports
- [ ] Dashboard
- [ ] JWT Authentication
- [ ] Password Encryption
- [ ] Docker
- [ ] Deployment

---

# 🤝 Contributing

Suggestions and improvements are always welcome.

Feel free to fork the repository, create a new branch, and submit a pull request.

---

# 👨‍💻 Author

**Anurag Laha**

Built as part of my journey to learn Spring Boot, backend development, and real-world software architecture.