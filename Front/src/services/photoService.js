import api from './api';

/**
 * Завантажує фото на сервер
 */
export const uploadPhoto = async (file) => {
  const formData = new FormData();
  formData.append('file', file);

  const response = await api.post('/photos/upload', formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  });

  return response.data;
};

/**
 * Завантажує фото з URL на сервер
 */
export const uploadPhotoFromUrl = async (url) => {
  const response = await api.post('/photos/upload-from-url', { url });
  return response.data;
};

/**
 * Отримує фото міста
 */
export const getCityPhotos = async (cityId) => {
  const response = await api.get(`/photos/city/${cityId}`);
  return response.data;
};

/**
 * Видаляє фото
 */
export const deletePhoto = async (photoId) => {
  const response = await api.delete(`/photos/${photoId}`);
  return response.data;
};

/**
 * Від'єднує фото від міста
 */
export const detachPhoto = async (photoId) => {
  const response = await api.post(`/photos/${photoId}/detach`);
  return response.data;
};
