import { useState } from 'react';
import { userService } from '../services/userService';
import Input from './common/Input';
import EmailInput from './common/EmailInput';
import PhoneInput from './common/PhoneInput';
import PasswordInput from './common/PasswordInput';
import './Register.css';

function Register() {
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    email: '',
    phone: '',
    image: '',
    password: '',
    confirmPassword: '',
  });

  const [errors, setErrors] = useState({});
  const [success, setSuccess] = useState('');
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
    // Clear error for this field
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
      
      await userService.registerUser(formData);
      
      setSuccess('Реєстрація успішна! Тепер ви можете увійти.');
      setFormData({
        firstName: '',
        lastName: '',
        email: '',
        phone: '',
        image: '',
        password: '',
        confirmPassword: '',
      });
    } catch (err) {
      // Server returns field-specific errors as an object
      if (err.response?.data && typeof err.response.data === 'object') {
        setErrors(err.response.data);
      } else {
        setErrors({
          general: err.response?.data?.message || 'Помилка реєстрації. Спробуйте ще раз.',
        });
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="register-container">
      <div className="register-card">
        <h1>Реєстрація</h1>
        
        {success && <div className="success-message">{success}</div>}
        {errors.general && <div className="error-message">{errors.general}</div>}

        <form onSubmit={handleSubmit} className="register-form">
          <Input
            label="Ім'я"
            name="firstName"
            value={formData.firstName}
            onChange={handleChange}
            error={errors.firstName}
            placeholder="Введіть ім'я"
            required
          />

          <Input
            label="Прізвище"
            name="lastName"
            value={formData.lastName}
            onChange={handleChange}
            error={errors.lastName}
            placeholder="Введіть прізвище"
            required
          />

          <EmailInput
            value={formData.email}
            onChange={handleChange}
            error={errors.email}
            required
          />

          <PhoneInput
            value={formData.phone}
            onChange={handleChange}
            error={errors.phone}
            required
          />

          <Input
            label="URL зображення (необов'язково)"
            name="image"
            value={formData.image}
            onChange={handleChange}
            error={errors.image}
            placeholder="https://example.com/avatar.jpg"
          />

          <PasswordInput
            label="Пароль"
            name="password"
            value={formData.password}
            onChange={handleChange}
            error={errors.password}
            placeholder="Мінімум 6 символів"
            required
          />

          <PasswordInput
            label="Підтвердіть пароль"
            name="confirmPassword"
            value={formData.confirmPassword}
            onChange={handleChange}
            error={errors.confirmPassword}
            placeholder="Повторіть пароль"
            required
          />

          <button type="submit" disabled={loading} className="submit-button">
            {loading ? 'Реєстрація...' : 'Зареєструватися'}
          </button>
        </form>

        <div className="register-footer">
          <p>Вже є акаунт? <a href="/login">Увійти</a></p>
        </div>
      </div>
    </div>
  );
}

export default Register;
