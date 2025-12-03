import React, { useState, useRef, useEffect } from 'react';
import { countries } from '../../data/countries';
import './CountrySelect.css';

const CountrySelect = ({ value, onChange, error, label = 'Країна', required = false }) => {
  const [isOpen, setIsOpen] = useState(false);
  const [searchTerm, setSearchTerm] = useState('');
  const dropdownRef = useRef(null);

  const selectedCountry = countries.find(c => c.name === value);
  
  const filteredCountries = countries.filter(country =>
    country.name.toLowerCase().includes(searchTerm.toLowerCase())
  );

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
        setIsOpen(false);
      }
    };

    document.addEventListener('mousedown', handleClickOutside);
    return () => document.removeEventListener('mousedown', handleClickOutside);
  }, []);

  const handleSelect = (country) => {
    onChange({ target: { name: 'country', value: country.name } });
    setIsOpen(false);
    setSearchTerm('');
  };

  return (
    <div className="country-select-group" ref={dropdownRef}>
      {label && (
        <label className="country-select-label">
          {label}
          {required && <span className="required">*</span>}
        </label>
      )}
      
      <div 
        className={`country-select-trigger ${error ? 'country-select-error' : ''}`}
        onClick={() => setIsOpen(!isOpen)}
      >
        {selectedCountry ? (
          <div className="selected-country">
            <span className="country-flag">{selectedCountry.flag}</span>
            <span className="country-name">{selectedCountry.name}</span>
          </div>
        ) : (
          <span className="placeholder">Оберіть країну</span>
        )}
        <svg 
          className={`dropdown-arrow ${isOpen ? 'open' : ''}`} 
          width="16" 
          height="16" 
          viewBox="0 0 24 24" 
          fill="none" 
          stroke="currentColor"
        >
          <polyline points="6 9 12 15 18 9" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
        </svg>
      </div>

      {isOpen && (
        <div className="country-dropdown">
          <input
            type="text"
            className="country-search"
            placeholder="Пошук країни..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            onClick={(e) => e.stopPropagation()}
          />
          <div className="country-list">
            {filteredCountries.length > 0 ? (
              filteredCountries.map((country) => (
                <div
                  key={country.code}
                  className={`country-option ${selectedCountry?.code === country.code ? 'selected' : ''}`}
                  onClick={() => handleSelect(country)}
                >
                  <span className="country-flag">{country.flag}</span>
                  <span className="country-name">{country.name}</span>
                </div>
              ))
            ) : (
              <div className="no-results">Країну не знайдено</div>
            )}
          </div>
        </div>
      )}

      {error && <span className="error-message">{error}</span>}
    </div>
  );
};

export default CountrySelect;
