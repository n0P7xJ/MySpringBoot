и# Spring Boot REST API

## Overview

REST API backend for the React application. Provides endpoints for managing:
- Products
- Categories
- Cities
- Users
- Photos

## Technologies

- **Spring Boot 3.5.7**
- **Java 21**
- **H2 Database** (in-memory)
- **Spring Data JPA**
- **Spring Security** (with CORS configuration)
- **Swagger/OpenAPI 3** (API documentation)
- **MapStruct** (DTO mapping)
- **Lombok**
- **Scheduling** (for automatic photo cleanup)

## Running the Application

```bash
cd javaapi
./mvnw spring-boot:run
```

The application runs on port **8080**.

## API Endpoints

### Products API
- `GET /api/products` - Get all products
- `GET /api/products?search={query}` - Search products
- `POST /api/products` - Create a new product

### Categories API
- `GET /api/categories` - Get all categories
- `GET /api/categories/{id}` - Get category by ID
- `POST /api/categories` - Create a new category

### Cities API
- `GET /api/cities` - Get all cities
- `GET /api/cities/{id}` - Get city by ID
- `POST /api/cities` - Create a new city
- `DELETE /api/cities/{id}` - Delete a city

### Users API
- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `POST /api/users/register` - Register a new user

### Photos API
- `POST /api/photos/upload` - Upload photo from file
- `POST /api/photos/upload-from-url` - Upload photo from URL
- `GET /api/photos/city/{cityId}` - Get city photos
- `DELETE /api/photos/{photoId}` - Delete photo
- `POST /api/photos/cleanup` - Cleanup unattached photos

## Swagger UI

API documentation is available at:
```
http://localhost:8080/swagger-ui/index.html
```

Swagger provides an interactive interface for testing all API endpoints.

## OpenAPI Specification

JSON specification is available at:
```
http://localhost:8080/v3/api-docs
```

## CORS Configuration

API is configured to work with React applications on ports:
- `http://localhost:3000` (Create React App)
- `http://localhost:5173` (Vite)

Allowed HTTP methods: GET, POST, PUT, DELETE, PATCH, OPTIONS

## Database

The project uses H2 in-memory database.

**H2 Console** is available at:
```
http://localhost:8080/h2-console
```

Connection parameters:
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: _(empty)_

## Request Examples

### Get all products
```bash
curl http://localhost:8080/api/products
```

### Search products
```bash
curl "http://localhost:8080/api/products?search=laptop"
```

### Create a new product
```bash
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "New Product",
    "description": "Опис продукту",
    "price": 999.99,
    "quantity": 10,
    "sku": "SKU-001"
  }'
```

### Отримати всі категорії
```bash
curl http://localhost:8080/api/categories
```

### Отримати всіх користувачів
```bash
curl http://localhost:8080/api/users
```

## Інтеграція з React

Приклад використання у React додатку:

```javascript
// Отримати всі продукти
fetch('http://localhost:8080/api/products')
  .then(response => response.json())
  .then(data => console.log(data));

// Створити новий продукт
fetch('http://localhost:8080/api/products', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
  },
  body: JSON.stringify({
    name: 'Новий продукт',
    description: 'Опис',
    price: 100.00,
    quantity: 5,
    sku: 'SKU-123'
  })
})
  .then(response => response.json())
  .then(data => console.log(data));
```

## Структура проекту

```
src/main/java/com/student/myspringboot/
├── config/
│   ├── SecurityConfig.java       # CORS та Security налаштування
│   └── OpenApiConfig.java        # Swagger/OpenAPI конфігурація
├── controller/
│   └── api/
│       ├── ProductApiController.java
│       ├── CategoryApiController.java
│       └── UserApiController.java
├── dto/
│   ├── ProductDto.java
│   └── UserDto.java
├── entity/
│   ├── Product.java
│   ├── Category.java
│   └── User.java
├── repository/
│   ├── ProductRepository.java
│   ├── CategoryRepository.java
│   └── UserRepository.java
└── service/
    ├── ProductService.java
    └── UserService.java
```

## Тестові дані

При запуску додатку автоматично завантажуються тестові дані:
- Категорії: Електроніка, Одяг, Книги, Для дому, Спорт
- Продукти: 50 випадкових продуктів
- Користувачі: admin@example.com, user@example.com
