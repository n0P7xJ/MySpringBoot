package com.student.myspringboot.repository;

import com.student.myspringboot.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    Optional<Photo> findByFilename(String filename);
    
    List<Photo> findByCityId(Long cityId);
    
    List<Photo> findByIsAttachedFalseAndUploadedAtBefore(LocalDateTime cutoffTime);
    
    void deleteByFilename(String filename);
}
