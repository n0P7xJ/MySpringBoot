import { useState, useEffect } from 'react';
import { productService } from '../services/productService';
import './ProductList.css';

function ProductList() {
  const [products, setProducts] = useState([]);
  const [search, setSearch] = useState('');
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    loadProducts();
  }, []);

  const loadProducts = async (searchTerm = '') => {
    try {
      setLoading(true);
      setError(null);
      const data = await productService.getAllProducts(searchTerm);
      setProducts(data);
    } catch (err) {
      setError('Помилка завантаження продуктів: ' + err.message);
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleSearch = (e) => {
    e.preventDefault();
    loadProducts(search);
  };

  if (loading) return <div className="loading">Завантаження...</div>;
  if (error) return <div className="error">{error}</div>;

  return (
    <div className="product-list">
      <h1>Продукти</h1>
      
      <form onSubmit={handleSearch} className="search-form">
        <input
          type="text"
          placeholder="Пошук продуктів..."
          value={search}
          onChange={(e) => setSearch(e.target.value)}
          className="search-input"
        />
        <button type="submit" className="search-button">Пошук</button>
        <button 
          type="button" 
          onClick={() => { setSearch(''); loadProducts(''); }}
          className="clear-button"
        >
          Очистити
        </button>
      </form>

      <div className="products-grid">
        {products.map((product) => (
          <div key={product.id} className="product-card">
            {product.imageUrl && (
              <img src={product.imageUrl} alt={product.name} className="product-image" />
            )}
            <h3>{product.name}</h3>
            <p className="product-description">{product.description}</p>
            <div className="product-details">
              <p className="product-price">${product.price?.toFixed(2)}</p>
              <p className="product-quantity">Кількість: {product.quantity}</p>
              {product.category && (
                <p className="product-category">Категорія: {product.category.name}</p>
              )}
            </div>
            <p className="product-sku">SKU: {product.sku}</p>
          </div>
        ))}
      </div>

      {products.length === 0 && (
        <p className="no-products">Продукти не знайдено</p>
      )}
    </div>
  );
}

export default ProductList;
