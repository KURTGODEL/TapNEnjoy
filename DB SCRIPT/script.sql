CREATE DATABASE IF NOT EXISTS tapnenjoy;

use tapnenjoy;

DROP TABLE IF EXISTS products;
CREATE TABLE products (
	product_id INT PRIMARY KEY,
	title VARCHAR(100) NOT NULL,
	price DECIMAL(10, 2) NOT NULL,
	description VARCHAR(1000) NOT NULL,
	stock INT NOT NULL,
    seller_id INT NOT NULL,
    creation DATETIME NOT NULL,
	status BIT DEFAULT 0
);

DROP TABLE IF EXISTS sellers;
CREATE TABLE sellers(
	seller_id INT,
    user_id INT
);

DROP TABLE IF EXISTS users;
CREATE TABLE users (
	user_id INT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    latitude DECIMAL(10, 8) NULL,
    longitude DECIMAL(10, 8) NULL,
    creation DATETIME NOT NULL,
    status BIT DEFAULT 0
);

DROP TABLE IF EXISTS user_watch;
CREATE TABLE user_watch(
	users_watch_id INT PRIMARY KEY,
	user_id INT,
    product_id INT,
    status BIT DEFAULT 0,
    updation DATETIME NULL,
    creation DATETIME NOT NULL
);

DROP TABLE IF EXISTS user_orders;
CREATE TABLE user_orders(
	users_order_id INT PRIMARY KEY,
    user_id INT,
    seller_id INT,
    product_id INT,
    status BIT DEFAULT 0,
    quantity INT NOT NULL,
    updation DATETIME NULL,
    creation DATETIME NOT NULL
);

DROP TABLE IF EXISTS user_offers;
CREATE TABLE user_offers(
	user_offer_id INT PRIMARY KEY,
    user_id INT,
    seller_id INT,
    product_id INT,
    price DECIMAL(10, 2) NOT NULL,
    new_price DECIMAL(10, 2) NOT NULL,
    approved BIT DEFAULT 0,
    status BIT DEFAULT 0,
    creation DATETIME NOT NULL
);


/*
ALTER TABLE users
DROP INDEX users_username_unique;

DROP INDEX products_seller_id_fk ON products;
*/

/* USERS TABLE - FK */
ALTER TABLE users
ADD CONSTRAINT users_username_unique UNIQUE (username);


/* PRODUCTS TABLE - FK */
ALTER TABLE products
ADD FOREIGN KEY products_seller_id_fk (seller_id) REFERENCES users(user_id)
ON UPDATE RESTRICT ON DELETE RESTRICT;


/* SELLER TABLE - FK */
ALTER TABLE sellers
ADD FOREIGN KEY sellers_user_id_fk (user_id) REFERENCES users(user_id)
ON UPDATE RESTRICT ON DELETE RESTRICT;


/* USER WATCH TABLE - FK */
ALTER TABLE user_watch
ADD FOREIGN KEY user_watch_user_id_fk (user_id) REFERENCES users(user_id)
ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE user_watch
ADD FOREIGN KEY user_watch_product_id_fk (product_id) REFERENCES products(product_id)
ON UPDATE RESTRICT ON DELETE RESTRICT;


/* USER ORDERS TABLE - FK */
ALTER TABLE user_orders
ADD FOREIGN KEY user_orders_user_id_fk (user_id) REFERENCES users(user_id)
ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE user_orders
ADD FOREIGN KEY user_orders_seller_id_fk (seller_id) REFERENCES users(user_id)
ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE user_orders
ADD FOREIGN KEY user_orders_product_id_fk (product_id) REFERENCES products(product_id)
ON UPDATE RESTRICT ON DELETE RESTRICT;


/* USER OFFERS TABLE - FK */
ALTER TABLE user_offers
ADD FOREIGN KEY user_offers_user_id_fk (user_id) REFERENCES users(user_id)
ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE user_offers
ADD FOREIGN KEY user_offers_seller_id_fk (seller_id) REFERENCES users(user_id)
ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE user_offers
ADD FOREIGN KEY user_offers_product_id_fk (product_id) REFERENCES products(product_id)
ON UPDATE RESTRICT ON DELETE RESTRICT;
