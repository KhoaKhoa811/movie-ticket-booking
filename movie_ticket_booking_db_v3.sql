BEGIN;

CREATE TABLE account (
	id SERIAL PRIMARY KEY,
	email VARCHAR(255) UNIQUE NOT NULL,
	password VARCHAR(255) NOT NULL
);

CREATE TABLE roles (
	id SERIAL PRIMARY KEY,
	name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE account_roles (
	account_id INT NOT NULL,
	role_id INT NOT NULL,
	PRIMARY KEY (account_id, role_id)
);

CREATE TABLE permissions (
	id SERIAL PRIMARY KEY,
	name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE roles_permissions (
	role_id INT NOT NULL,
	permission_id INT NOT NULL,
	PRIMARY KEY (role_id, permission_id)
);

CREATE TABLE movie (
	id SERIAL PRIMARY KEY,
	title VARCHAR(255) NOT NULL,
	director VARCHAR(255),
	image_path VARCHAR(255),
	description TEXT,
	duration_in_mins INT,
	language VARCHAR(100),
	release_date DATE,
	country VARCHAR(100),
	style VARCHAR(100)
);

CREATE TABLE genre (
	id SERIAL PRIMARY KEY,
	name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE movie_genre (
	movie_id INT NOT NULL,
	genre_id INT NOT NULL,
	PRIMARY KEY (movie_id, genre_id)
);

CREATE TABLE cinema (
	id SERIAL PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	address VARCHAR(255) NOT NULL,
	street VARCHAR(255) NOT NULL,
	ward VARCHAR(255) NOT NULL,
	district VARCHAR(255) NOT NULL,
	city VARCHAR(255) NOT NULL,
	phone_number VARCHAR(15)
);

CREATE TABLE cinema_hall (
	id SERIAL PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	total_seat INT NOT NULL
);

CREATE TABLE cinema_hall_seat (
	id SERIAL PRIMARY KEY,
	seat_row CHAR(1) NOT NULL,
	seat_column INT NOT NULL,
	type VARCHAR(255) NOT NULL
);

CREATE TABLE shows (
	id SERIAL PRIMARY KEY,
	show_date DATE NOT NULL,
	start_time TIME NOT NULL,
	end_time TIME NOT NULL,
	type VARCHAR(255)
);

CREATE TABLE show_seat (
	id SERIAL PRIMARY KEY,
	status VARCHAR(255) NOT NULL
);

CREATE TABLE booking (
	id SERIAL PRIMARY KEY,
	number_of_seats INT NOT NULL,
	create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	status VARCHAR(255) NOT NULL
);

CREATE TABLE booking_detail (
	id SERIAL PRIMARY KEY,
	booking_id INT NOT NULL,
	show_seat_id INT NOT NULL
);

CREATE TABLE payment (
	id SERIAL PRIMARY KEY,
	create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	status VARCHAR(255),
	transaction_id INT
);

CREATE TABLE cinema_images (
	id SERIAL PRIMARY KEY,
	img_url VARCHAR(255) NOT NULL
);




ALTER TABLE account_roles
ADD CONSTRAINT fk_ar_account FOREIGN KEY (account_id)
REFERENCES account(id);

ALTER TABLE account_roles
ADD CONSTRAINT fk_ar_roles FOREIGN KEY (role_id)
REFERENCES roles(id);

ALTER TABLE roles_permissions
ADD CONSTRAINT fk_rp_roles FOREIGN KEY (role_id)
REFERENCES roles(id);

ALTER TABLE roles_permissions
ADD CONSTRAINT fk_rp_permissions FOREIGN KEY (permission_id)
REFERENCES permissions(id);

ALTER TABLE movie_genre
ADD CONSTRAINT fk_mg_movie FOREIGN KEY (movie_id)
REFERENCES movie(id);

ALTER TABLE movie_genre
ADD CONSTRAINT fk_mg_genre FOREIGN KEY (genre_id)
REFERENCES genre(id);

ALTER TABLE cinema_hall ADD COLUMN cinema_id INT NOT NULL;
ALTER TABLE cinema_hall
ADD CONSTRAINT fk_ch_cinema FOREIGN KEY (cinema_id)
REFERENCES cinema(id);

ALTER TABLE cinema_hall_seat ADD COLUMN cinema_hall_id INT NOT NULL;
AlTER TABLE cinema_hall_seat
ADD CONSTRAINT fk_chs_ch FOREIGN KEY (cinema_hall_id)
REFERENCES cinema_hall(id);

ALTER TABLE shows ADD COLUMN movie_id INT NOT NULL;
ALTER TABLE shows
ADD CONSTRAINT fk_shows_movie FOREIGN KEY (movie_id)
REFERENCES movie(id);

ALTER TABLE shows ADD COLUMN cinema_hall_id INT NOT NULL;
ALTER TABLE shows
ADD CONSTRAINT fk_shows_ch FOREIGN KEY (cinema_hall_id)
REFERENCES cinema_hall(id);


ALTER TABLE show_seat ADD COLUMN show_id INT NOT NULL;
ALTER TABLE show_seat
ADD CONSTRAINT fk_ss_shows FOREIGN KEY (show_id)
REFERENCES shows(id);

ALTER TABLE show_seat ADD COLUMN cinema_hall_seat_id INT NOT NULL;
ALTER TABLE show_seat
ADD CONSTRAINT fk_ss_chs FOREIGN KEY (cinema_hall_seat_id)
REFERENCES cinema_hall_seat(id);

ALTER TABLE booking ADD COLUMN account_id INT NOT NULL;
ALTER TABLE booking
ADD CONSTRAINT fk_booking_account FOREIGN KEY (account_id)
REFERENCES account(id);

ALTER TABLE booking_detail
ADD CONSTRAINT fk_bd_booking FOREIGN KEY (booking_id)
REFERENCES booking(id);

ALTER TABLE booking_detail
ADD CONSTRAINT fk_bd_ss FOREIGN KEY (show_seat_id)
REFERENCES show_seat(id);

ALTER TABLE payment ADD COLUMN booking_id INT NOT NULL;
ALTER TABLE payment
ADD CONSTRAINT fk_payment_booking FOREIGN KEY (booking_id)
REFERENCES booking(id);

ALTER TABLE cinema_images ADD COLUMN cinema_id INT NOT NULL;
ALTER TABLE cinema_images
ADD CONSTRAINT  fk_ci_cinema FOREIGN KEY (cinema_id)
REFERENCES cinema(id);

COMMIT;
