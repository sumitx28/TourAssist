-- All the DDL and indexes are mentioned here. This is a single sql file which could be executed to create the entire database.

CREATE TABLE `app_user` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`user_type` VARCHAR(20) NOT NULL,
    `email` VARCHAR(50) NOT NULL UNIQUE,
    `password` varchar(255) NOT NULL,
	`created_by` VARCHAR(50) NULL DEFAULT NULL,
	`created_date` DATETIME(6) NULL DEFAULT NULL,
	`last_modified_by` VARCHAR(50) NULL DEFAULT NULL,
	`last_modified_date` DATETIME(6) NULL DEFAULT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `customer` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(50) NOT NULL,
    `mobile` BIGINT(10) NOT NULL UNIQUE,
    `date_of_birth` DATETIME(6) NOT NULL,
    `country` varchar(20) NOT NULL,
    `app_user_id` BIGINT(20) NOT NULL,
	`created_by` VARCHAR(50) NULL DEFAULT NULL,
	`created_date` DATETIME(6) NULL DEFAULT NULL,
	`last_modified_by` VARCHAR(50) NULL DEFAULT NULL,
	`last_modified_date` DATETIME(6) NULL DEFAULT NULL,
	PRIMARY KEY (`id`)
);