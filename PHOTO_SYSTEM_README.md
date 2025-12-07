# Photo Management System for Cities

## Overview

A complete photo management system with a separate database table and automatic cleanup of unattached files.

## Key Features

### Backend (Spring Boot)

#### 1. Photo Entity
- **Table**: `photos`
- **Fields**:
  - `id` - unique identifier
  - `filename` - unique filename
  - `file_path` - full path to file on server
  - `uploaded_at` - upload timestamp
  - `city_id` - relationship with city (nullable)
  - `is_attached` - whether photo is attached to a city
  - `original_url` - original URL (if uploaded from URL)

#### 2. Relationship with City
- **OneToMany** relationship: one city can have many photos
- Photos are deleted when city is deleted (`orphanRemoval = true`)

#### 3. PhotoService
Main methods:
- `uploadPhoto(MultipartFile file)` - upload photo from file
- `uploadPhotoFromUrl(String url)` - upload photo from URL
- `attachPhotosToCity(List<Long> photoIds, City city)` - attach photos to city
- `detachPhotoFromCity(Long photoId)` - detach photo from city
- `cleanupUnattachedPhotos()` - cleanup unattached photos

#### 4. REST API Endpoints

**POST /api/photos/upload**
- Upload photo from file
- Parameter: `file` (MultipartFile)
- Response: `{ id, filename, url, uploadedAt }`

**POST /api/photos/upload-from-url**
- Upload photo from URL
- Body: `{ "url": "https://example.com/image.jpg" }`
- Response: `{ id, filename, url, originalUrl, uploadedAt }`

**GET /api/photos/city/{cityId}**
- Get all photos of a city

**DELETE /api/photos/{photoId}**
- Delete photo

**POST /api/photos/{photoId}/detach**
- Detach photo from city

**POST /api/photos/cleanup**
- Manual cleanup of unattached photos

#### 5. Automatic Cleanup
- **PhotoCleanupScheduler** - runs every 6 hours
- Deletes photos not attached to a city for more than 24 hours
- Cleanup time is configurable via `photo.cleanup.hours` in `application.properties`

### Frontend (React)

#### 1. PhotoUpload Component
Interactive component for working with photos:

**Capabilities**:
- Upload via "Select Photo" button
- Drag & Drop files (planned)
- **Paste (Ctrl+V)**:
  - Paste image files
  - Paste image URLs (automatic upload to server)
- Preview uploaded photos
- Delete photos

**Usage**:
```jsx
<PhotoUpload 
  onPhotosChange={(photoIds) => console.log(photoIds)}
  initialPhotos={[]}
/>
```

#### 2. CityForm Updated
- Integrated `PhotoUpload` component
- Intercepts image URL paste in Quill editor
- Automatic upload of images from URL to server

#### 3. photoService.js
Service for working with photo API:
```javascript
uploadPhoto(file)
uploadPhotoFromUrl(url)
getCityPhotos(cityId)
deletePhoto(photoId)
detachPhoto(photoId)
```

## Configuration

### application.properties
```properties
# File upload directory
file.upload-dir=uploads

# Cleanup time for unattached photos (hours)
photo.cleanup.hours=24
```

## Workflow

### Creating a City with Photos:

1. **User uploads photos**:
   - Via "Select Photo" button
   - Via Ctrl+V (paste file or URL)
   - Photos are stored in `photos` table with `is_attached = false`

2. **Filling city form**:
   - Enter name, region, country, etc.
   - Photos are already uploaded and displayed

3. **Saving city**:
   - City record is created
   - Photos are attached to city (`is_attached = true`)
   - Field `city_id` is set

### Cleanup of Unattached Photos:

1. **Automatically** (every 6 hours):
   - Scheduler finds photos where `is_attached = false` and `uploaded_at < 24 hours ago`
   - Deletes physical files
   - Deletes records from DB

2. **Manually**:
   - Call `POST /api/photos/cleanup`

## System Advantages

1. ✅ **Separate table** - photos are stored independently from cities
2. ✅ **Automatic cleanup** - no garbage files
3. ✅ **URL support** - can upload images from internet
4. ✅ **Paste interception** - convenient UX (Ctrl+V)
5. ✅ **Cascading delete** - photos are deleted when city is deleted
6. ✅ **Validation** - file type checking
7. ✅ **Unique names** - UUID to avoid conflicts

## Future Improvements

- [ ] Image compression
- [ ] Thumbnails
- [ ] Drag & Drop in PhotoUpload
- [ ] Upload progress bar
- [ ] Multiple selection support in gallery
- [ ] Photo sorting
- [ ] Set main photo for city
