import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import App from './App.tsx';
import Layout from './components/Layout';
import Home from './pages/Home';
import AnnouncementsPage from './pages/AnnouncementsPage';
import Login from './pages/Login';
import LikedPage from './pages/LikedPage';
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
        </Route>
      </Routes>
    </Router>
  );
}

export default AppRouter;
