CREATE DATABASE IF NOT EXISTS test_myolx CHARACTER SET utf8mb4 COLLATE utf8mb4_uca1400_polish_as_cs;
USE test_myolx;

-- 1. Towns Table
CREATE TABLE IF NOT EXISTS towns (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_uca1400_polish_as_cs NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_polish_as_cs;

-- 2. Users Table
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_uca1400_polish_as_cs NOT NULL,
    last_name VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_uca1400_polish_as_cs NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_uca1400_polish_as_cs NOT NULL,
    town_id INT,
    FOREIGN KEY (town_id) REFERENCES towns(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_polish_as_cs;

-- 3. Announcements Images Table
CREATE TABLE IF NOT EXISTS announcements_images (
    id INT AUTO_INCREMENT PRIMARY KEY,
    image_data LONGBLOB NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_polish_as_cs;

-- 4. Announcements Table
CREATE TABLE IF NOT EXISTS announcements (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_uca1400_polish_as_cs NOT NULL,
    description TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_uca1400_polish_as_cs,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE,
    user_id INT,
    announcment_image_id INT,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (announcment_image_id) REFERENCES announcements_images(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_polish_as_cs;

-- 5. Orders Table
CREATE TABLE IF NOT EXISTS orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_uca1400_polish_as_cs NOT NULL,
    status VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_uca1400_polish_as_cs NOT NULL,
    is_correct BOOLEAN DEFAULT TRUE,
    is_protected BOOLEAN DEFAULT FALSE,
    announcements_id INT,
    FOREIGN KEY (announcements_id) REFERENCES announcements(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_polish_as_cs;

-- 6. Users Order Table (Many-to-Many or Association)
CREATE TABLE IF NOT EXISTS users_order (
    order_id INT,
    buyer_id INT,
    seller_id INT,
    PRIMARY KEY (order_id, buyer_id, seller_id),
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (buyer_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (seller_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_polish_as_cs;
