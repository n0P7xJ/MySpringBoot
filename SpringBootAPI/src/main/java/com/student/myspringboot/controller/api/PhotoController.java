package com.student.myspringboot.controller.api;

import com.student.myspringboot.entity.Photo;
import com.student.myspringboot.service.PhotoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/photos")
@RequiredArgsConstructor
@Tag(name = "Photos", description = "API для управління фотографіями")
public class PhotoController {

    private final PhotoService photoService;

    @PostMapping("/upload")
    @Operation(summary = "Завантажити фото", description = "Завантажує фото на сервер (без прив'язки до міста)")
    public ResponseEntity<?> uploadPhoto(@RequestParam("file") MultipartFile file) {
        try {
            Photo photo = photoService.uploadPhoto(file);
            
            Map<String, Object> response = new HashMap<>();
            response.put("id", photo.getId());
            response.put("filename", photo.getFilename());
            response.put("url", "/uploads/" + photo.getFilename());
            response.put("uploadedAt", photo.getUploadedAt());
            
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/upload-from-url")
    @Operation(summary = "Завантажити фото з URL", description = "Завантажує фото з вказаного URL на сервер")
    public ResponseEntity<?> uploadPhotoFromUrl(@RequestBody Map<String, String> request) {
        try {
            String imageUrl = request.get("url");
            if (imageUrl == null || imageUrl.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "URL не вказано"));
            }

            Photo photo = photoService.uploadPhotoFromUrl(imageUrl);
            
            Map<String, Object> response = new HashMap<>();
            response.put("id", photo.getId());
            response.put("filename", photo.getFilename());
            response.put("url", "/uploads/" + photo.getFilename());
            response.put("originalUrl", photo.getOriginalUrl());
            response.put("uploadedAt", photo.getUploadedAt());
            
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/city/{cityId}")
    @Operation(summary = "Отримати фото міста", description = "Повертає всі фото прив'язані до міста")
    public ResponseEntity<List<Photo>> getPhotosByCity(@PathVariable Long cityId) {
        List<Photo> photos = photoService.getPhotosByCity(cityId);
        return ResponseEntity.ok(photos);
    }

    @DeleteMapping("/{photoId}")
    @Operation(summary = "Видалити фото", description = "Видаляє фото з сервера")
    public ResponseEntity<?> deletePhoto(@PathVariable Long photoId) {
        try {
            photoService.deletePhoto(photoId);
            return ResponseEntity.ok(Map.of("message", "Фото успішно видалено"));
        } catch (IOException e) {
            return ResponseEntity.internalServerError()
                .body(Map.of("error", "Помилка при видаленні фото: " + e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{photoId}/detach")
    @Operation(summary = "Від'єднати фото від міста", description = "Від'єднує фото від міста")
    public ResponseEntity<?> detachPhoto(@PathVariable Long photoId) {
        try {
            photoService.detachPhotoFromCity(photoId);
            return ResponseEntity.ok(Map.of("message", "Фото від'єднано від міста"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/cleanup")
    @Operation(summary = "Очистити непідв'язані фото", description = "Видаляє непідв'язані фото старіші за вказаний час")
    public ResponseEntity<?> cleanupUnattachedPhotos() {
        int deletedCount = photoService.cleanupUnattachedPhotos();
        return ResponseEntity.ok(Map.of(
            "message", "Очищення завершено",
            "deletedCount", deletedCount
        ));
    }
}
