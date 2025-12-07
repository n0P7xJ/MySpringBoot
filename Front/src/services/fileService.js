import api from './api';

export const fileService = {
  uploadImage: async (file) => {
    const formData = new FormData();
    formData.append('file', file);

    const response = await api.post('/upload/image', formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
    return response.data;
  },

  deleteImage: async (filename) => {
    const response = await api.delete('/upload/image', {
      params: { filename },
    });
    return response.data;
  },
};
