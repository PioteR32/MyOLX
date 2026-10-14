-- 1. Najpierw wyłączamy sprawdzanie kluczy, aby móc łatwo usunąć i wstawić dane

SET FOREIGN_KEY_CHECKS = 0;



-- 2. Towns (Miasta)
INSERT INTO towns (id, name) VALUES 
(1, 'Warszawa'), (2, 'Kraków'), (3, 'Wrocław'), (4, 'Poznań'), (5, 'Gdańsk'), 
(6, 'Łódź'), (7, 'Szczecin'), (8, 'Bydgoszcz'), (9, 'Lublin'), (10, 'Katowice');

-- 3. Users (Użytkownicy)
INSERT INTO users (id, first_name, last_name, password, phone_num, email, town_id) VALUES 
(1, 'Jan', 'Kowalski', 'pass1', '123456789', 'jan@example.com', 1),
(2, 'Anna', 'Nowak', 'pass2', '987654321', 'anna@example.com', 2),
(3, 'Piotr', 'Zieliński', 'pass3', '111222333', 'piotr@example.com', 3),
(4, 'Maria', 'Wiśniewska', 'pass4', '444555666', 'maria@example.com', 4),
(5, 'Krzysztof', 'Wójcik', 'pass5', '777888999', 'krzysztof@example.com', 5),
(6, 'Agnieszka', 'Kamińska', 'pass6', '222333444', 'agnieszka@example.com', 6),
(7, 'Tomasz', 'Lewandowski', 'pass7', '555666777', 'tomasz@example.com', 7),
(8, 'Monika', 'Zalewska', 'pass8', '888999000', 'monika@example.com', 8),
(9, 'Michał', 'Dąbrowski', 'pass9', '333444555', 'michal@example.com', 9),
(10, 'Katarzyna', 'Szymańska', 'pass10', '666777888', 'katarzyna@example.com', 10);

-- 4. Announcements (Ogłoszenia)
-- UWAGA: Tu nie ma już kolumny announcment_image_id!
INSERT INTO announcements (id, title, description, created_at, is_active, user_id, order_id) VALUES 
(1, 'Sprzedam rower', 'Rower górski w świetnym stanie', '2023-10-14 10:00:00', 1, 1, 1),
(2, 'Oddam książki', 'Zbiór książek fantasy', '2023-10-14 11:00:00', 1, 2, 2),
(3, 'Laptop gamingowy', 'W stanie idealnym', '2023-10-14 12:00:00', 1, 3, 3),
(4, 'Telefon iPhone', 'Stan idealny', '2023-10-14 13:00:00', 1, 4, 4),
(5, 'Konsola PS5', 'Z kompletem padów', '2023-10-14 14:00:00', 1, 5, 5),
(6, 'Monitor 24 cali', 'Monitor Dell 144Hz', '2023-10-14 15:00:00', 1, 6, 6),
(7, 'Klawiatura', 'Keychron K2', '2023-10-14 16:00:00', 1, 7, 7),
(8, 'Myszka', 'Logitech MX', '2023-10-14 17:00:00', 1, 8, 8),
(9, 'Słuchawki', 'Sony WH-1000XM4', '2023-10-14 18:00:00', 1, 9, 9),
(10, 'Biurko', 'Biurko regulowane', '2023-10-14 19:00:00', 1, 10, 10);

-- 5. Announcements Images (Zdjęcia)
-- Klucz obcy announcement_id znajduje się tutaj!
INSERT INTO announcements_images (id, image_data, announcement_id) VALUES 
(1, 0x1, 1), (2, 0x2, 2), (3, 0x3, 3), (4, 0x4, 4), (5, 0x5, 5),
(6, 0x6, 6), (7, 0x7, 7), (8, 0x8, 8), (9, 0x9, 9), (10, 0xA, 10);

-- 6. Orders (Zamówienia)
INSERT INTO orders (id, name, status, is_correct, is_protected, announcements_id) VALUES 
(1, 'Order 1', 'DELIVERED', 1, 1, 1), (2, 'Order 2', 'SHIPPED', 1, 1, 2),
(3, 'Order 3', 'PENDING', 1, 1, 3), (4, 'Order 4', 'CANCELLED', 0, 1, 4),
(5, 'Order 5', 'DELIVERED', 1, 1, 5), (6, 'Order 6', 'SHIPPED', 1, 1, 6),
(7, 'Order 7', 'PENDING', 1, 1, 7), (8, 'Order 8', 'DELIVERED', 1, 1, 8),
(9, 'Order 9', 'CANCELLED', 0, 1, 9), (10, 'Order 10', 'SHIPPED', 1, 1, 10);

-- 7. Users Order (Relacja kupujący-sprzedający)
INSERT INTO usersOrder (id, order_id, buyer_id, seller_id) VALUES 
(1, 1, 2, 1), (2, 2, 3, 2), (3, 3, 4, 3), (4, 4, 5, 4), (5, 5, 6, 5),
(6, 6, 7, 6), (7, 7, 8, 7), (8, 8, 9, 8), (9, 9, 10, 9), (10, 10, 1, 10);

SET FOREIGN_KEY_CHECKS = 1;
