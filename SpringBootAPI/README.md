# Java API (Backend)

Spring Boot REST API для управління містами, користувачами, продуктами та фотографіями.

## Технології

- **Java 17+**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **Spring Security**
- **H2 Database** (in-memory)
- **Lombok**
- **SendGrid** (email service)
- **Swagger/OpenAPI** (API documentation)

## Запуск

### Використовуючи Maven Wrapper:

```bash
./mvnw spring-boot:run
```

### Або через Maven:

```bash
mvn spring-boot:run
```

Сервер запуститься на http://localhost:8080

## Основні ендпоінти

### Cities API
- `GET /api/cities` - отримати всі міста
- `GET /api/cities/{id}` - отримати місто за ID
- `POST /api/cities` - створити нове місто
- `PUT /api/cities/{id}` - оновити місто
- `DELETE /api/cities/{id}` - видалити місто

### Photos API
- `POST /api/photos/upload` - завантажити фото
- `POST /api/photos/upload-from-url` - завантажити фото з URL
- `GET /api/photos/city/{cityId}` - отримати фото міста
- `DELETE /api/photos/{id}` - видалити фото

### Users API
- `GET /api/users` - отримати всіх користувачів
- `POST /api/users/register` - реєстрація користувача
- `POST /api/users/login` - вхід користувача

### Products API
- `GET /api/products` - отримати всі продукти
- `GET /api/products/{id}` - отримати продукт за ID
- `POST /api/products` - створити новий продукт
- `PUT /api/products/{id}` - оновити продукт
- `DELETE /api/products/{id}` - видалити продукт

## Swagger UI

Після запуску сервера документація API доступна за адресою:
http://localhost:8080/swagger-ui.html

## H2 Console

Консоль бази даних H2 доступна за адресою:
http://localhost:8080/h2-console

**Параметри підключення:**
- JDBC URL: `jdbc:h2:mem:testdb`
- User: `sa`
- Password: (порожнє)

## Конфігурація

Основні налаштування знаходяться в `src/main/resources/application.properties`

### База даних
```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.jpa.hibernate.ddl-auto=update
```

### Завантаження файлів
```properties
file.upload-dir=uploads
spring.servlet.multipart.max-file-size=10MB
```

### Очищення фото
```properties
photo.cleanup.hours=24
```

### Email (SendGrid)
```properties
spring.mail.host=smtp.sendgrid.net
spring.mail.username=apikey
spring.mail.password=${SENDGRID_API_KEY}
```

## Структура пакетів

```
src/main/java/com/student/myspringboot/
├── config/          # Конфігурація (Security, CORS, File Upload)
├── controller/      # REST контролери
│   └── api/        
├── dto/            # Data Transfer Objects
├── entity/         # JPA сутності
├── repository/     # Spring Data JPA репозиторії
├── service/        # Бізнес логіка
├── scheduler/      # Заплановані завдання
└── mapper/         # Маперы (якщо є)
```

## Функції

### Система управління фотографіями
- Завантаження фото з файлів або URL
- Автоматичне очищення непідв'язаних фото (кожні 6 годин)
- Прив'язка фото до міст
- Зберігання в окремій таблиці

### Автентифікація
- Реєстрація користувачів
- Вхід/вихід
- Spring Security

### Email сповіщення
- Відправка email через SendGrid
- Скидання пароля

## Build

```bash
./mvnw clean package
```

JAR файл буде створено в `target/myspringboot-0.0.1-SNAPSHOT.jar`

## Запуск JAR

```bash
java -jar target/myspringboot-0.0.1-SNAPSHOT.jar
```
