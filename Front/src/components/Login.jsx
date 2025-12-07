import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { login } from '../store/authSlice';
import './Login.css';

function Login() {
  const [formData, setFormData] = useState({
    email: '',
    password: '',
  });

  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
    if (error) setError('');
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    if (!formData.email || !formData.password) {
      setError('Введіть email та пароль');
      return;
    }

    try {
      setLoading(true);
      setError('');
      
      // Simulate login - check for admin user
      const isAdmin = formData.email === 'admin@example.com';
      
      const userData = {
        email: formData.email,
        firstName: isAdmin ? 'Admin' : 'User',
        lastName: isAdmin ? 'User' : 'Name',
        role: isAdmin ? 'ADMIN' : 'USER',
      };
      
      // Dispatch login action
      dispatch(login(userData));
      
      // Redirect to products page
      navigate('/');
      
    } catch (err) {
      setError('Невірний email або пароль');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="login-container">
      <div className="login-card">
        <h1>Вхід в акаунт</h1>
        
        {error && <div className="error-message">{error}</div>}

        <form onSubmit={handleSubmit} className="login-form">
          <div className="form-group">
            <label htmlFor="email">Email</label>
            <input
              type="email"
              id="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
              placeholder="email@example.com"
              required
            />
          </div>

          <div className="form-group">
            <label htmlFor="password">Пароль</label>
            <input
              type="password"
              id="password"
              name="password"
              value={formData.password}
              onChange={handleChange}
              placeholder="Введіть пароль"
              required
            />
          </div>

          <button type="submit" disabled={loading} className="submit-button">
            {loading ? 'Вхід...' : 'Увійти'}
          </button>
        </form>

        <div className="login-footer">
          <p>Немає акаунту? <a href="/register">Зареєструватися</a></p>
        </div>
      </div>
    </div>
  );
}

export default Login;
