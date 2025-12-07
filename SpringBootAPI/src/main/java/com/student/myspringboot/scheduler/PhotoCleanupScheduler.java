package com.student.myspringboot.scheduler;

import com.student.myspringboot.service.PhotoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PhotoCleanupScheduler {

    private final PhotoService photoService;

    /**
     * Запускається кожні 6 годин
     * Видаляє непідв'язані фото старіші за вказаний час (за замовчуванням 24 години)
     */
    @Scheduled(fixedRate = 21600000) // 6 годин = 6 * 60 * 60 * 1000 мс
    public void cleanupUnattachedPhotos() {
        log.info("Запуск очищення непідв'язаних фото...");
        int deletedCount = photoService.cleanupUnattachedPhotos();
        log.info("Очищення завершено. Видалено {} фото", deletedCount);
    }
}
