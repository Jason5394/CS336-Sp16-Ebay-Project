CREATE DATABASE IF NOT EXISTS projDB;
USE projDB;

DROP TABLE IF EXISTS UpperLimit;
DROP TABLE IF EXISTS Email;
DROP TABLE IF EXISTS Alert;
DROP TABLE IF EXISTS Bid;
DROP TABLE IF EXISTS Item;
DROP TABLE IF EXISTS Auction;
DROP TABLE IF EXISTS Member;


CREATE TABLE Member (
    username 		VARCHAR(20) NOT NULL,
    first_name 		VARCHAR(20) NOT NULL,
    last_name 		VARCHAR(20) NOT NULL,
    password 		VARCHAR(20) NOT NULL,
    is_customer_rep BOOLEAN NOT NULL,
    is_admin 		BOOLEAN NOT NULL,
    FULLTEXT (username),
    PRIMARY KEY (username)
)ENGINE = INNODB;


CREATE TABLE Auction (
    auction_id 				INT	NOT NULL AUTO_INCREMENT,
    start_datetime 			DATETIME NOT NULL,
    end_datetime	 		DATETIME NOT NULL,
    minimum_increment_price FLOAT(8,2) NOT NULL,
    hidden_minimum_price	FLOAT(8,2),
	seller 					VARCHAR(20) NOT NULL,
    top_bid					FLOAT(8,2) NOT NULL,
    bidder					VARCHAR(20),
    winner					VARCHAR(20),
    FOREIGN KEY (seller) REFERENCES Member(username) ON DELETE CASCADE,
    FOREIGN KEY (bidder) REFERENCES Member(username) ON DELETE CASCADE,
    FOREIGN KEY (winner) REFERENCES Member(username) ON DELETE CASCADE,
    PRIMARY KEY (auction_id)
)ENGINE = INNODB;


CREATE TABLE Item (
    item_id 		INT	NOT NULL AUTO_INCREMENT,
    movie_title 	VARCHAR(50) NOT NULL,
    genre	 		VARCHAR(20), 	
    movie_length 	INT,	 		
    description	 	TEXT, 	
    movie_format	VARCHAR(20) NOT NULL,
	seller 			VARCHAR(20) NOT NULL,
	auction_id		INT NOT NULL,
    FULLTEXT (movie_title, description),
    PRIMARY KEY (item_id),
    FOREIGN KEY (seller) REFERENCES Member(username) ON DELETE CASCADE,
    FOREIGN KEY (auction_id) REFERENCES Auction(auction_id) ON DELETE CASCADE
)ENGINE = INNODB;


CREATE TABLE Bid (
	bid_id				INT NOT NULL AUTO_INCREMENT,
    bid_amount 			FLOAT(8,2) NOT NULL,
    creation_datetime	DATETIME NOT NULL,
	bidder 				VARCHAR(20) NOT NULL,
	auction_id			INT NOT NULL,
    FOREIGN KEY (bidder) REFERENCES Member(username) ON DELETE CASCADE,
    FOREIGN KEY (auction_id) REFERENCES Auction(auction_id) ON DELETE CASCADE,
    PRIMARY KEY (bid_id)
)ENGINE = INNODB;


CREATE TABLE Alert (
    alert_id 		INT	NOT NULL AUTO_INCREMENT,
    movie_title 	VARCHAR(50),
    genre	 		VARCHAR(20),
    movie_format	VARCHAR(20) NOT NULL,
	owner			VARCHAR(20) NOT NULL,
    FULLTEXT (movie_title),
    PRIMARY KEY (alert_id),
    FOREIGN KEY (owner) REFERENCES Member(username) ON DELETE CASCADE
)ENGINE = INNODB;


CREATE TABLE Email (
	email_id			INT NOT NULL AUTO_INCREMENT,
	sender				VARCHAR(20) NOT NULL,
    recipient			VARCHAR(20) NOT NULL,
    subject				VARCHAR(100),
    date_time 			DATETIME NOT NULL,
    content				TEXT,
    PRIMARY KEY (email_id),
    FOREIGN KEY (sender) REFERENCES Member(username),
    FOREIGN KEY (recipient) REFERENCES Member(username)
) ENGINE = INNODB;

CREATE TABLE UpperLimit (
	auction_id			INT NOT NULL,
    bidder				VARCHAR(20) NOT NULL,
    upper_limit			FLOAT(8,2) NOT NULL,
    PRIMARY KEY (auction_id, bidder),
    FOREIGN KEY (auction_id) REFERENCES Auction(auction_id),
    FOREIGN KEY (bidder) REFERENCES Member(username)
) ENGINE = INNODB;

