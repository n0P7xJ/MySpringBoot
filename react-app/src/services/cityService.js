import api from './api';

export const cityService = {
  getAllCities: async (search = '') => {
    const params = search ? { search } : {};
    const response = await api.get('/cities', { params });
    return response.data;
  },

  getCityById: async (id) => {
    const response = await api.get(`/cities/${id}`);
    return response.data;
  },

  getCitiesByRegion: async (region) => {
    const response = await api.get('/cities/by-region', { params: { region } });
    return response.data;
  },

  getCitiesByCountry: async (country) => {
    const response = await api.get('/cities/by-country', { params: { country } });
    return response.data;
  },

  createCity: async (cityData) => {
    const response = await api.post('/cities', cityData);
    return response.data;
  },

  updateCity: async (id, cityData) => {
    const response = await api.put(`/cities/${id}`, cityData);
    return response.data;
  },

  deleteCity: async (id) => {
    const response = await api.delete(`/cities/${id}`);
    return response.data;
  }
};
