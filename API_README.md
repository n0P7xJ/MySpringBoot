# Spring Boot REST API

## Опис проекту

REST API для роботи з React додатком. Додаток надає ендпоінти для управління:
- Продуктами (Products)
- Категоріями (Categories)
- Користувачами (Users)

## Технології

- **Spring Boot 3.5.7**
- **Java 21**
- **H2 Database** (in-memory)
- **Spring Data JPA**
- **Spring Security** (з CORS налаштуваннями)
- **Swagger/OpenAPI 3** (документація API)
- **MapStruct** (маппінг DTO)
- **Lombok**

## Запуск додатку

```bash
./mvnw spring-boot:run
```

Додаток запускається на порту **8080**.

## API Endpoints

### Products API
- `GET /api/products` - Отримати всі продукти
- `GET /api/products?search={query}` - Пошук продуктів
- `POST /api/products` - Створити новий продукт

### Categories API
- `GET /api/categories` - Отримати всі категорії
- `GET /api/categories/{id}` - Отримати категорію за ID
- `POST /api/categories` - Створити нову категорію

### Users API
- `GET /api/users` - Отримати всіх користувачів
- `GET /api/users/{id}` - Отримати користувача за ID

## Swagger UI

Документація API доступна за адресою:
```
http://localhost:8080/swagger-ui/index.html
```

Swagger надає інтерактивний інтерфейс для тестування всіх API ендпоінтів.

## OpenAPI Specification

JSON специфікація доступна за адресою:
```
http://localhost:8080/v3/api-docs
```

## CORS Configuration

API налаштовано для роботи з React додатками на портах:
- `http://localhost:3000` (Create React App)
- `http://localhost:5173` (Vite)

Дозволені HTTP методи: GET, POST, PUT, DELETE, PATCH, OPTIONS

## База даних

Проект використовує H2 in-memory базу даних.

**H2 Console** доступна за адресою:
```
http://localhost:8080/h2-console
```

Параметри підключення:
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: _(порожній)_

## Приклади запитів

### Отримати всі продукти
```bash
curl http://localhost:8080/api/products
```

### Пошук продуктів
```bash
curl "http://localhost:8080/api/products?search=laptop"
```

### Створити новий продукт
```bash
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Новий продукт",
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
