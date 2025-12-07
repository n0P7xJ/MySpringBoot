import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { cityService } from '../services/cityService';
import './CityList.css';

function CityList() {
  const navigate = useNavigate();
  const { user } = useSelector((state) => state.auth);
  const isAdmin = user?.role === 'ADMIN';
  
  const [cities, setCities] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedCity, setSelectedCity] = useState(null);

  useEffect(() => {
    loadCities();
  }, []);

  const loadCities = async () => {
    try {
      setLoading(true);
      const data = await cityService.getAllCities();
      setCities(data);
      setError(null);
    } catch (err) {
      setError('Помилка завантаження міст');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleSearch = async () => {
    try {
      setLoading(true);
      const data = await cityService.getAllCities(searchTerm);
      setCities(data);
    } catch (err) {
      setError('Помилка пошуку');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleViewCity = (city) => {
    setSelectedCity(city);
  };

  const handleCloseModal = () => {
    setSelectedCity(null);
  };

  const handleEditCity = (cityId) => {
    navigate(`/cities/edit/${cityId}`);
  };

  const handleDeleteCity = async (cityId) => {
    if (!window.confirm('Ви впевнені, що хочете видалити це місто?')) {
      return;
    }

    try {
      await cityService.deleteCity(cityId);
      await loadCities();
    } catch (err) {
      setError('Помилка видалення міста: ' + err.message);
      console.error(err);
    }
  };

  if (loading) {
    return <div className="loading">Завантаження міст...</div>;
  }

  if (error) {
    return <div className="error-container">{error}</div>;
  }

  return (
    <div className="city-list-container">
      <div className="city-list-header">
        <div className="header-top">
          <h1>Міста</h1>
          {isAdmin && (
            <button onClick={() => navigate('/cities/create')} className="create-city-button">
              ➕ Створити місто
            </button>
          )}
        </div>
        <div className="search-bar">
          <input
            type="text"
            placeholder="Пошук міста..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            onKeyPress={(e) => e.key === 'Enter' && handleSearch()}
            className="search-input"
          />
          <button onClick={handleSearch} className="search-button">
            🔍 Пошук
          </button>
        </div>
      </div>

      {cities.length === 0 ? (
        <p className="no-cities">Міст не знайдено</p>
      ) : (
        <div className="cities-grid">
          {cities.map((city) => (
            <div key={city.id} className="city-card">
              {city.imageUrl && (
                <div className="city-image-container">
                  <img 
                    src={city.imageUrl} 
                    alt={city.name}
                    className="city-image"
                    onError={(e) => {
                      e.target.style.display = 'none';
                    }}
                  />
                </div>
              )}
              <div className="city-card-header">
                <h3>{city.name}</h3>
                {city.country && <span className="city-country">📍 {city.country}</span>}
              </div>
              
              <div className="city-card-body">
                {city.region && (
                  <p className="city-detail">
                    <strong>Регіон:</strong> {city.region}
                  </p>
                )}
                {city.population && (
                  <p className="city-detail">
                    <strong>Населення:</strong> {city.population.toLocaleString()}
                  </p>
                )}
                {city.postalCode && (
                  <p className="city-detail">
                    <strong>Індекс:</strong> {city.postalCode}
                  </p>
                )}
              </div>

              <button 
                onClick={() => handleViewCity(city)} 
                className="view-button"
              >
                Детальніше
              </button>
              
              {isAdmin && (
                <div className="admin-actions">
                  <button 
                    onClick={() => handleEditCity(city.id)} 
                    className="edit-button"
                  >
                    ✏️ Редагувати
                  </button>
                  <button 
                    onClick={() => handleDeleteCity(city.id)} 
                    className="delete-city-button"
                  >
                    🗑️ Видалити
                  </button>
                </div>
              )}
            </div>
          ))}
        </div>
      )}

      {/* City Detail Modal */}
      {selectedCity && (
        <div className="modal-overlay" onClick={handleCloseModal}>
          <div className="modal-content" onClick={(e) => e.stopPropagation()}>
            <button className="modal-close" onClick={handleCloseModal}>✕</button>
            
            {selectedCity.imageUrl && (
              <div className="modal-image-container">
                <img 
                  src={selectedCity.imageUrl} 
                  alt={selectedCity.name}
                  className="modal-city-image"
                  onError={(e) => {
                    e.target.style.display = 'none';
                  }}
                />
              </div>
            )}
            
            <h2>{selectedCity.name}</h2>
            
            <div className="modal-details">
              {selectedCity.country && (
                <p><strong>Країна:</strong> {selectedCity.country}</p>
              )}
              {selectedCity.region && (
                <p><strong>Регіон:</strong> {selectedCity.region}</p>
              )}
              {selectedCity.population && (
                <p><strong>Населення:</strong> {selectedCity.population.toLocaleString()}</p>
              )}
              {selectedCity.postalCode && (
                <p><strong>Поштовий індекс:</strong> {selectedCity.postalCode}</p>
              )}
              {selectedCity.latitude && selectedCity.longitude && (
                <p>
                  <strong>Координати:</strong> {selectedCity.latitude}, {selectedCity.longitude}
                </p>
              )}
            </div>

            {selectedCity.description && (
              <div className="city-description">
                <h3>Опис</h3>
                <div 
                  className="description-content"
                  dangerouslySetInnerHTML={{ __html: selectedCity.description }}
                />
              </div>
            )}
          </div>
        </div>
      )}
    </div>
  );
}

export default CityList;
