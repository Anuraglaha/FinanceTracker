CREATE DATABASE finance_tracker;

USE finance_tracker;


CREATE TABLE users
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);


CREATE TABLE transactions (
    transaction_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    category VARCHAR(100) NOT NULL,
    type VARCHAR(20) NOT NULL,
    description VARCHAR(255),
    created_at DATETIME NOT NULL,

    FOREIGN KEY (user_id)
    REFERENCES users(id)
);


CREATE TABLE budgets (
	budget_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    category VARCHAR(100) NOT NULL,
    monthly_limit DECIMAL(10,2) NOT NULL,
    
    FOREIGN KEY (user_id)
    REFERENCES users(id)
);