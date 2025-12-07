import React, { useState, useCallback } from 'react';
import { uploadPhoto, uploadPhotoFromUrl, deletePhoto } from '../services/photoService';
import './PhotoUpload.css';

const PhotoUpload = ({ onPhotosChange, initialPhotos = [] }) => {
  const [photos, setPhotos] = useState(initialPhotos);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  // Завантаження фото з файлу
  const handleFileUpload = async (event) => {
    const files = Array.from(event.target.files);
    setLoading(true);
    setError(null);

    try {
      const uploadPromises = files.map(file => uploadPhoto(file));
      const uploadedPhotos = await Promise.all(uploadPromises);
      
      const newPhotos = [...photos, ...uploadedPhotos];
      setPhotos(newPhotos);
      onPhotosChange(newPhotos.map(p => p.id));
    } catch (err) {
      setError('Помилка при завантаженні фото: ' + err.message);
    } finally {
      setLoading(false);
    }
  };

  // Перехоплення вставки (paste)
  const handlePaste = useCallback(async (event) => {
    const items = event.clipboardData?.items;
    if (!items) return;

    setLoading(true);
    setError(null);

    try {
      const uploadPromises = [];

      for (let item of items) {
        // Перевіряємо чи це зображення
        if (item.type.indexOf('image') !== -1) {
          const file = item.getAsFile();
          if (file) {
            uploadPromises.push(uploadPhoto(file));
          }
        }
        // Перевіряємо чи це текст (можливо URL)
        else if (item.type === 'text/plain') {
          item.getAsString(async (text) => {
            // Перевіряємо чи це URL зображення
            if (isImageUrl(text)) {
              try {
                const uploadedPhoto = await uploadPhotoFromUrl(text);
                const newPhotos = [...photos, uploadedPhoto];
                setPhotos(newPhotos);
                onPhotosChange(newPhotos.map(p => p.id));
              } catch (err) {
                setError('Помилка при завантаженні з URL: ' + err.message);
              }
            }
          });
        }
      }

      if (uploadPromises.length > 0) {
        const uploadedPhotos = await Promise.all(uploadPromises);
        const newPhotos = [...photos, ...uploadedPhotos];
        setPhotos(newPhotos);
        onPhotosChange(newPhotos.map(p => p.id));
      }
    } catch (err) {
      setError('Помилка при обробці вставки: ' + err.message);
    } finally {
      setLoading(false);
    }
  }, [photos, onPhotosChange]);

  // Перевірка чи це URL зображення
  const isImageUrl = (text) => {
    const imageExtensions = /\.(jpg|jpeg|png|gif|webp|bmp|svg)$/i;
    const urlPattern = /^https?:\/\/.+/i;
    return urlPattern.test(text) && (imageExtensions.test(text) || text.includes('image'));
  };

  // Видалення фото
  const handleDelete = async (photoId) => {
    try {
      await deletePhoto(photoId);
      const newPhotos = photos.filter(p => p.id !== photoId);
      setPhotos(newPhotos);
      onPhotosChange(newPhotos.map(p => p.id));
    } catch (err) {
      setError('Помилка при видаленні фото: ' + err.message);
    }
  };

  return (
    <div 
      className="photo-upload-container"
      onPaste={handlePaste}
      tabIndex={0}
    >
      <div className="upload-section">
        <label className="upload-label">
          <input
            type="file"
            multiple
            accept="image/*"
            onChange={handleFileUpload}
            className="file-input"
            disabled={loading}
          />
          <span className="upload-button">
            📁 Вибрати фото
          </span>
        </label>
        <p className="upload-hint">
          Або вставте фото (Ctrl+V) / URL зображення
        </p>
      </div>

      {error && (
        <div className="error-message">
          {error}
        </div>
      )}

      {loading && (
        <div className="loading">
          Завантаження...
        </div>
      )}

      <div className="photos-grid">
        {photos.map(photo => (
          <div key={photo.id} className="photo-item">
            <img 
              src={photo.url} 
              alt={photo.filename}
              className="photo-preview"
            />
            <button
              onClick={() => handleDelete(photo.id)}
              className="delete-button"
              title="Видалити"
            >
              ❌
            </button>
          </div>
        ))}
      </div>

      {photos.length === 0 && !loading && (
        <div className="empty-state">
          Фото ще не додано
        </div>
      )}
    </div>
  );
};

export default PhotoUpload;
