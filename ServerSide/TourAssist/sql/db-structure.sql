-- All the DDL and indexes are mentioned here. This is a single sql file which could be executed to create the entire database.

create table app_user (
                          user_type tinyint,
                          id bigint not null auto_increment,
                          email varchar(255),
                          first_name varchar(255),
                          last_name varchar(255),
                          password varchar(255),
                          primary key (id)
) engine=InnoDB


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

--- Core DB structure



CREATE TABLE destination_master (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    city VARCHAR(255) NOT NULL,
    country VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE travel_mode_master (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    `mode` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE resort_master (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    `resort_name` VARCHAR(255) NOT NULL,
    destination_master_id bigint(20) not null,
    PRIMARY KEY (`id`),
	FOREIGN KEY (destination_master_id) REFERENCES destination_master(id)
);

CREATE TABLE suite_master (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    `suite_type` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE amenities_master (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    `amenity_name` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE resort_amenities (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    resort_master_id bigint(20) not null,
    amenity_master_id bigint(20) not null,
    `amenity_name` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (resort_master_id) REFERENCES resort_master(id),
    FOREIGN KEY (amenity_master_id) REFERENCES amenities_master(id)
);

CREATE TABLE activity_master (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    `activity_name` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE guide_master (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    `guide_name` VARCHAR(255) NOT NULL,
    experience_years int not null,
    destination_master_id bigint(20) not null,
    description text,
	FOREIGN KEY (destination_master_id) REFERENCES guide_master(id),
    PRIMARY KEY (`id`)
);

CREATE TABLE package (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    package_name VARCHAR(255) NOT NULL,
    agent_id bigint(20) not null,
    trip_start_date datetime not null,
    trip_end_date datetime not null,
    source_id bigint(20) not null,
    destination_id bigint(20) not null,
    package_created_date datetime not null,
    is_customizable tinyint(1) not null,
    `status` tinyint(1) not null,
	FOREIGN KEY (agent_id) REFERENCES agent(id),
	FOREIGN KEY (source_id) REFERENCES destination_master(id),
	FOREIGN KEY (destination_id) REFERENCES destination_master(id),
    PRIMARY KEY (`id`)
);

CREATE TABLE transportation (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    package_id bigint(20) NOT NULL,
    mode_master_id bigint(20) not null,
    price_start_date datetime not null,
    price_expiry_date datetime not null,
    price double not null,
    is_customizable tinyint(1) not null,
    FOREIGN KEY (package_id) REFERENCES package(id),
	FOREIGN KEY (mode_master_id) REFERENCES travel_mode_master(id),
	PRIMARY KEY (`id`)
);

CREATE TABLE stay (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    package_id bigint(20) NOT NULL,
    resort_master_id bigint(20) not null,
    suite_master_id bigint(20) not null,
    price_start_date datetime not null,
    price_expiry_date datetime not null,
    price double not null,
    is_customizable tinyint(1) not null,
    FOREIGN KEY (package_id) REFERENCES package(id),
	FOREIGN KEY (resort_master_id) REFERENCES resort_master(id),
	FOREIGN KEY (resort_master_id) REFERENCES suite_master(id),
	PRIMARY KEY (`id`)
);


CREATE TABLE activity (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    package_id bigint(20) NOT NULL,
    activity_master_id bigint(20) not null,
	activity_date datetime not null,
	price_start_date datetime not null,
    price_expiry_date datetime not null,
    price double not null,
    is_customizable tinyint(1) not null,
    FOREIGN KEY (package_id) REFERENCES package(id),
	FOREIGN KEY (activity_master_id) REFERENCES activity_master(id),
	PRIMARY KEY (`id`)
);

CREATE TABLE tour_guide (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    package_id bigint(20) NOT NULL,
    guide_master_id bigint(20) not null,
	price_start_date datetime not null,
    price_expiry_date datetime not null,
    price double not null,
    is_customizable tinyint(1) not null,
    FOREIGN KEY (package_id) REFERENCES package(id),
	FOREIGN KEY (guide_master_id) REFERENCES tour_guide(id),
	PRIMARY KEY (`id`)
);

CREATE TABLE package_review (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    package_id bigint(20) NOT NULL,
    customer_id bigint(20) not null,
	review_date datetime not null,
    ratings int,
    review_comment text,
    FOREIGN KEY (package_id) REFERENCES package(id),
	FOREIGN KEY (customer_id) REFERENCES customer(id),
	PRIMARY KEY (`id`)
);

---
