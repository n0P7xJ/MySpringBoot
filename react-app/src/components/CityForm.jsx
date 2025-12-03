import React, { useState, useMemo, useRef } from 'react';
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';
import { useNavigate } from 'react-router-dom';
import { cityService } from '../services/cityService';
import { fileService } from '../services/fileService';
import Input from './common/Input';
import CountrySelect from './common/CountrySelect';
import './CityForm.css';

function CityForm() {
  const navigate = useNavigate();
  const quillRef = useRef(null);
  
  const [formData, setFormData] = useState({
    name: '',
    region: '',
    country: '',
    population: '',
    postalCode: '',
    latitude: '',
    longitude: '',
    description: ''
  });

  const [errors, setErrors] = useState({});
  const [loading, setLoading] = useState(false);
  const [success, setSuccess] = useState('');

  // Image upload handler for Quill editor
  const imageHandler = () => {
    const input = document.createElement('input');
    input.setAttribute('type', 'file');
    input.setAttribute('accept', 'image/*');
    input.click();

    input.onchange = async () => {
      const file = input.files[0];
      if (file) {
        try {
          const formData = new FormData();
          formData.append('file', file);
          
          const result = await fileService.uploadImage(file);
          const imageUrl = `http://localhost:8080${result.url}`;
          
          const quill = quillRef.current.getEditor();
          const range = quill.getSelection(true);
          quill.insertEmbed(range.index, 'image', imageUrl);
        } catch (error) {
          console.error('Error uploading image:', error);
          alert('Помилка завантаження зображення');
        }
      }
    };
  };

  const modules = useMemo(() => ({
    toolbar: {
      container: [
        [{ 'header': [1, 2, 3, 4, 5, 6, false] }],
        ['bold', 'italic', 'underline', 'strike'],
        [{ 'color': [] }, { 'background': [] }],
        [{ 'list': 'ordered'}, { 'list': 'bullet' }],
        [{ 'align': [] }],
        ['link', 'image', 'video'],
        ['clean']
      ],
      handlers: {
        image: imageHandler
      }
    }
  }), []);

  const formats = [
    'header',
    'bold', 'italic', 'underline', 'strike',
    'color', 'background',
    'list', 'bullet',
    'align',
    'link', 'image', 'video'
  ];

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

  const handleDescriptionChange = (value) => {
    setFormData({
      ...formData,
      description: value,
    });
    if (errors.description) {
      setErrors({
        ...errors,
        description: '',
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
        description: ''
      });

      // Redirect after 2 seconds
      setTimeout(() => {
        navigate('/cities');
      }, 2000);

    } catch (err) {
      if (err.response?.data && typeof err.response.data === 'object') {
        setErrors(err.response.data);
      } else {
        setErrors({
          general: err.response?.data?.message || 'Помилка створення міста. Спробуйте ще раз.',
        });
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="city-form-container">
      <div className="city-form-card">
        <h1>Створити нове місто</h1>

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

          <div className="form-group">
            <label className="editor-label">
              Опис міста
              <span className="editor-hint"> (можна додавати зображення)</span>
            </label>
            <ReactQuill
              ref={quillRef}
              theme="snow"
              value={formData.description}
              onChange={handleDescriptionChange}
              modules={modules}
              formats={formats}
              placeholder="Введіть опис міста з можливістю додавання зображень..."
              className={errors.description ? 'quill-error' : ''}
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
              {loading ? 'Створення...' : 'Створити місто'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default CityForm;
