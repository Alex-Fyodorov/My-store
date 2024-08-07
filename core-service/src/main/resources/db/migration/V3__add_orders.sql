CREATE TABLE IF NOT EXISTS orders (
id BIGINT PRIMARY KEY AUTO_INCREMENT,
username VARCHAR(255) NOT NULL,
phone VARCHAR(255) NOT NULL,
address VARCHAR(255) NOT NULL,
total_price NUMERIC (8, 2),
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS order_items (
id BIGINT PRIMARY KEY AUTO_INCREMENT,
order_id BIGINT REFERENCES orders (id),
product_id BIGINT REFERENCES products (id),
price_per_product NUMERIC (8, 2),
quantity INT,
price NUMERIC(8, 2),
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);