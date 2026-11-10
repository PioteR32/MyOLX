-- Dane testowe MySQL; uruchamiaj po utworzeniu schematu przez aplikację.
-- Wymagane PUSTE tabele. Jawne ID zapewniają deterministyczne powiązania.
-- Skrypt nie usuwa danych i nie jest przeznaczony do ponownego uruchamiania.
-- Razem: 500 rekordów, minimum 10 w każdej z 10 tabel.
-- towns: 10, users: 40, categories: 20, filters: 10, filter_options: 30,
-- categories_filters: 30, announcements: 80, announcements_images: 200,
-- orders: 40, users_order: 40.
-- Hasło Test12345! służy wyłącznie do kont demonstracyjnych.

START TRANSACTION;

-- 1. Miasta: 10
INSERT INTO towns (id, name) VALUES
    (1, 'Warszawa'), (2, 'Kraków'), (3, 'Wrocław'), (4, 'Gdańsk'),
    (5, 'Poznań'), (6, 'Łódź'), (7, 'Katowice'), (8, 'Lublin'),
    (9, 'Szczecin'), (10, 'Bydgoszcz');

-- 2. Użytkownicy: 40 (po czterech w każdym mieście)
INSERT INTO users (id, first_name, last_name, password, phone_num, email, town_id)
SELECT t.id + g.offset_id, g.first_name, g.last_name, 'Test12345!',
       CONCAT('6000000', CASE WHEN t.id + g.offset_id < 10 THEN '0' ELSE '' END,
              t.id + g.offset_id),
       CONCAT('user', t.id + g.offset_id, '@example.com'), t.id
FROM towns t
CROSS JOIN (
    SELECT 0 AS offset_id, 'Jan' AS first_name, 'Kowalski' AS last_name
    UNION ALL SELECT 10, 'Anna', 'Nowak'
    UNION ALL SELECT 20, 'Piotr', 'Zieliński'
    UNION ALL SELECT 30, 'Maria', 'Lewandowska'
) g
WHERE t.id BETWEEN 1 AND 10;

-- 3. Kategorie: 20 (10 głównych i 10 podkategorii)
INSERT INTO categories (id, title, parent_id) VALUES
    (1, 'Elektronika', NULL), (2, 'Motoryzacja', NULL),
    (3, 'Dom i ogród', NULL), (4, 'Moda', NULL), (5, 'Sport', NULL),
    (6, 'Kultura', NULL), (7, 'Dla dzieci', NULL), (8, 'Zwierzęta', NULL),
    (9, 'Narzędzia', NULL), (10, 'Muzyka', NULL);
INSERT INTO categories (id, title, parent_id) VALUES
    (11, 'Telefony', 1), (12, 'Części samochodowe', 2),
    (13, 'Meble', 3), (14, 'Buty', 4), (15, 'Rowery', 5),
    (16, 'Książki', 6), (17, 'Zabawki', 7), (18, 'Akcesoria dla zwierząt', 8),
    (19, 'Elektronarzędzia', 9), (20, 'Instrumenty', 10);

-- 4. Filtry: 10
INSERT INTO filters (id, name) VALUES
    (1, 'Stan'), (2, 'Marka telefonu'), (3, 'Marka samochodu'),
    (4, 'Materiał'), (5, 'Rozmiar buta'), (6, 'Typ roweru'),
    (7, 'Gatunek książki'), (8, 'Wiek dziecka'),
    (9, 'Rodzaj zwierzęcia'), (10, 'Zasilanie');

-- 5. Opcje filtrów: 30 (po trzy na filtr)
INSERT INTO filter_options (id, name, filter_id, parent_id) VALUES
    (1, 'Nowy', 1, NULL), (2, 'Używany', 1, NULL), (3, 'Uszkodzony', 1, NULL),
    (4, 'Samsung', 2, NULL), (5, 'Apple', 2, NULL), (6, 'Xiaomi', 2, NULL),
    (7, 'Toyota', 3, NULL), (8, 'Volkswagen', 3, NULL), (9, 'Ford', 3, NULL),
    (10, 'Drewno', 4, NULL), (11, 'Metal', 4, NULL), (12, 'Tworzywo', 4, NULL),
    (13, '38', 5, NULL), (14, '40', 5, NULL), (15, '42', 5, NULL),
    (16, 'Miejski', 6, NULL), (17, 'Górski', 6, NULL), (18, 'Szosowy', 6, NULL),
    (19, 'Fantasy', 7, NULL), (20, 'Kryminał', 7, NULL), (21, 'Reportaż', 7, NULL),
    (22, '0-3 lata', 8, NULL), (23, '4-6 lat', 8, NULL), (24, '7-12 lat', 8, NULL),
    (25, 'Pies', 9, NULL), (26, 'Kot', 9, NULL), (27, 'Gryzoń', 9, NULL),
    (28, 'Sieciowe', 10, NULL), (29, 'Akumulatorowe', 10, NULL),
    (30, 'Bateryjne', 10, NULL);

-- 6. Kategorie i filtry: 30
-- Nazwa categories_filters zgodna z konwencją nazewnictwa Spring/Hibernate.
INSERT INTO categories_filters (filter_id, category_id)
SELECT 1, id FROM categories WHERE id BETWEEN 1 AND 20;
INSERT INTO categories_filters (filter_id, category_id) VALUES
    (2, 11), (3, 12), (4, 13), (5, 14), (6, 15),
    (7, 16), (8, 17), (9, 18), (10, 19), (4, 20);

-- 7. Ogłoszenia: 80 (po dwa na użytkownika)
-- Najpierw NULL w order_id ze względu na cykliczną relację z orders.
INSERT INTO announcements
    (id, title, description, price, created_at, is_active, user_id, order_id)
SELECT u.id + g.offset_id,
       CONCAT(CASE MOD(u.id - 1, 10)
           WHEN 0 THEN 'Telefon Samsung' WHEN 1 THEN 'Komplet opon'
           WHEN 2 THEN 'Stół drewniany' WHEN 3 THEN 'Buty sportowe'
           WHEN 4 THEN 'Rower miejski' WHEN 5 THEN 'Książka fantasy'
           WHEN 6 THEN 'Klocki dla dzieci' WHEN 7 THEN 'Transporter dla kota'
           WHEN 8 THEN 'Wiertarka' ELSE 'Gitara akustyczna'
       END, ' #', u.id + g.offset_id),
       CONCAT('Oferta testowa nr ', u.id + g.offset_id,
              '. Przedmiot w dobrym stanie. Odbiór osobisty lub wysyłka.'),
       25.00 + MOD(u.id * 37 + g.offset_id * 13, 2000),
       CURRENT_TIMESTAMP,
       CASE WHEN g.offset_id = 0 AND MOD(u.id, 3) = 1 THEN false ELSE true END,
       u.id, NULL
FROM users u
CROSS JOIN (SELECT 0 AS offset_id UNION ALL SELECT 40) g
WHERE u.id BETWEEN 1 AND 40;

-- 8. Zamówienia: 40 (do pierwszych 40 ogłoszeń)
INSERT INTO orders (id, name, status, is_correct, is_protected, announcement_id)
SELECT id, CONCAT('Zamówienie ', id),
       CASE MOD(id, 3) WHEN 1 THEN 'COMPLETED' WHEN 2 THEN 'PENDING' ELSE 'CANCELLED' END,
       CASE WHEN MOD(id, 3) = 0 THEN false ELSE true END,
       CASE WHEN MOD(id, 2) = 0 THEN true ELSE false END,
       id
FROM announcements WHERE id BETWEEN 1 AND 40;

-- Uzupełnienie drugiej strony relacji ogłoszenie–zamówienie.
UPDATE announcements
SET order_id = (SELECT o.id FROM orders o WHERE o.announcement_id = announcements.id)
WHERE id BETWEEN 1 AND 40;

-- 9. Zdjęcia: 200 (po dwa na ogłoszenie i trzecie dla pierwszych 40)
-- PNG 1x1 jako obraz demonstracyjny zamiast losowych bajtów.
INSERT INTO announcements_images (id, image_data, announcement_id)
SELECT a.id + g.offset_id,
       X'89504E470D0A1A0A0000000D4948445200000001000000010804000000B51C0C020000000B4944415478DA6364F80F00010501012718E3660000000049454E44AE426082',
       a.id
FROM announcements a
CROSS JOIN (SELECT 0 AS offset_id UNION ALL SELECT 80 UNION ALL SELECT 160) g
WHERE a.id BETWEEN 1 AND 80 AND (g.offset_id < 160 OR a.id <= 40);

-- 10. Uczestnicy zamówień: 40
-- Sprzedawca jest właścicielem ogłoszenia; kupujący to inny użytkownik.
INSERT INTO users_order (id, order_id, buyer_id, seller_id)
SELECT o.id, o.id, MOD(a.user_id, 40) + 1, a.user_id
FROM orders o
JOIN announcements a ON a.id = o.announcement_id
WHERE o.id BETWEEN 1 AND 40;

COMMIT;

-- Kontrola liczby rekordów: ostatni wiersz powinien pokazać 500.
SELECT 'towns' AS tabela, COUNT(*) AS liczba_rekordow FROM towns
UNION ALL SELECT 'users', COUNT(*) FROM users
UNION ALL SELECT 'categories', COUNT(*) FROM categories
UNION ALL SELECT 'filters', COUNT(*) FROM filters
UNION ALL SELECT 'filter_options', COUNT(*) FROM filter_options
UNION ALL SELECT 'categories_filters', COUNT(*) FROM categories_filters
UNION ALL SELECT 'announcements', COUNT(*) FROM announcements
UNION ALL SELECT 'announcements_images', COUNT(*) FROM announcements_images
UNION ALL SELECT 'orders', COUNT(*) FROM orders
UNION ALL SELECT 'users_order', COUNT(*) FROM users_order
UNION ALL SELECT 'RAZEM',
    (SELECT COUNT(*) FROM towns) + (SELECT COUNT(*) FROM users)
    + (SELECT COUNT(*) FROM categories) + (SELECT COUNT(*) FROM filters)
    + (SELECT COUNT(*) FROM filter_options) + (SELECT COUNT(*) FROM categories_filters)
    + (SELECT COUNT(*) FROM announcements) + (SELECT COUNT(*) FROM announcements_images)
    + (SELECT COUNT(*) FROM orders) + (SELECT COUNT(*) FROM users_order);