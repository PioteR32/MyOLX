import { useState, useEffect } from 'react'
import './App.css'

function App() {
  const [announcements, setAnnouncements] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  // Assuming the backend is running on localhost:8082
  const API_URL = 'http://localhost:8082/api/announcements'
  const fetchAnnouncements = async () => {
    try {
      setLoading(true)
      const response = await fetch(API_URL)
      console.log("Status odpowiedzi:", response.status, response.statusText)
      if (!response.ok) {
        throw new Error(`Error: ${response.status} ${response.statusText}`)
      }
      const data = await response.json()
      setAnnouncements(data)
      console.log("Dane otrzymane z backendu:", data)
    } catch (err) {
      setError(err.message)
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    fetchAnnouncements()
  }, [])

  
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
            {!loading && !error && announcements.map(announcement => (
              <div key={announcement.id} className="announcement-card">
                <h2>{announcement.title || 'No Title'}</h2>
                <p>{announcement.content || announcement.description || announcement.text || 'No content available.'}</p>
                <p>Price: ${announcement.price?.toFixed(2) || 'N/A'}</p>
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
  )
}

export default App

