# Frontend (React + Vite)

React додаток для управління містами, користувачами та продуктами.

## Технології

- **React 18**
- **Vite** (build tool)
- **React Router** (navigation)
- **Redux Toolkit** (state management)
- **Axios** (HTTP client)
- **React Quill** (rich text editor)

## Запуск

### Встановлення залежностей:

```bash
npm install
```

### Запуск development сервера:

```bash
npm run dev
```

Додаток буде доступний на http://localhost:5173

### Build для production:

```bash
npm run build
```

Файли будуть створені в папці `dist/`

### Preview production build:

```bash
npm run preview
```

## Структура проекту

```
front/
├── src/
│   ├── components/      # React компоненти
│   │   ├── admin/       # Адмін компоненти
│   │   ├── common/      # Спільні компоненти
│   │   ├── CityForm.jsx
│   │   ├── CityList.jsx
│   │   ├── PhotoUpload.jsx
│   │   └── ...
│   ├── services/        # API сервіси
│   │   ├── api.js
│   │   ├── cityService.js
│   │   ├── photoService.js
│   │   └── ...
│   ├── store/           # Redux store
│   │   ├── store.js
│   │   ├── authSlice.js
│   │   └── themeSlice.js
│   ├── data/            # Статичні дані
│   ├── assets/          # Зображення, шрифти
│   ├── App.jsx          # Головний компонент
│   ├── main.jsx         # Entry point
│   └── index.css        # Глобальні стилі
├── public/              # Статичні файли
├── package.json
└── vite.config.js       # Vite конфігурація
```

## Основні компоненти

### CityForm
Форма створення/редагування міста з підтримкою:
- Rich text editor (React Quill)
- Завантаження фото (drag&drop, paste)
- Валідація полів
- Вибір країни

### PhotoUpload
Компонент завантаження фотографій:
- Вибір файлів
- Paste (Ctrl+V) для файлів та URL
- Автоматичне завантаження з URL на сервер
- Попередній перегляд
- Видалення фото

### CityList
Список міст з можливістю:
- Пошук
- Фільтрація
- Видалення

## API Інтеграція

Backend API доступний на http://localhost:8080

Налаштування в `src/services/api.js`:

```javascript
const API_BASE_URL = 'http://localhost:8080/api';
```

## State Management

Використовується Redux Toolkit для управління станом:

- **authSlice** - автентифікація користувача
- **themeSlice** - тема інтерфейсу (dark/light)

## Стилізація

Кожен компонент має власний CSS файл:
- `ComponentName.jsx` → `ComponentName.css`

Глобальні стилі: `src/index.css`

## Скрипти

- `npm run dev` - запуск dev сервера
- `npm run build` - build для production
- `npm run preview` - перегляд production build
- `npm run lint` - перевірка коду з ESLint

## Конфігурація

### vite.config.js

```javascript
export default defineConfig({
  plugins: [react()],
  server: {
    port: 5173,
    proxy: {
      '/api': 'http://localhost:8080'
    }
  }
})
```

## Особливості

### Фотографії
- Підтримка drag & drop
- Paste з clipboard (файли та URL)
- Автоматичне завантаження зображень з URL на backend

### Rich Text Editor
- React Quill для форматування тексту
- Вставка зображень
- Підтримка HTML

### Валідація форм
- Client-side валідація
- Відображення помилок від server

### Responsive Design
- Адаптивний дизайн для mobile/tablet/desktop
