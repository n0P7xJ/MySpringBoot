import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
import { toggleTheme } from './store/themeSlice';
import { logout } from './store/authSlice';
import ProductList from './components/ProductList';
import CategoryList from './components/CategoryList';
import UserList from './components/UserList';
import Register from './components/Register';
import Login from './components/Login';
import AdminProducts from './components/admin/AdminProducts';
import './App.css';

function App() {
  const dispatch = useDispatch();
  const theme = useSelector((state) => state.theme.mode);
  const { user, isAuthenticated } = useSelector((state) => state.auth);
  const isAdmin = user?.role === 'ADMIN';

  const handleLogout = () => {
    dispatch(logout());
  };

  return (
    <Router>
      <div className="app">
        <nav className="navbar">
          <div className="nav-container">
            <h1 className="logo">MySpringBoot Shop</h1>
            <ul className="nav-menu">
              <li><Link to="/" className="nav-link">Продукти</Link></li>
              <li><Link to="/categories" className="nav-link">Категорії</Link></li>
              
              {isAuthenticated ? (
                <>
                  {isAdmin && (
                    <>
                      <li><Link to="/users" className="nav-link">Користувачі</Link></li>
                      <li><Link to="/admin/products" className="nav-link">Керування товарами</Link></li>
                    </>
                  )}
                  <li className="user-info">
                    <span className="user-name">👤 {user.firstName}</span>
                  </li>
                  <li>
                    <button onClick={handleLogout} className="logout-button">
                      Вихід
                    </button>
                  </li>
                </>
              ) : (
                <>
                  <li><Link to="/login" className="nav-link">Вхід</Link></li>
                  <li><Link to="/register" className="nav-link">Реєстрація</Link></li>
                </>
              )}
              
              <li>
                <button 
                  onClick={() => dispatch(toggleTheme())} 
                  className="theme-toggle"
                  title={theme === 'light' ? 'Темна тема' : 'Світла тема'}
                >
                  {theme === 'light' ? '🌙' : '☀️'}
                </button>
              </li>
            </ul>
          </div>
        </nav>

        <main className="main-content">
          <Routes>
            <Route path="/" element={<ProductList />} />
            <Route path="/categories" element={<CategoryList />} />
            <Route path="/users" element={<UserList />} />
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
            <Route path="/admin/products" element={<AdminProducts />} />
          </Routes>
        </main>

        <footer className="footer">
          <p>&copy; 2025 MySpringBoot. Spring Boot + React App</p>
        </footer>
      </div>
    </Router>
  );
}

export default App;
