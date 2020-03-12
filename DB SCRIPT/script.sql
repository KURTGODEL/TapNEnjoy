use tapnenjoy;

CREATE TABLE IF NOT EXISTS products (
	product_id INT PRIMARY KEY,
	title VARCHAR(100),
	price DECIMAL(10, 2),
	description VARCHAR(1000),
	quantity INT,
    seller_id INT,
    creation DATETIME,
	status BIT
);

CREATE TABLE IF NOT EXISTS sellers(
	seller_id INT,
    user_id INT
);

CREATE TABLE IF NOT EXISTS users (
	user_id INT PRIMARY KEY,
    name VARCHAR(50),
    username VARCHAR(50),
    password VARCHAR(50),
    latitude DECIMAL(10, 8),
    longitude DECIMAL(10, 8),
    creation DATETIME,
    status BIT
);

CREATE TABLE IF NOT EXISTS users_watch(
	users_watch_id INT PRIMARY KEY,
	user_id INT,
    status BIT,
    updation DATETIME,
    creation DATETIME
);

CREATE TABLE IF NOT EXISTS users_order(
	users_order_id INT PRIMARY KEY,
    user_id INT,
    status BIT,
    updation DATETIME,
    creation DATETIME
);