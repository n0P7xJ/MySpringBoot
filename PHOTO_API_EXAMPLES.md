# API Documentation - Photo Management

## Photo API Usage Examples

### 1. Upload Photo from File

```bash
curl -X POST http://localhost:8080/api/photos/upload \
  -F "file=@/path/to/image.jpg"
```

**Response:**
```json
{
  "id": 1,
  "filename": "a1b2c3d4-e5f6-7890-abcd-ef1234567890.jpg",
  "url": "/uploads/a1b2c3d4-e5f6-7890-abcd-ef1234567890.jpg",
  "uploadedAt": "2024-12-06T10:30:00"
}
```

### 2. Upload Photo from URL

```bash
curl -X POST http://localhost:8080/api/photos/upload-from-url \
  -H "Content-Type: application/json" \
  -d '{
    "url": "https://example.com/city-image.jpg"
  }'
```

**Response:**
```json
{
  "id": 2,
  "filename": "b2c3d4e5-f6a7-8901-bcde-f12345678901.jpg",
  "url": "/uploads/b2c3d4e5-f6a7-8901-bcde-f12345678901.jpg",
  "originalUrl": "https://example.com/city-image.jpg",
  "uploadedAt": "2024-12-06T10:35:00"
}
```

### 3. Create City with Photos

```bash
curl -X POST http://localhost:8080/api/cities \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Kyiv",
    "region": "Kyiv Oblast",
    "country": "Ukraine",
    "population": 2950000,
    "postalCode": "01001",
    "latitude": 50.4501,
    "longitude": 30.5234,
    "description": "Capital of Ukraine",
    "photoIds": [1, 2]
  }'
```

### 4. Get City Photos

```bash
curl http://localhost:8080/api/photos/city/1
```

**Response:**
```json
[
  {
    "id": 1,
    "filename": "a1b2c3d4-e5f6-7890-abcd-ef1234567890.jpg",
    "filePath": "uploads/a1b2c3d4-e5f6-7890-abcd-ef1234567890.jpg",
    "uploadedAt": "2024-12-06T10:30:00",
    "isAttached": true,
    "originalUrl": null
  },
  {
    "id": 2,
    "filename": "b2c3d4e5-f6a7-8901-bcde-f12345678901.jpg",
    "filePath": "uploads/b2c3d4e5-f6a7-8901-bcde-f12345678901.jpg",
    "uploadedAt": "2024-12-06T10:35:00",
    "isAttached": true,
    "originalUrl": "https://example.com/city-image.jpg"
  }
]
```

### 5. Delete Photo

```bash
curl -X DELETE http://localhost:8080/api/photos/1
```

**Response:**
```json
{
  "message": "Photo successfully deleted"
}
```

### 6. Detach Photo from City

```bash
curl -X POST http://localhost:8080/api/photos/1/detach
```

**Response:**
```json
{
  "message": "Photo detached from city"
}
```

### 7. Manual Cleanup of Unattached Photos

```bash
curl -X POST http://localhost:8080/api/photos/cleanup
```

**Response:**
```json
{
  "message": "Cleanup completed",
  "deletedCount": 5
}
```

## Frontend Examples (React)

### Using PhotoUpload Component

```jsx
import PhotoUpload from './components/PhotoUpload';

function CityForm() {
  const [photoIds, setPhotoIds] = useState([]);

  return (
    <form>
      {/* Other form fields */}
      
      <PhotoUpload 
        onPhotosChange={setPhotoIds}
        initialPhotos={[]}
      />
      
      {/* Photos are automatically uploaded via drag&drop or Ctrl+V */}
    </form>
  );
}
```

### Upload Photos Programmatically

```javascript
import { uploadPhoto, uploadPhotoFromUrl } from './services/photoService';

// Upload from file
const file = document.getElementById('fileInput').files[0];
const photo = await uploadPhoto(file);
console.log('Uploaded:', photo);

// Upload from URL
const photoFromUrl = await uploadPhotoFromUrl('https://example.com/image.jpg');
console.log('Downloaded and uploaded:', photoFromUrl);
```

## Workflow Scenario

1. **User opens city creation form**
2. **Uploads photos**:
   - Clicks "Select Photo" button OR
   - Pastes Ctrl+V image/URL
3. **Photos are saved** to `photos` table with `is_attached = false`
4. **Fills city form**
5. **Clicks "Create City"**
6. **Backend**:
   - Creates city
   - Attaches photos (sets `city_id` and `is_attached = true`)
7. **Unattached photos** (if user didn't save city) are deleted after 24 hours

## Configuration

In `application.properties`:
```properties
# Cleanup time for unattached photos (hours)
photo.cleanup.hours=24

# Upload directory
file.upload-dir=uploads
```
