import { useState, useEffect } from 'react';
import { categoryService } from '../services/categoryService';
import './CategoryList.css';

function CategoryList() {
  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    loadCategories();
  }, []);

  const loadCategories = async () => {
    try {
      setLoading(true);
      setError(null);
      const data = await categoryService.getAllCategories();
      setCategories(data);
    } catch (err) {
      setError('Помилка завантаження категорій: ' + err.message);
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  if (loading) return <div className="loading">Завантаження...</div>;
  if (error) return <div className="error">{error}</div>;

  return (
    <div className="category-list">
      <h1>Категорії</h1>
      
      <div className="categories-grid">
        {categories.map((category) => (
          <div key={category.id} className="category-card">
            <h3>{category.name}</h3>
            <p className="category-id">ID: {category.id}</p>
          </div>
        ))}
      </div>

      {categories.length === 0 && (
        <p className="no-categories">Категорії не знайдено</p>
      )}
    </div>
  );
}

export default CategoryList;
