-- 1. Tabela: towns
INSERT INTO towns (name) VALUES ('Warszawa');
INSERT INTO towns (name) VALUES ('Kraków');
INSERT INTO towns (name) VALUES ('Wrocław');
INSERT INTO towns (name) VALUES ('Gdańsk');
INSERT INTO towns (name) VALUES ('Poznań');

-- 2. Tabela: users
-- (Zakładamy: id 1-5, town_id 1-5)
INSERT INTO users (first_name, last_name, password, phone_num, email, town_id) 
VALUES ('Jan', 'Kowalski', 'pass123', '123456789', 'jan@example.com', 1);
INSERT INTO users (first_name, last_name, password, phone_num, email, town_id) 
VALUES ('Anna', 'Nowak', 'pass123', '223344556', 'anna@example.com', 2);
INSERT INTO users (first_name, last_name, password, phone_num, email, town_id) 
VALUES ('Piotr', 'Zieliński', 'pass123', '556677889', 'piotr@example.com', 3);
INSERT INTO users (first_name, last_name, password, phone_num, email, town_id) 
VALUES ('Maria', 'Lewandowska', 'pass123', '998877665', 'maria@example.com', 4);
INSERT INTO users (first_name, last_name, password, phone_num, email, town_id) 
VALUES ('Adam', 'Wiśniewski', 'pass123', '445566778', 'adam@example.com', 5);

-- 3. Tabela: orders
-- Ze względu na cykliczną zależność, w realnej bazie najpierw tworzymy order, 
-- a potem aktualizujemy go o announcement_id, lub odwrotnie.
-- Tutaj zakładamy, że announcement_id jest najpierw wprowadzany jako NULL lub używamy ID z poprzednich kroków.


-- 4. Tabela: announcements
-- (Zakładamy, że id zamówień to 1-5 i user_id to 1-5)
INSERT INTO announcements (title, description, created_at, is_active, user_id, order_id) 
VALUES ('Sprzedam rower', 'Świetny stan, mało używany', CURRENT_TIMESTAMP, true, 1, NULL);
INSERT INTO announcements (title, description, created_at, is_active, user_id, order_id) 
VALUES ('Laptop gamingowy', 'Potrzebuję gotówki', CURRENT_TIMESTAMP, true, 2, NULL);
INSERT INTO announcements (title, description, created_at, is_active, user_id, order_id) 
VALUES ('Książka fantasy', 'Nieczytana', CURRENT_TIMESTAMP, true, 3, NULL);
INSERT INTO announcements (title, description, created_at, is_active, user_id, order_id) 
VALUES ('Telefon Samsung', 'Zarysowany ekran', CURRENT_TIMESTAMP, true, 4, NULL);
INSERT INTO announcements (title, description, created_at, is_active, user_id, order_id) 
VALUES ('Konsola PS5', 'Nowa w pudełku', CURRENT_TIMESTAMP, true, 5, NULL);

INSERT INTO orders (name, status, is_correct, is_protected, announcement_id) VALUES ('Order 1', 'COMPLETED', true, true, 1);
INSERT INTO orders (name, status, is_correct, is_protected, announcement_id) VALUES ('Order 2', 'PENDING', true, false, 2);
INSERT INTO orders (name, status, is_correct, is_protected, announcement_id) VALUES ('Order 3', 'CANCELLED', false, false, 3);
INSERT INTO orders (name, status, is_correct, is_protected, announcement_id) VALUES ('Order 4', 'COMPLETED', true, true, 4);
INSERT INTO orders (name, status, is_correct, is_protected, announcement_id) VALUES ('Order 5', 'PENDING', true, true, 5);
-- Opcjonalnie: Aktualizacja zamówień, aby połączyły się z ogłoszeniami (jeśli baza wymaga NOT NULL)
-- UPDATE orders SET announcement_id = 1 WHERE id = 1;
-- UPDATE orders SET announcement_id = 2 WHERE id = 2;
-- UPDATE orders SET announcement_id = 3 WHERE id = 3;
-- UPDATE orders SET announcement_id = 4 WHERE id = 4;
-- UPDATE orders SET announcement_id = 5 WHERE id = 5;

-- 5. Tabela: announcements_images
-- (Zakładamy binary data jako pusty blob dla przykładu)
INSERT INTO announcements_images (image_data, announcement_id) VALUES (X'010203', 1);
INSERT INTO announcements_images (image_data, announcement_id) VALUES (X'040506', 2);
INSERT INTO announcements_images (image_data, announcement_id) VALUES (X'070809', 3);
INSERT INTO announcements_images (image_data, announcement_id) VALUES (X'0A0B0C', 4);
INSERT INTO announcements_images (image_data, announcement_id) VALUES (X'0D0E0F', 5);

-- 6. Tabela: usersOrder
-- (order_id, buyer_id, seller_id)
INSERT INTO users_order (order_id, buyer_id, seller_id) VALUES (1, 2, 1);
INSERT INTO users_order (order_id, buyer_id, seller_id) VALUES (2, 3, 2);
INSERT INTO users_order (order_id, buyer_id, seller_id) VALUES (3, 4, 3);
INSERT INTO users_order (order_id, buyer_id, seller_id) VALUES (4, 5, 4);
INSERT INTO users_order (order_id, buyer_id, seller_id) VALUES (5, 1, 5);

-- 7. (Opcjonalnie) Naprawa cyklicznej zależności w tabeli announcements dla order_id
-- UPDATE announcements SET order_id = 1 WHERE id = 1;
-- UPDATE announcements SET order_id = 2 WHERE id = 2;
-- UPDATE announcements SET order_id = 3 WHERE id = 3;
-- UPDATE announcements SET order_id = 4 WHERE id = 4;
-- UPDATE announcements SET order_id = 5 WHERE id = 5;
