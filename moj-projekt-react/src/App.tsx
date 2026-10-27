// @ts-ignore
import { useState, useEffect } from 'react';
// @ts-ignore -- CSS side-effect imports are handled by the bundler.
import './App.css';

// 1. Definiujemy interfejs dla ogłoszenia
// Dzięki temu będziemy wiedzieć, jakie pola posiada każdy obiekt w tablicy
interface Announcement {
  id: number | string;
  title?: string;
  content?: string;
  description?: string;
  text?: string;
  price?: number;
}

function App() {
  // 2. Definiujemy typy dla stanów za pomocą generyków
  const [announcements, setAnnouncements] = useState<Announcement[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  // Zakładamy, że backend działa na localhost:8082
  const API_URL = 'http://localhost:8082/api/announcements';

  const fetchAnnouncements = async () => {
    try {
      setLoading(true);
      const response = await fetch(API_URL);
      
      console.log("Status odpowiedzi:", response.status, response.statusText);
      
      if (!response.ok) {
        throw new Error(`Error: ${response.status} ${response.statusText}`);
      }
      
      // Wskazujemy, że oczekujemy danych typu Announcement[]
      const data: Announcement[] = await response.json();
      setAnnouncements(data);
      console.log("Dane otrzymane z backendu:", data);
    } catch (err) {
      // 3. Bezpieczna obsługa błędu w TypeScript
      if (err instanceof Error) {
        setError(err.message);
      } else {
        setError('Unknown error occurred');
      }
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchAnnouncements();
  }, []);

  return (
    <div id="root">
      <header style={{ padding: '20px', borderBottom: '1px solid var(--border)' }}>
        <h1 style={{ margin: 0 }}>Announcements</h1>
      </header>
      
      <main id="center">
        <div className="announcements-container">
          {loading && <p>Loading announcements...</p>}
          
          {error && (
            <div style={{ color: 'red', padding: '20px' }}>
              <p>Error: {error}</p>
              <p>Make sure the backend is running at {API_URL}</p>
              <button onClick={fetchAnnouncements} className="counter">Retry</button>
            </div>
          )}

          {!loading && !error && announcements.length === 0 && (
            <p>No announcements found.</p>
          )}
          
          <div className="announcements-list">
            {!loading && !error && announcements.map((announcement) => (
              <div key={announcement.id} className="announcement-card">
                <h2>{announcement.title || 'No Title'}</h2>
                {/* Wybieramy odpowiednie pole tekstowe, jeśli jedno z nich nie istnieje */}
                <p>{announcement.content || announcement.description || announcement.text || 'No content available.'}</p>
                {/* Bezpieczne wyświetlanie ceny z obsługą braku wartości */}
                <p>Price: {announcement.price !== undefined ? `$${announcement.price.toFixed(2)}` : 'N/A'}</p>
                <small>ID: {announcement.id}</small>
              </div>
            ))}
          </div>
        </div>
      </main>

      <footer id="spacer" style={{ display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
        <p>© 2026 MyOLX Announcements</p>
      </footer>
    </div>
  );
}

export default App;
