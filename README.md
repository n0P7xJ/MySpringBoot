# MySpringBoot Project

A full-stack web application with separated backend and frontend.

## Project Structure

```
MySpringBoot/
├── javaapi/           # Backend (Spring Boot REST API)
│   ├── src/
│   ├── pom.xml
│   └── mvnw
├── front/             # Frontend (React + Vite)
│   ├── src/
│   ├── package.json
│   └── vite.config.js
├── API_README.md
├── PHOTO_SYSTEM_README.md
└── PHOTO_API_EXAMPLES.md
```

## Quick Start

### Backend (javaapi)

```bash
cd javaapi
./mvnw spring-boot:run
```

Backend will be available at http://localhost:8080

### Frontend (front)

```bash
cd front
npm install
npm run dev
```

Frontend will be available at http://localhost:5173

## Documentation

- [API Documentation](API_README.md)
- [Photo System Guide](PHOTO_SYSTEM_README.md)
- [Photo API Examples](PHOTO_API_EXAMPLES.md)
- [SendGrid Setup](SENDGRID_SETUP.md)
- [Backend README](javaapi/README.md)
- [Frontend README](front/README.md)

## Tech Stack

### Backend
- Java 17+
- Spring Boot 3.x
- Spring Data JPA
- H2 Database
- Lombok
- Spring Security
- SendGrid (email)
- Swagger/OpenAPI

### Frontend
- React 18
- Vite
- React Router
- Redux Toolkit
- Axios
- React Quill (rich text editor)

## Features

- 🏙️ City management with rich text descriptions
- 📸 Photo management system with automatic cleanup
- 👥 User authentication and registration
- 📦 Product catalog with categories
- 🔐 Secure API with CORS configuration
- 📧 Email notifications via SendGrid
- 📝 Rich text editor for content
- 🖼️ Image upload from files and URLs
- ⏰ Scheduled tasks for maintenance
