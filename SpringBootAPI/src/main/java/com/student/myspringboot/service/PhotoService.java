package com.student.myspringboot.service;

import com.student.myspringboot.entity.City;
import com.student.myspringboot.entity.Photo;
import com.student.myspringboot.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PhotoService {

    private final PhotoRepository photoRepository;

    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

    @Value("${photo.cleanup.hours:24}")
    private int cleanupHours;

    /**
     * Завантажує фото з MultipartFile
     */
    public Photo uploadPhoto(MultipartFile file) throws IOException {
        validateImageFile(file);

        String filename = generateUniqueFilename(file.getOriginalFilename());
        String filePath = saveFile(file.getInputStream(), filename);

        Photo photo = new Photo();
        photo.setFilename(filename);
        photo.setFilePath(filePath);
        photo.setIsAttached(false);

        return photoRepository.save(photo);
    }

    /**
     * Завантажує фото з URL
     */
    public Photo uploadPhotoFromUrl(String imageUrl) throws IOException {
        try {
            java.net.URI uri = java.net.URI.create(imageUrl);
            String filename = generateUniqueFilename(getFilenameFromUrl(imageUrl));

            try (InputStream inputStream = uri.toURL().openStream()) {
                String filePath = saveFile(inputStream, filename);

                Photo photo = new Photo();
                photo.setFilename(filename);
                photo.setFilePath(filePath);
                photo.setOriginalUrl(imageUrl);
                photo.setIsAttached(false);

                return photoRepository.save(photo);
            }
        } catch (Exception e) {
            log.error("Помилка при завантаженні фото з URL: {}", imageUrl, e);
            throw new IOException("Не вдалося завантажити фото з URL: " + imageUrl, e);
        }
    }

    /**
     * Прив'язує фото до міста
     */
    public void attachPhotosToCity(List<Long> photoIds, City city) {
        if (photoIds == null || photoIds.isEmpty()) {
            return;
        }

        for (Long photoId : photoIds) {
            Photo photo = photoRepository.findById(photoId)
                    .orElseThrow(() -> new IllegalArgumentException("Фото з ID " + photoId + " не знайдено"));
            
            photo.setCity(city);
            photo.setIsAttached(true);
            photoRepository.save(photo);
        }
    }

    /**
     * Від'єднує фото від міста
     */
    public void detachPhotoFromCity(Long photoId) {
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new IllegalArgumentException("Фото з ID " + photoId + " не знайдено"));
        
        photo.setCity(null);
        photo.setIsAttached(false);
        photoRepository.save(photo);
    }

    /**
     * Отримує всі фото міста
     */
    public List<Photo> getPhotosByCity(Long cityId) {
        return photoRepository.findByCityId(cityId);
    }

    /**
     * Видаляє фото
     */
    public void deletePhoto(Long photoId) throws IOException {
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new IllegalArgumentException("Фото з ID " + photoId + " не знайдено"));

        deletePhysicalFile(photo.getFilePath());
        photoRepository.delete(photo);
    }

    /**
     * Очищає непідв'язані фото старіші за вказаний час
     */
    public int cleanupUnattachedPhotos() {
        LocalDateTime cutoffTime = LocalDateTime.now().minusHours(cleanupHours);
        List<Photo> unattachedPhotos = photoRepository.findByIsAttachedFalseAndUploadedAtBefore(cutoffTime);

        int deletedCount = 0;
        for (Photo photo : unattachedPhotos) {
            try {
                deletePhysicalFile(photo.getFilePath());
                photoRepository.delete(photo);
                deletedCount++;
                log.info("Видалено непідв'язане фото: {}", photo.getFilename());
            } catch (IOException e) {
                log.error("Помилка при видаленні файлу: {}", photo.getFilePath(), e);
            }
        }

        log.info("Очищено {} непідв'язаних фото", deletedCount);
        return deletedCount;
    }

    // Допоміжні методи

    private void validateImageFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("Файл порожній");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IOException("Дозволені тільки зображення");
        }
    }

    private String generateUniqueFilename(String originalFilename) {
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        } else {
            extension = ".jpg";
        }
        return UUID.randomUUID().toString() + extension;
    }

    private String saveFile(InputStream inputStream, String filename) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(filename);
        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

        return filePath.toString();
    }

    private void deletePhysicalFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        if (Files.exists(path)) {
            Files.delete(path);
        }
    }

    private String getFilenameFromUrl(String url) {
        String[] parts = url.split("/");
        String lastPart = parts[parts.length - 1];
        
        // Видаляємо query parameters
        if (lastPart.contains("?")) {
            lastPart = lastPart.substring(0, lastPart.indexOf("?"));
        }
        
        return lastPart.isEmpty() ? "image.jpg" : lastPart;
    }
}
