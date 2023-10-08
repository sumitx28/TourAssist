-- All the DDL and indexes are mentioned here. This is a single sql file which could be executed to create the entire database.

CREATE TABLE `app_user` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`user_type` VARCHAR(20) NOT NULL,
    `email` VARCHAR(50) NOT NULL UNIQUE,
    `password` varchar(255) NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `customer` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(50) NOT NULL,
    `mobile` varchar(20) NOT NULL UNIQUE,
    `date_of_birth` DATETIME(6) NOT NULL,
    `country` varchar(20) NOT NULL,
    `app_user_id` BIGINT(20) NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `agent` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`company_name` VARCHAR(50) NOT NULL,
    `mobile` varchar(20) NOT NULL UNIQUE,
    `employee_count` INT NOT NULL,
    `verification_id` varchar(50) NOT NULL,
    `verification_doc_link` varchar(255) NOT NULL,
    `app_user_id` BIGINT(20) NOT NULL,
	PRIMARY KEY (`id`)
);