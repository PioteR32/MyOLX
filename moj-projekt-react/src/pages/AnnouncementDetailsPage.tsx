import { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import type { Announcement } from '../types/Announcement';

const AnnouncementDetailsPage = () => {
  const { id } = useParams<{ id: string }>();
  const [announcement, setAnnouncement] = useState<Announcement | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [notFound, setNotFound] = useState(false);
  const [retry, setRetry] = useState(0);

  useEffect(() => {
    const controller = new AbortController();

    const fetchAnnouncement = async () => {
      setLoading(true);
      setError(null);
      setNotFound(false);
      setAnnouncement(null);

      try {
        if (!id) {
          setNotFound(true);
          return;
        }

        const response = await fetch(
          `http://localhost:8082/api/announcements/${encodeURIComponent(id)}`,
          { signal: controller.signal },
        );

        if (response.status === 404) {
          setNotFound(true);
          return;
        }
        if (!response.ok) {
          throw new Error(`Error: ${response.status} ${response.statusText}`);
        }

        const data: Announcement = await response.json();
        if (!controller.signal.aborted) {
          setAnnouncement(data);
        }
      } catch (err) {
        if (!controller.signal.aborted) {
          setError(err instanceof Error ? err.message : 'Unknown error occurred');
        }
      } finally {
        if (!controller.signal.aborted) {
          setLoading(false);
        }
      }
    };

    fetchAnnouncement();
    return () => controller.abort();
  }, [id, retry]);

  return (
    <div className="announcement-details">
      <Link to="/announcements">Back to announcements</Link>

      {loading && <p role="status">Loading announcement...</p>}

      {!loading && notFound && (
        <div>
          <h1>Announcement not found</h1>
          <p>This announcement does not exist or has been removed.</p>
        </div>
      )}

      {!loading && error && (
        <div role="alert">
          <p>Unable to load announcement: {error}</p>
          <button onClick={() => setRetry(value => value + 1)} className="counter">
            Retry
          </button>
        </div>
      )}

      {!loading && !error && !notFound && announcement && (
        <article>
          <h1>{announcement.title || 'No Title'}</h1>
          <p>Price: {announcement.price != null ? `$${announcement.price.toFixed(2)}` : 'N/A'}</p>

          <section aria-labelledby="announcement-description">
            <h2 id="announcement-description">Description</h2>
            <p className="announcement-description">
              {announcement.description || announcement.content || announcement.text || 'No description available.'}
            </p>
          </section>

          <section aria-labelledby="announcement-filters">
            <h2 id="announcement-filters">Filter options</h2>
            {announcement.filterOptions?.length ? (
              <ul className="announcement-filter-options">
                {announcement.filterOptions.map(option => (
                  <li key={option.id}>
                    {option.filter?.name && <strong>{option.filter.name}: </strong>}
                    {option.name}
                  </li>
                ))}
              </ul>
            ) : (
              <p>No filter options available.</p>
            )}
          </section>
        </article>
      )}
    </div>
  );
};

export default AnnouncementDetailsPage;