-- Database creation
CREATE DATABASE `bibgyani`
	DEFAULT CHARACTER SET utf8
	DEFAULT COLLATE utf8_general_ci;

-- Users creation
CREATE USER `bibgyani`@`localhost` IDENTIFIED BY 'bibgyani';
GRANT ALL PRIVILEGES ON bibgyani.* TO `bibgyani`@`localhost`;
CREATE USER `bibgyani`@`%` IDENTIFIED BY 'bibgyani';
GRANT ALL PRIVILEGES ON bibgyani.* TO `bibgyani`@`%`;
