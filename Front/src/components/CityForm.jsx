import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { cityService } from '../services/cityService';
import { getCityPhotos } from '../services/photoService';
import Input from './common/Input';
import CountrySelect from './common/CountrySelect';
import PhotoUpload from './PhotoUpload';
import './CityForm.css';

function CityForm() {
  const navigate = useNavigate();
  const { id } = useParams();
  const isEditMode = !!id;
  
  const [formData, setFormData] = useState({
    name: '',
    region: '',
    country: '',
    population: '',
    postalCode: '',
    latitude: '',
    longitude: '',
    description: '',
    imageUrl: '',
    photoIds: []
  });

  const [photos, setPhotos] = useState([]);
  const [errors, setErrors] = useState({});
  const [loading, setLoading] = useState(false);
  const [success, setSuccess] = useState('');

  // Завантаження даних міста для редагування
  useEffect(() => {
    if (isEditMode) {
      loadCityData();
    }
  }, [id]);

  const loadCityData = async () => {
    try {
      setLoading(true);
      const cityData = await cityService.getCityById(id);
      
      setFormData({
        name: cityData.name || '',
        region: cityData.region || '',
        country: cityData.country || '',
        population: cityData.population?.toString() || '',
        postalCode: cityData.postalCode || '',
        latitude: cityData.latitude?.toString() || '',
        longitude: cityData.longitude?.toString() || '',
        description: cityData.description || '',
        imageUrl: cityData.imageUrl || '',
        photoIds: []
      });

      // Завантажуємо фото міста
      if (cityData.id) {
        const cityPhotos = await getCityPhotos(cityData.id);
        setPhotos(cityPhotos);
      }
    } catch (err) {
      setErrors({
        general: 'Помилка завантаження даних міста: ' + err.message
      });
    } finally {
      setLoading(false);
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
    if (errors[name]) {
      setErrors({
        ...errors,
        [name]: '',
      });
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setSuccess('');

    try {
      setLoading(true);
      setErrors({});

      // Convert string numbers to actual numbers
      const cityData = {
        ...formData,
        population: formData.population ? parseInt(formData.population) : null,
        latitude: formData.latitude ? parseFloat(formData.latitude) : null,
        longitude: formData.longitude ? parseFloat(formData.longitude) : null,
      };

      if (isEditMode) {
        // Режим редагування
        await cityService.updateCity(id, cityData);
        setSuccess('Місто успішно оновлено!');
      } else {
        // Режим створення
        await cityService.createCity(cityData);
        setSuccess('Місто успішно створено!');
        
        // Reset form
        setFormData({
          name: '',
          region: '',
          country: '',
          population: '',
          postalCode: '',
          latitude: '',
          longitude: '',
          description: '',
          imageUrl: '',
          photoIds: []
        });
        setPhotos([]);
      }

      // Redirect after 2 seconds
      setTimeout(() => {
        navigate('/cities');
      }, 2000);

    } catch (err) {
      if (err.response?.data && typeof err.response.data === 'object') {
        setErrors(err.response.data);
      } else {
        setErrors({
          general: err.response?.data?.message || `Помилка ${isEditMode ? 'оновлення' : 'створення'} міста. Спробуйте ще раз.`,
        });
      }
    } finally {
      setLoading(false);
    }
  };

  const handlePhotosChange = (photosData) => {
    if (isEditMode) {
      // В режимі редагування отримуємо масив об'єктів фото
      setPhotos(photosData);
      setFormData({
        ...formData,
        photoIds: photosData.map(p => p.id)
      });
    } else {
      // В режимі створення отримуємо масив ID
      setFormData({
        ...formData,
        photoIds: photosData
      });
    }
  };

  console.log('About to render, loading:', loading, 'isEditMode:', isEditMode);

  if (loading && isEditMode) {
    return (
      <div className="city-form-container">
        <div className="loading">Завантаження даних міста...</div>
      </div>
    );
  }

  return (
    <div className="city-form-container">
      <div className="city-form-card">
        <h1>{isEditMode ? 'Редагувати місто' : 'Створити нове місто'}</h1>

        {success && <div className="success-message">{success}</div>}
        {errors.general && <div className="error-message">{errors.general}</div>}

        <form onSubmit={handleSubmit} className="city-form">
          <div className="form-row">
            <Input
              label="Назва міста"
              name="name"
              value={formData.name}
              onChange={handleChange}
              error={errors.name}
              placeholder="Київ"
              required
            />

            <Input
              label="Регіон"
              name="region"
              value={formData.region}
              onChange={handleChange}
              error={errors.region}
              placeholder="Київська область"
            />
          </div>

          <CountrySelect
            value={formData.country}
            onChange={handleChange}
            error={errors.country}
            required
          />

          <div className="form-row">
            <Input
              label="Населення"
              name="population"
              type="number"
              value={formData.population}
              onChange={handleChange}
              error={errors.population}
              placeholder="1000000"
            />

            <Input
              label="Поштовий індекс"
              name="postalCode"
              value={formData.postalCode}
              onChange={handleChange}
              error={errors.postalCode}
              placeholder="01001"
            />
          </div>

          <div className="form-row">
            <Input
              label="Широта"
              name="latitude"
              type="number"
              step="any"
              value={formData.latitude}
              onChange={handleChange}
              error={errors.latitude}
              placeholder="50.4501"
            />

            <Input
              label="Довгота"
              name="longitude"
              type="number"
              step="any"
              value={formData.longitude}
              onChange={handleChange}
              error={errors.longitude}
              placeholder="30.5234"
            />
          </div>

          <Input
            label="URL зображення міста"
            name="imageUrl"
            value={formData.imageUrl}
            onChange={handleChange}
            error={errors.imageUrl}
            placeholder="https://images.unsplash.com/photo-..."
          />

          <div className="form-group">
            <label className="editor-label">
              Фотографії міста
            </label>
            <PhotoUpload 
              onPhotosChange={handlePhotosChange}
              initialPhotos={photos}
              isEditMode={isEditMode}
            />
          </div>

          <div className="form-group">
            <label className="editor-label">
              Опис міста
            </label>
            <textarea
              name="description"
              value={formData.description}
              onChange={handleChange}
              placeholder="Введіть опис міста..."
              className={`city-description-textarea ${errors.description ? 'error' : ''}`}
              rows="8"
            />
            {errors.description && <span className="error-message">{errors.description}</span>}
          </div>

          <div className="form-actions">
            <button 
              type="button" 
              onClick={() => navigate('/cities')} 
              className="cancel-button"
              disabled={loading}
            >
              Скасувати
            </button>
            <button type="submit" disabled={loading} className="submit-button">
              {loading ? (isEditMode ? 'Оновлення...' : 'Створення...') : (isEditMode ? 'Оновити місто' : 'Створити місто')}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default CityForm;
