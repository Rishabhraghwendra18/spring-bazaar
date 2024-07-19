CREATE DATABASE IF NOT EXISTS `spring-bazaar`;
USE `spring-bazaar`;

-- USER Table
CREATE TABLE IF NOT EXISTS users(name varchar(100), email varchar(100) NOT NULL PRIMARY KEY, phoneNo varchar(100) NOT NULL, password varchar(65) NOT NULL, role varchar(20));

-- Inventory Table
CREATE TABLE IF NOT EXISTS inventory(id INT PRIMARY KEY AUTO_INCREMENT, sellerId varchar(100), itemQuantity INT, itemTitle varchar(100), itemDescription varchar(500),itemPrice float, itemPhoto varchar(1000), file_name varchar(50), FOREIGN KEY(sellerId) REFERENCES users(email));

-- Orders Table
 CREATE TABLE IF NOT EXISTS orders(orderId INT AUTO_INCREMENT PRIMARY KEY, orderDate Date, buyerId varchar(100), deliveryAddress varchar(500),pinCode INT, itemId INT, productSize VARCHAR(10), orderValue float, isCompleted BOOLEAN DEFAULT FALSE,payment_state VARCHAR(10) NOT NULL , razorpay_payment_id VARCHAR(80) , razorpay_order_id VARCHAR(80) , razorpay_signature VARCHAR(80) , FOREIGN KEY(buyerId) REFERENCES users(email), FOREIGN KEY(itemId) REFERENCES inventory(id));