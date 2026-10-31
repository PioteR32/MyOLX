import React from 'react';
import { Link } from 'react-router-dom';
import './Navbar.css';

const Navbar = () => {
  return (
    <header className="navbar-header">
      <div className="navbar-container">
        {/* 1. Logo Section (Left) */}
        <div className="nav-section nav-section-left">
          <Link to="/" className="nav-logo">MyOLX</Link>
        </div>

        {/* 2. Links Section (Center) */}
        <nav className="nav-section nav-section-center">
          <ul className="nav-links">
            <li><Link to="/">Home</Link></li>
            <li><Link to="/announcements">Announcements</Link></li>
            <li><Link to="/liked">Liked</Link></li>
            <li><Link to="/login">Login</Link></li>
            <li><Link to="/register">Register</Link></li>
          </ul>
        </nav>

        {/* 3. Spacer Section (Right) to keep everything centered */}
        <div className="nav-section nav-section-right"></div>
      </div>
    </header>
  );
};

export default Navbar;
