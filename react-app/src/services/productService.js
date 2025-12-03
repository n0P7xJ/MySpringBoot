import api from './api';

export const productService = {
  getAllProducts: async (search = '') => {
    const params = search ? { search } : {};
    const response = await api.get('/products', { params });
    return response.data;
  },

  createProduct: async (product) => {
    const response = await api.post('/products', product);
    return response.data;
  },
};
