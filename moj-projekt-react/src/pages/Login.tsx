import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const Login = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const handleLogin = (e: React.FormEvent) => {
    e.preventDefault();
    console.log('Logging in with:', { email, password });
    // For now, just navigate to home after "login"
    alert('Login successful (mock)');
    window.location.href = '/';
  };

  return (
    <div style={{ 
      display: 'flex', 
      justifyContent: 'center', 
      alignItems: 'center', 
      height: '80vh',
      padding: '20px' 
    }}>
      <form 
        onSubmit={handleLogin}
        style={{
          display: 'flex',
          flexDirection: 'column',
          gap: '16px',
          width: '100%',
          maxWidth: '400px',
          padding: '40px',
          border: '1px solid var(--border)',
          borderRadius: '8px',
          background: 'var(--code-bg)'
        }}
      >
        <h2 style={{ textAlign: 'center', margin: '0 0 20px 0' }}>Login</h2>
        
        <div style={{ display: 'flex', flexDirection: 'column', textAlign: 'left' }}>
          <label htmlFor="email">Email</label>
          <input
            id="email"
            type="email"
            required
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            style={{ padding: '10px', borderRadius: '4px', border: '1px solid var(--border)' }}
          />
        </div>

        <div style={{ display: 'flex', flexDirection: 'column', textAlign: 'left' }}>
          <label htmlFor="password">Password</label>
          <input
            id="password"
            type="password"
            required
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            style={{ padding: '10px', borderRadius: '4px', border: '1px solid var(--border)' }}
          />
        </div>

        <button
          type="submit"
          style={{
            padding: '12px',
            backgroundColor: 'var(--accent)',
            color: 'white',
            border: 'none',
            borderRadius: '6px',
            cursor: 'pointer',
            fontSize: '16px'
          }}
        >
          Login
        </button>
      </form>
    </div>
  );
};

export default Login;
