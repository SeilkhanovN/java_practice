DROP TABLE IF EXISTS purchases;
DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS products;

CREATE TABLE customers (
                           id SERIAL PRIMARY KEY,
                           name VARCHAR(255) NOT NULL
);

CREATE TABLE products (
                          id SERIAL PRIMARY KEY,
                          title VARCHAR(255) NOT NULL,
                          price DECIMAL(10, 2) NOT NULL
);

CREATE TABLE purchases (
                           id SERIAL PRIMARY KEY,
                           customer_id INTEGER REFERENCES customers(id),
                           product_id INTEGER REFERENCES products(id),
                           purchase_price DECIMAL(10, 2) NOT NULL,
                           purchase_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);