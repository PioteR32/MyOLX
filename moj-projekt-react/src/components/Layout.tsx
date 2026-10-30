import React from 'react';
import { Outlet } from 'react-router-dom';
import Navbar from '../components/Navbar';

const Layout = () => {
  return (
    <div style={{ display: 'flex', flexDirection: 'column', minHeight: '100vh' }}>
      <Navbar />
      <main style={{ flex: 1 }}>
        <Outlet />
      </main>
      <footer style={{ 
        padding: '20px', 
        textAlign: 'center', 
        borderTop: '1px solid var(--border)',
        marginTop: 'auto' 
      }}>
        <p>© 2026 MyOLX Announcements</p>
      </footer>
    </div>
  );
};

export default Layout;
