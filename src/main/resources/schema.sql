-- Drop tables if they exist in correct order
IF OBJECT_ID('order_item', 'U') IS NOT NULL DROP TABLE order_item;
IF OBJECT_ID('restaurant_order', 'U') IS NOT NULL DROP TABLE restaurant_order;
IF OBJECT_ID('cart_item', 'U') IS NOT NULL DROP TABLE cart_item;
IF OBJECT_ID('cart', 'U') IS NOT NULL DROP TABLE cart;
IF OBJECT_ID('menu_item', 'U') IS NOT NULL DROP TABLE menu_item;
IF OBJECT_ID('menu_category', 'U') IS NOT NULL DROP TABLE menu_category;
IF OBJECT_ID('restaurant_table', 'U') IS NOT NULL DROP TABLE restaurant_table;
IF OBJECT_ID('customer', 'U') IS NOT NULL DROP TABLE customer;

-- Create menu_category table
CREATE TABLE menu_category (
    id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(100) NOT NULL,
    description NVARCHAR(500)
);

-- Create menu_item table
CREATE TABLE menu_item (
    id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(255) NOT NULL,
    description NVARCHAR(500),
    price DECIMAL(10,2) NOT NULL,
    category_id INT,
    image_url NVARCHAR(500),
    status NVARCHAR(20) DEFAULT 'AVAILABLE',
    FOREIGN KEY (category_id) REFERENCES menu_category(id)
);

-- Create restaurant_table table
CREATE TABLE restaurant_table (
    id INT IDENTITY(1,1) PRIMARY KEY,
    table_number INT NOT NULL UNIQUE,
    capacity INT NOT NULL,
    status NVARCHAR(20) DEFAULT 'AVAILABLE'
);

-- Create customer table
CREATE TABLE customer (
    id INT IDENTITY(1,1) PRIMARY KEY,
    phone_number NVARCHAR(15) NOT NULL UNIQUE,
    points INT DEFAULT 0,
    created_at DATETIME DEFAULT GETDATE()
);

-- Create cart table
CREATE TABLE cart (
    id INT IDENTITY(1,1) PRIMARY KEY,
    table_id INT NOT NULL,
    total DECIMAL(10,2) DEFAULT 0,
    created_at DATETIME DEFAULT GETDATE(),
    updated_at DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (table_id) REFERENCES restaurant_table(id)
);

-- Create cart_item table
CREATE TABLE cart_item (
    id INT IDENTITY(1,1) PRIMARY KEY,
    cart_id INT NOT NULL,
    menu_item_id INT NOT NULL,
    quantity INT DEFAULT 1,
    subtotal DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (cart_id) REFERENCES cart(id),
    FOREIGN KEY (menu_item_id) REFERENCES menu_item(id)
);

-- Create restaurant_order table
CREATE TABLE restaurant_order (
    id INT IDENTITY(1,1) PRIMARY KEY,
    table_id INT NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    status NVARCHAR(20) DEFAULT 'PENDING',
    created_at DATETIME DEFAULT GETDATE(),
    updated_at DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (table_id) REFERENCES restaurant_table(id)
);

-- Create order_item table
CREATE TABLE order_item (
    id INT IDENTITY(1,1) PRIMARY KEY,
    order_id INT NOT NULL,
    menu_item_id INT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES restaurant_order(id),
    FOREIGN KEY (menu_item_id) REFERENCES menu_item(id)
); 