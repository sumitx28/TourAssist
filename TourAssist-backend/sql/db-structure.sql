CREATE TABLE `app_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` enum('ADMIN','AGENT','CUSTOMER','MANAGER') DEFAULT NULL,
  PRIMARY KEY (`id`)
);


CREATE TABLE `customer` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`first_name` VARCHAR(50) NOT NULL,
	`last_name` VARCHAR(50) NOT NULL,
    `mobile` varchar(20) NOT NULL UNIQUE,
    `date_of_birth` DATETIME(6) NOT NULL,
    `country` varchar(20) NOT NULL,
    `app_user_id` BIGINT(20) NOT NULL,
    FOREIGN KEY (app_user_id) REFERENCES app_user(id),
	PRIMARY KEY (`id`)
);

CREATE TABLE `agent` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`company_name` VARCHAR(50) NOT NULL,
    `mobile` varchar(20) NOT NULL UNIQUE,
    `employee_count` INT NOT NULL,
    `verification_id` varchar(50) NOT NULL,
    `app_user_id` BIGINT(20) NOT NULL,
    FOREIGN KEY (app_user_id) REFERENCES app_user(id),
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
	FOREIGN KEY (suite_master_id) REFERENCES suite_master(id),
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
	FOREIGN KEY (guide_master_id) REFERENCES guide_master(id),
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

CREATE TABLE package_media (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    package_id bigint(20) NOT NULL,
    media text,
	`description` text,
    upload_date datetime not null,
    FOREIGN KEY (package_id) REFERENCES package(id),
	PRIMARY KEY (`id`)
);

CREATE TABLE booking (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    package_id BIGINT(20) NOT NULL,
    customer_id BIGINT(20) NOT NULL,
    agent_id BIGINT(20) NOT NULL,
    booking_date datetime not null,
	total_price double not null,
	booking_status varchar(255) not null CHECK (booking_status IN ('CONFIRM', 'PENDING')),
	FOREIGN KEY (package_id) REFERENCES package(id),
	FOREIGN KEY (customer_id) REFERENCES customer(id),
    FOREIGN KEY (agent_id) REFERENCES agent(id),
	PRIMARY KEY (`id`)
);

CREATE TABLE booking_line_item (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    booking_id BIGINT(20) NOT NULL,
    booked_item varchar(255) NOT NULL CHECK (item_name IN ('ACTIVITY', 'GUIDE', 'RESORT', 'TRANSPORTATION')),
    booked_item_id BIGINT(20) NOT NULL,
    price double not null,
	FOREIGN KEY (booking_id) REFERENCES booking(id),
	PRIMARY KEY (`id`)
);

CREATE TABLE payment_transaction (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    transaction_id varchar(255) NOT NULL,
    booking_id BIGINT(20) NOT NULL,
    payment_type varchar(255) not null,
    transaction_status varchar(255) not null CHECK (transaction_status IN ('SUCCESS', 'FAILED')),
    price double not null,
    transaction_date datetime not null,
	FOREIGN KEY (booking_id) REFERENCES booking(id),
	PRIMARY KEY (`id`)
);


CREATE TABLE guest (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    first_name varchar(255)  NOT NULL,
    last_name varchar(255) not null,
	date_of_birth datetime not null,
	booking_id bigint(20) NOT NULL,
    FOREIGN KEY (booking_id) REFERENCES booking(id),
	PRIMARY KEY (`id`)
);



-- Inserts:

-- destination_master

INSERT INTO destination_master (id, city, country) VALUES (1, 'New York', 'United States');
INSERT INTO destination_master (id, city, country) VALUES (2, 'Paris', 'France');
INSERT INTO destination_master (id, city, country) VALUES (3, 'Tokyo', 'Japan');
INSERT INTO destination_master (id, city, country) VALUES (4, 'London', 'United Kingdom');
INSERT INTO destination_master (id, city, country) VALUES (5, 'Sydney', 'Australia');
INSERT INTO destination_master (id, city, country) VALUES (6, 'Rome', 'Italy');
INSERT INTO destination_master (id, city, country) VALUES (7, 'Berlin', 'Germany');
INSERT INTO destination_master (id, city, country) VALUES (8, 'Beijing', 'China');
INSERT INTO destination_master (id, city, country) VALUES (9, 'Cairo', 'Egypt');
INSERT INTO destination_master (id, city, country) VALUES (10, 'Rio de Janeiro', 'Brazil');
INSERT INTO destination_master (id, city, country) VALUES (11, 'Moscow', 'Russia');
INSERT INTO destination_master (id, city, country) VALUES (12, 'Toronto', 'Canada');
INSERT INTO destination_master (id, city, country) VALUES (13, 'Dubai', 'United Arab Emirates');
INSERT INTO destination_master (id, city, country) VALUES (14, 'Seoul', 'South Korea');
INSERT INTO destination_master (id, city, country) VALUES (15, 'Mumbai', 'India');
INSERT INTO destination_master (id, city, country) VALUES (16, 'Cape Town', 'South Africa');
INSERT INTO destination_master (id, city, country) VALUES (17, 'Mexico City', 'Mexico');
INSERT INTO destination_master (id, city, country) VALUES (18, 'Athens', 'Greece');
INSERT INTO destination_master (id, city, country) VALUES (19, 'Bangkok', 'Thailand');
INSERT INTO destination_master (id, city, country) VALUES (20, 'Stockholm', 'Sweden');

--

INSERT INTO travel_mode_master (id, mode) VALUES (1, 'Flight');
INSERT INTO travel_mode_master (id, mode) VALUES (2, 'Train');
INSERT INTO travel_mode_master (id, mode) VALUES (3, 'Cruise');
INSERT INTO travel_mode_master (id, mode) VALUES (4, 'Bus');

--

INSERT INTO resort_master (id, resort_name, destination_master_id) VALUES (1, 'Seaside Resort', 5);
INSERT INTO resort_master (id, resort_name, destination_master_id) VALUES (2, 'Mountain Retreat', 12);
INSERT INTO resort_master (id, resort_name, destination_master_id) VALUES (3, 'Tropical Oasis', 8);
INSERT INTO resort_master (id, resort_name, destination_master_id) VALUES (4, 'Urban Escape', 16);
INSERT INTO resort_master (id, resort_name, destination_master_id) VALUES (5, 'Lakeside Lodge', 3);
INSERT INTO resort_master (id, resort_name, destination_master_id) VALUES (6, 'Ski Chalet', 10);
INSERT INTO resort_master (id, resort_name, destination_master_id) VALUES (7, 'Desert Mirage', 7);
INSERT INTO resort_master (id, resort_name, destination_master_id) VALUES (8, 'Island Paradise Resort', 18);
INSERT INTO resort_master (id, resort_name, destination_master_id) VALUES (9, 'City Lights Hotel', 14);
INSERT INTO resort_master (id, resort_name, destination_master_id) VALUES (10, 'Historic Inn', 2);
INSERT INTO resort_master (id, resort_name, destination_master_id) VALUES (11, 'Sunset Beach Resort', 6);
INSERT INTO resort_master (id, resort_name, destination_master_id) VALUES (12, 'Alpine Lodge', 11);
INSERT INTO resort_master (id, resort_name, destination_master_id) VALUES (13, 'Mystic Sands Resort', 19);
INSERT INTO resort_master (id, resort_name, destination_master_id) VALUES (14, 'Downtown Suites', 15);
INSERT INTO resort_master (id, resort_name, destination_master_id) VALUES (15, 'Jungle Retreat', 9);
INSERT INTO resort_master (id, resort_name, destination_master_id) VALUES (16, 'Riverfront Resort', 4);
INSERT INTO resort_master (id, resort_name, destination_master_id) VALUES (17, 'Snowy Peaks Lodge', 13);
INSERT INTO resort_master (id, resort_name, destination_master_id) VALUES (18, 'Sunny Side Villas', 1);
INSERT INTO resort_master (id, resort_name, destination_master_id) VALUES (19, 'Royal Gardens Resort', 17);
INSERT INTO resort_master (id, resort_name, destination_master_id) VALUES (20, 'Harbor View Hotel', 20);


INSERT INTO resort_master (resort_name, destination_master_id) VALUES ('Sunny Shores Retreat', 1);
INSERT INTO resort_master (resort_name, destination_master_id) VALUES ('Mountain Vista Lodge', 2);
INSERT INTO resort_master (resort_name, destination_master_id) VALUES ('Ocean Breeze Resort', 3);
INSERT INTO resort_master (resort_name, destination_master_id) VALUES ('Tranquil Pines Lodge', 4);
INSERT INTO resort_master (resort_name, destination_master_id) VALUES ('Golden Sands Oasis', 5);
INSERT INTO resort_master (resort_name, destination_master_id) VALUES ('Alpine Serenity Resort', 6);
INSERT INTO resort_master (resort_name, destination_master_id) VALUES ('Tropical Paradise Inn', 7);
INSERT INTO resort_master (resort_name, destination_master_id) VALUES ('Majestic Peaks Retreat', 8);
INSERT INTO resort_master (resort_name, destination_master_id) VALUES ('Whispering Palms Resort', 9);
INSERT INTO resort_master (resort_name, destination_master_id) VALUES ('Eternal Springs Lodge', 10);
INSERT INTO resort_master (resort_name, destination_master_id) VALUES ('Sunset Haven Resort', 1);
INSERT INTO resort_master (resort_name, destination_master_id) VALUES ('Emerald Isle Lodge', 2);
INSERT INTO resort_master (resort_name, destination_master_id) VALUES ('Azure Waters Retreat', 3);
INSERT INTO resort_master (resort_name, destination_master_id) VALUES ('Tranquil Oasis Lodge', 4);
INSERT INTO resort_master (resort_name, destination_master_id) VALUES ('Silver Sands Resort', 5);
INSERT INTO resort_master (resort_name, destination_master_id) VALUES ('Alpine Escape Lodge', 6);
INSERT INTO resort_master (resort_name, destination_master_id) VALUES ('Coral Coast Oasis', 7);
INSERT INTO resort_master (resort_name, destination_master_id) VALUES ('Mystic Valley Retreat', 8);
INSERT INTO resort_master (resort_name, destination_master_id) VALUES ('Serene Shores Resort', 9);
INSERT INTO resort_master (resort_name, destination_master_id) VALUES ('Eternal Bliss Lodge', 10);
INSERT INTO resort_master (resort_name, destination_master_id) VALUES ('Harmony Hills Resort', 1);
INSERT INTO resort_master (resort_name, destination_master_id) VALUES ('Tranquil Waters Lodge', 2);
INSERT INTO resort_master (resort_name, destination_master_id) VALUES ('Tropical Breeze Resort', 3);
INSERT INTO resort_master (resort_name, destination_master_id) VALUES ('Mountain Retreat Lodge', 4);
INSERT INTO resort_master (resort_name, destination_master_id) VALUES ('Golden Sunset Resort', 5);
INSERT INTO resort_master (resort_name, destination_master_id) VALUES ('Whispering Pines Lodge', 6);
INSERT INTO resort_master (resort_name, destination_master_id) VALUES ('Paradise Cove Oasis', 7);
INSERT INTO resort_master (resort_name, destination_master_id) VALUES ('Ethereal Peaks Retreat', 8);
INSERT INTO resort_master (resort_name, destination_master_id) VALUES ('Sapphire Shores Resort', 9);
INSERT INTO resort_master (resort_name, destination_master_id) VALUES ('Timeless Meadows Lodge', 10);
INSERT INTO resort_master (resort_name, destination_master_id) VALUES ('Seaside Serenity Resort', 1);
INSERT INTO resort_master (resort_name, destination_master_id) VALUES ('Majestic Ridge Lodge', 2);
INSERT INTO resort_master (resort_name, destination_master_id) VALUES ('Coastal Haven Retreat', 3);
INSERT INTO resort_master (resort_name, destination_master_id) VALUES ('Peaceful Pines Lodge', 4);
INSERT INTO resort_master (resort_name, destination_master_id) VALUES ('Sun-kissed Sands Resort', 5);
INSERT INTO resort_master (resort_name, destination_master_id) VALUES ('Snowy Summit Lodge', 6);
INSERT INTO resort_master (resort_name, destination_master_id) VALUES ('Lagoon Paradise Oasis', 7);
INSERT INTO resort_master (resort_name, destination_master_id) VALUES ('Eternal Peaks Retreat', 8);
INSERT INTO resort_master (resort_name, destination_master_id) VALUES ('Tranquil Tides Resort', 9);
INSERT INTO resort_master (resort_name, destination_master_id) VALUES ('Harmony Meadows Lodge', 10);
INSERT INTO resort_master (resort_name, destination_master_id) VALUES ('Garden of Eden Resort', 1);
INSERT INTO resort_master (resort_name, destination_master_id) VALUES ('Serenity Springs Lodge', 2);
INSERT INTO resort_master (resort_name, destination_master_id) VALUES ('Blue Lagoon Retreat', 3);
INSERT INTO resort_master (resort_name, destination_master_id) VALUES ('Whispering Willows Lodge', 4);
INSERT INTO resort_master (resort_name, destination_master_id) VALUES ('Crimson Sunset Resort', 5);
INSERT INTO resort_master (resort_name, destination_master_id) VALUES ('Misty Mountain Lodge', 6);
INSERT INTO resort_master (resort_name, destination_master_id) VALUES ('Golden Horizon Oasis', 7);
INSERT INTO resort_master (resort_name, destination_master_id) VALUES ('Eternal Harmony Retreat', 8);
INSERT INTO resort_master (resort_name, destination_master_id) VALUES ('Tranquil Gardens Resort', 9);
INSERT INTO resort_master (resort_name, destination_master_id) VALUES ('Celestial Peaks Lodge', 10);

--

INSERT INTO suite_master (id, suite_type) VALUES (1, 'Standard Suite');
INSERT INTO suite_master (id, suite_type) VALUES (2, 'Deluxe Suite');
INSERT INTO suite_master (id, suite_type) VALUES (3, 'Executive Suite');
INSERT INTO suite_master (id, suite_type) VALUES (4, 'Presidential Suite');
INSERT INTO suite_master (id, suite_type) VALUES (5, 'Family Suite');
INSERT INTO suite_master (id, suite_type) VALUES (6, 'Poolside Suite');
INSERT INTO suite_master (id, suite_type) VALUES (7, 'Pet-Friendly Suite');
INSERT INTO suite_master (id, suite_type) VALUES (8, 'Oceanfront Suite');


--

INSERT INTO amenities_master (id, amenity_name) VALUES (1, 'Swimming Pools');
INSERT INTO amenities_master (id, amenity_name) VALUES (2, 'Restaurants and Bars');
INSERT INTO amenities_master (id, amenity_name) VALUES (3, 'Golf Course');
INSERT INTO amenities_master (id, amenity_name) VALUES (4, 'Tennis Courts');
INSERT INTO amenities_master (id, amenity_name) VALUES (5, 'Fitness Center');
INSERT INTO amenities_master (id, amenity_name) VALUES (6, 'Gift Shops and Boutiques');
INSERT INTO amenities_master (id, amenity_name) VALUES (7, 'Airport Transfer');
INSERT INTO amenities_master (id, amenity_name) VALUES (8, 'Valet Parking');
INSERT INTO amenities_master (id, amenity_name) VALUES (9, 'Library or Reading Lounge');

--

-- Insert statements for resort_master_id = 1

INSERT INTO resort_amenities (id, resort_master_id, amenity_master_id) VALUES (1, 1, 3);
INSERT INTO resort_amenities (id, resort_master_id, amenity_master_id) VALUES (2, 1, 7);
INSERT INTO resort_amenities (id, resort_master_id, amenity_master_id) VALUES (3, 1, 2);
INSERT INTO resort_amenities (id, resort_master_id, amenity_master_id) VALUES (4, 1, 5);
INSERT INTO resort_amenities (id, resort_master_id, amenity_master_id) VALUES (5, 1, 9);

-- Insert statements for resort_master_id = 2

INSERT INTO resort_amenities (id, resort_master_id, amenity_master_id) VALUES (6, 2, 4);
INSERT INTO resort_amenities (id, resort_master_id, amenity_master_id) VALUES (7, 2, 1);
INSERT INTO resort_amenities (id, resort_master_id, amenity_master_id) VALUES (8, 2, 8);
INSERT INTO resort_amenities (id, resort_master_id, amenity_master_id) VALUES (9, 2, 6);
INSERT INTO resort_amenities (id, resort_master_id, amenity_master_id) VALUES (10, 2, 3);

-- Insert statements for resort_master_id = 3

INSERT INTO resort_amenities (id, resort_master_id, amenity_master_id) VALUES (11, 3, 2);
INSERT INTO resort_amenities (id, resort_master_id, amenity_master_id) VALUES (12, 3, 6);
INSERT INTO resort_amenities (id, resort_master_id, amenity_master_id) VALUES (13, 3, 8);
INSERT INTO resort_amenities (id, resort_master_id, amenity_master_id) VALUES (14, 3, 4);
INSERT INTO resort_amenities (id, resort_master_id, amenity_master_id) VALUES (15, 3, 1);


-- Insert statements for resort_master_id = 20

INSERT INTO resort_amenities (id, resort_master_id, amenity_master_id) VALUES (96, 20, 5);
INSERT INTO resort_amenities (id, resort_master_id, amenity_master_id) VALUES (97, 20, 9);
INSERT INTO resort_amenities (id, resort_master_id, amenity_master_id) VALUES (98, 20, 3);
INSERT INTO resort_amenities (id, resort_master_id, amenity_master_id) VALUES (99, 20, 7);
INSERT INTO resort_amenities (id, resort_master_id, amenity_master_id) VALUES (100, 20, 2);

--


--

INSERT INTO activity_master (id, activity_name) VALUES (1, 'Guided City Tours');
INSERT INTO activity_master (id, activity_name) VALUES (2, 'Snorkeling Adventure');
INSERT INTO activity_master (id, activity_name) VALUES (3, 'Cooking Classes');
INSERT INTO activity_master (id, activity_name) VALUES (4, 'Sunset Cruise');
INSERT INTO activity_master (id, activity_name) VALUES (5, 'Hiking Excursion');
INSERT INTO activity_master (id, activity_name) VALUES (6, 'Wine Tasting Tour');
INSERT INTO activity_master (id, activity_name) VALUES (7, 'Cultural Performances');
INSERT INTO activity_master (id, activity_name) VALUES (8, 'Hot Air Balloon Ride');
INSERT INTO activity_master (id, activity_name) VALUES (9, 'Wildlife Safari');
INSERT INTO activity_master (id, activity_name) VALUES (10, 'Zip-lining Adventure');
INSERT INTO activity_master (id, activity_name) VALUES (11, 'Beach Picnic');
INSERT INTO activity_master (id, activity_name) VALUES (12, 'Historical Walking Tour');
INSERT INTO activity_master (id, activity_name) VALUES (13, 'Scuba Diving Expedition');
INSERT INTO activity_master (id, activity_name) VALUES (14, 'Spa and Wellness Retreat');
INSERT INTO activity_master (id, activity_name) VALUES (15, 'Golfing Experience');
INSERT INTO activity_master (id, activity_name) VALUES (16, 'Art and Craft Workshops');
INSERT INTO activity_master (id, activity_name) VALUES (17, 'River Rafting Adventure');
INSERT INTO activity_master (id, activity_name) VALUES (18, 'Bicycle Tour');
INSERT INTO activity_master (id, activity_name) VALUES (19, 'Photography Safari');
INSERT INTO activity_master (id, activity_name) VALUES (20, 'Cultural Immersion Day');

--

INSERT INTO guide_master (id, guide_name, experience_years, destination_master_id, description) VALUES (1, 'Maria Rodriguez', 10, 1, 'Passionate about showcasing the rich history and vibrant culture of Spain.');
INSERT INTO guide_master (id, guide_name, experience_years, destination_master_id, description) VALUES (2, 'Raj Gupta', 8, 2, 'Specializes in guiding travelers through the diverse landscapes and historical wonders of India.');
INSERT INTO guide_master (id, guide_name, experience_years, destination_master_id, description) VALUES (3, 'Emily Thompson', 12, 3, 'Dedicated to showcasing the beauty and diversity of America''s landscapes and cultures.');
INSERT INTO guide_master (id, guide_name, experience_years, destination_master_id, description) VALUES (4, 'Luca Moretti', 15, 4, 'Offers a delightful journey through the art, cuisine, and history of Italy.');
INSERT INTO guide_master (id, guide_name, experience_years, destination_master_id, description) VALUES (5, 'Mei Chen', 7, 5, 'Passionate about introducing travelers to the rich heritage and modern wonders of China.');
INSERT INTO guide_master (id, guide_name, experience_years, destination_master_id, description) VALUES (6, 'Ahmed Hassan', 9, 6, 'Brings ancient civilizations to life with extensive knowledge of historical sites and cultural traditions in Egypt.');
INSERT INTO guide_master (id, guide_name, experience_years, destination_master_id, description) VALUES (7, 'Isabella Morales', 11, 7, 'Combines a deep love for Mexico''s traditions with a modern perspective.');
INSERT INTO guide_master (id, guide_name, experience_years, destination_master_id, description) VALUES (8, 'Hiroshi Tanaka', 14, 8, 'Provides an immersive journey through the juxtaposition of ancient traditions and cutting-edge technology in Japan.');
INSERT INTO guide_master (id, guide_name, experience_years, destination_master_id, description) VALUES (9, 'Svetlana Ivanova', 6, 9, 'Shares the rich history and cultural treasures of Russia.');
INSERT INTO guide_master (id, guide_name, experience_years, destination_master_id, description) VALUES (10, 'Carlos Fernandez', 13, 10, 'Offers an exploration of the diverse landscapes, tango rhythms, and culinary delights of Argentina.');

INSERT INTO guide_master (guide_name, experience_years, destination_master_id, description) VALUES ('John Smith', 5, 1, 'Experienced guide with a passion for adventure.');
INSERT INTO guide_master (guide_name, experience_years, destination_master_id, description) VALUES ('Emily Johnson', 3, 2, 'Knowledgeable guide specializing in nature walks.');
INSERT INTO guide_master (guide_name, experience_years, destination_master_id, description) VALUES ('Daniel Turner', 8, 3, 'Outdoor enthusiast with expertise in mountain trails.');
INSERT INTO guide_master (guide_name, experience_years, destination_master_id, description) VALUES ('Olivia Davis', 6, 4, 'Skilled guide for historical and cultural tours.');
INSERT INTO guide_master (guide_name, experience_years, destination_master_id, description) VALUES ('Michael Brown', 4, 5, 'Passionate guide for wildlife safaris.');
INSERT INTO guide_master (guide_name, experience_years, destination_master_id, description) VALUES ('Sophia White', 7, 6, 'Adventurous guide with knowledge of river expeditions.');
INSERT INTO guide_master (guide_name, experience_years, destination_master_id, description) VALUES ('Matthew Miller', 2, 7, 'Friendly guide for family-friendly tours.');
INSERT INTO guide_master (guide_name, experience_years, destination_master_id, description) VALUES ('Emma Wilson', 9, 8, 'Experienced guide specializing in birdwatching.');
INSERT INTO guide_master (guide_name, experience_years, destination_master_id, description) VALUES ('Christopher Davis', 5, 9, 'Knowledgeable guide for photography tours.');
INSERT INTO guide_master (guide_name, experience_years, destination_master_id, description) VALUES ('Ava Anderson', 6, 10, 'Passionate guide with expertise in beach excursions.');
INSERT INTO guide_master (guide_name, experience_years, destination_master_id, description) VALUES ('Liam Robinson', 3, 1, 'Nature lover and guide for eco-friendly tours.');
INSERT INTO guide_master (guide_name, experience_years, destination_master_id, description) VALUES ('Mia Martinez', 7, 2, 'Skilled guide for hiking and trekking adventures.');
INSERT INTO guide_master (guide_name, experience_years, destination_master_id, description) VALUES ('Ethan Turner', 4, 3, 'Outdoor enthusiast with knowledge of camping trips.');
INSERT INTO guide_master (guide_name, experience_years, destination_master_id, description) VALUES ('Isabella Hall', 8, 4, 'Adventurous guide for extreme sports tours.');
INSERT INTO guide_master (guide_name, experience_years, destination_master_id, description) VALUES ('Noah Scott', 6, 5, 'Experienced guide for cultural and historical explorations.');
INSERT INTO guide_master (guide_name, experience_years, destination_master_id, description) VALUES ('Avery Garcia', 5, 6, 'Passionate guide with expertise in rainforest tours.');
INSERT INTO guide_master (guide_name, experience_years, destination_master_id, description) VALUES ('Logan Adams', 2, 7, 'Friendly guide for family adventures.');
INSERT INTO guide_master (guide_name, experience_years, destination_master_id, description) VALUES ('Grace Hall', 9, 8, 'Skilled guide for wildlife photography.');
INSERT INTO guide_master (guide_name, experience_years, destination_master_id, description) VALUES ('Elijah Turner', 4, 9, 'Outdoor enthusiast with a love for mountain trails.');
INSERT INTO guide_master (guide_name, experience_years, destination_master_id, description) VALUES ('Aria White', 7, 10, 'Adventurous guide for water sports excursions.');

--
