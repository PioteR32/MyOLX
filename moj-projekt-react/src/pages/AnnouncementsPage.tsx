import React, { useState, useEffect } from 'react';

interface Announcement {
  id: number | string;
  title?: string;
  content?: string;
  description?: string;
  text?: string;
  price?: number;
}

const AnnouncementsPage = () => {
  const [announcements, setAnnouncements] = useState<Announcement[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  const API_URL = 'http://localhost:8082/api/announcements';

  const fetchAnnouncements = async () => {
    try {
      setLoading(true);
      const response = await fetch(API_URL);
      if (!response.ok) {
        throw new Error(`Error: ${response.status} ${response.statusText}`);
      }
      const data: Announcement[] = await response.json();
      setAnnouncements(data);
    } catch (err) {
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
    <div style={{ padding: '20px' }}>
      <h1 style={{ textAlign: 'center' }}>Announcements</h1>
      <div className="announcements-container">
        {loading && <p>Loading announcements...</p>}
        
        {error && (
          <div style={{ color: 'red', padding: '20px' }}>
            <p>Error: {error}</p>
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
              <p>{announcement.content || announcement.description || announcement.text || 'No content available.'}</p>
              <p>Price: {announcement.price !== undefined ? `$${announcement.price.toFixed(2)}` : 'N/A'}</p>
              <small>ID: {announcement.id}</small>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default AnnouncementsPage;
