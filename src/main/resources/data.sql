-- Insert sample menu categories
INSERT INTO menu_category (name, description) VALUES
(N'Main Course', N'Main dishes'),
(N'Appetizer', N'Light dishes to start your meal'),
(N'Dessert', N'Sweet treats to end your meal'),
(N'Beverage', N'Refreshing drinks');

-- Insert sample customers
INSERT INTO customer (phone_number, points) VALUES
('0123456789', 100),
('0987654321', 50),
('0369852147', 75);

-- Insert sample tables
INSERT INTO restaurant_table (table_number, capacity, status) VALUES
(1, 4, 'AVAILABLE'),
(2, 4, 'AVAILABLE'),
(3, 6, 'AVAILABLE'),
(4, 2, 'AVAILABLE'),
(5, 8, 'AVAILABLE');

-- Insert sample menu items
INSERT INTO menu_item (name, description, price, category_id, status, image_url) VALUES
-- Main Course
(N'Phở Bò', N'Traditional Vietnamese beef noodle soup', 65000, 
    (SELECT id FROM menu_category WHERE name = N'Main Course'), 'AVAILABLE', 
    'https://upload.wikimedia.org/wikipedia/commons/5/53/Pho-Beef-Noodles-2008.jpg'),
(N'Cơm Tấm', N'Broken rice with grilled pork', 55000,
    (SELECT id FROM menu_category WHERE name = N'Main Course'), 'AVAILABLE',
    'https://upload.wikimedia.org/wikipedia/commons/4/4d/C%C6%A1m_t%E1%BA%A5m_s%C6%B0%E1%BB%9Dn_n%C6%B0%E1%BB%9Bng.jpg'),
(N'Bún Bò Huế', N'Spicy beef noodle soup from Hue', 70000,
    (SELECT id FROM menu_category WHERE name = N'Main Course'), 'AVAILABLE',
    'https://upload.wikimedia.org/wikipedia/commons/6/62/B%C3%BAn_b%C3%B2_Hu%E1%BA%BF_-_Flickr_-_Kent_Wang.jpg'),
(N'Cơm Gà', N'Rice with grilled chicken', 50000,
    (SELECT id FROM menu_category WHERE name = N'Main Course'), 'AVAILABLE',
    'https://i.imgur.com/3ZTwCJD.jpg'),
(N'Mì Xào Hải Sản', N'Stir-fried noodles with seafood', 80000,
    (SELECT id FROM menu_category WHERE name = N'Main Course'), 'AVAILABLE',
    'https://i.imgur.com/4ayD7vS.jpg'),

-- Appetizers
(N'Bánh Mì', N'Vietnamese sandwich with pate and vegetables', 35000,
    (SELECT id FROM menu_category WHERE name = N'Appetizer'), 'AVAILABLE',
    'https://upload.wikimedia.org/wikipedia/commons/0/0c/B%C3%A1nh_m%C3%AC_th%E1%BB%8Bt_n%C6%B0%E1%BB%9Bng.jpg'),
(N'Gỏi Cuốn', N'Fresh spring rolls with shrimp', 45000,
    (SELECT id FROM menu_category WHERE name = N'Appetizer'), 'AVAILABLE',
    'https://upload.wikimedia.org/wikipedia/commons/9/99/G%E1%BB%8Fi_cu%E1%BB%91n.jpg'),
(N'Chả Giò', N'Fried spring rolls', 40000,
    (SELECT id FROM menu_category WHERE name = N'Appetizer'), 'AVAILABLE',
    'https://i.imgur.com/2DZyUqn.jpg'),
(N'Gỏi Đu Đủ', N'Papaya salad with shrimp', 55000,
    (SELECT id FROM menu_category WHERE name = N'Appetizer'), 'AVAILABLE',
    'https://i.imgur.com/8v7L5Nm.jpg'),

-- Desserts
(N'Chè Ba Màu', N'Three-color dessert', 25000,
    (SELECT id FROM menu_category WHERE name = N'Dessert'), 'AVAILABLE',
    'https://upload.wikimedia.org/wikipedia/commons/a/a0/Ch%C3%A8_ba_m%C3%A0u.jpg'),
(N'Bánh Flan', N'Vietnamese caramel custard', 20000,
    (SELECT id FROM menu_category WHERE name = N'Dessert'), 'AVAILABLE',
    'https://i.imgur.com/YkzXxYq.jpg'),
(N'Chè Thái', N'Thai-style dessert soup', 30000,
    (SELECT id FROM menu_category WHERE name = N'Dessert'), 'AVAILABLE',
    'https://i.imgur.com/1PQZT8R.jpg'),

-- Beverages
(N'Cà Phê Sữa Đá', N'Vietnamese iced coffee with condensed milk', 25000,
    (SELECT id FROM menu_category WHERE name = N'Beverage'), 'AVAILABLE',
    'https://upload.wikimedia.org/wikipedia/commons/1/1c/Ca_phe_sua_da_2.jpg'),
(N'Trà Đá', N'Vietnamese iced tea', 10000,
    (SELECT id FROM menu_category WHERE name = N'Beverage'), 'AVAILABLE',
    'https://upload.wikimedia.org/wikipedia/commons/b/b4/Tr%C3%A0_%C4%91%C3%A1.jpg'),
(N'Sinh Tố Bơ', N'Avocado smoothie', 35000,
    (SELECT id FROM menu_category WHERE name = N'Beverage'), 'AVAILABLE',
    'https://i.imgur.com/JVxqkr9.jpg'),
(N'Nước Cam', N'Fresh orange juice', 30000,
    (SELECT id FROM menu_category WHERE name = N'Beverage'), 'AVAILABLE',
    'https://i.imgur.com/zXOaXIC.jpg');

-- Insert sample orders
INSERT INTO restaurant_order (table_id, total, status) VALUES
(1, 130000, 'COMPLETED'),
(2, 95000, 'PENDING'),
(4, 180000, 'PREPARING');

-- Insert sample order items
INSERT INTO order_item (order_id, menu_item_id, quantity, price, subtotal) VALUES
(1, 1, 2, 65000, 130000),
(2, 2, 1, 55000, 55000),
(2, 3, 2, 20000, 40000),
(3, 4, 3, 60000, 180000); 