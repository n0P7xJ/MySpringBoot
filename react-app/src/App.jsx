import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import ProductList from './components/ProductList';
import CategoryList from './components/CategoryList';
import UserList from './components/UserList';
import './App.css';

function App() {
  return (
    <Router>
      <div className="app">
        <nav className="navbar">
          <div className="nav-container">
            <h1 className="logo">MySpringBoot Shop</h1>
            <ul className="nav-menu">
              <li><Link to="/" className="nav-link">Продукти</Link></li>
              <li><Link to="/categories" className="nav-link">Категорії</Link></li>
              <li><Link to="/users" className="nav-link">Користувачі</Link></li>
            </ul>
          </div>
        </nav>

        <main className="main-content">
          <Routes>
            <Route path="/" element={<ProductList />} />
            <Route path="/categories" element={<CategoryList />} />
            <Route path="/users" element={<UserList />} />
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
