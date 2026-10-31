import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Home from './pages/Home';
import AnnouncementsPage from './pages/AnnouncementsPage';
import Login from './pages/Login';
import Register from './pages/Register';
import LikedPage from './pages/LikedPage';
import Layout from './components/Layout';
import './App.css';

function AppRouter() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route index element={<Home />} />
          <Route path="announcements" element={<AnnouncementsPage />} />
          <Route path="liked" element={<LikedPage />} />
          <Route path="login" element={<Login />} />
          <Route path="register" element={<Register />} />
        </Route>
      </Routes>
    </Router>
  );
}

export default AppRouter;
