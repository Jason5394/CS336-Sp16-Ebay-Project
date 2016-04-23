CREATE DATABASE IF NOT EXISTS projDB;
USE projDB;

DROP TABLE IF EXISTS Answer;
DROP TABLE IF EXISTS Question;
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
    PRIMARY KEY (username)
)ENGINE = INNODB;


CREATE TABLE Auction (
    auction_id 				INT AUTO_INCREMENT,
    start_datetime 			DATETIME NOT NULL,
    end_datetime	 		DATETIME NOT NULL,
    minimum_increment_price FLOAT(8,2) NOT NULL,
    hidden_minimum_price	FLOAT(8,2),
	seller 					VARCHAR(20) NOT NULL,
    FOREIGN KEY (seller) REFERENCES Member(username) ON DELETE CASCADE,
	PRIMARY KEY (auction_id)
)ENGINE = INNODB;


CREATE TABLE Item (
    item_id 		INT AUTO_INCREMENT,
    movie_title 	VARCHAR(50) NOT NULL,
    genre	 		VARCHAR(20), 	
    movie_length 	INT,	 		
    description	 	TEXT, 	
    movie_format	VARCHAR(20) NOT NULL,
	seller 			VARCHAR(20) NOT NULL,
	auction_id		INT NOT NULL,
    PRIMARY KEY (item_id),
    FOREIGN KEY (seller) REFERENCES Member(username) ON DELETE CASCADE,
    FOREIGN KEY (auction_id) REFERENCES Auction(auction_id) ON DELETE CASCADE
)ENGINE = INNODB;


CREATE TABLE Bid (
    bid_amount 			FLOAT(8,2) NOT NULL,
    creation_datetime	DATETIME NOT NULL,
	seller 				VARCHAR(20) NOT NULL,
	auction_id			INT NOT NULL,
	item_id				INT NOT NULL,
    FOREIGN KEY (seller) REFERENCES Member(username) ON DELETE CASCADE,
    FOREIGN KEY (auction_id) REFERENCES Auction(auction_id) ON DELETE CASCADE,
    FOREIGN KEY (item_id) REFERENCES Item(item_id) ON DELETE CASCADE,
    PRIMARY KEY (seller, auction_id, item_id)
)ENGINE = INNODB;


CREATE TABLE Alert (
    alert_id 		INT AUTO_INCREMENT,
    movie_title 	VARCHAR(50) NOT NULL,
    genre	 		VARCHAR(20),
    movie_format	VARCHAR(20) NOT NULL,
	owner			VARCHAR(20) NOT NULL,
    PRIMARY KEY (alert_id),
    FOREIGN KEY (owner) REFERENCES Member(username) ON DELETE CASCADE
)ENGINE = INNODB;


CREATE TABLE Question (
    question_id 		INT AUTO_INCREMENT,
    question_string		TEXT,
    datetime_asked 		DATETIME NOT NULL,
	asker 				VARCHAR(20) NOT NULL,
	PRIMARY KEY (question_id),
    FOREIGN KEY (asker) REFERENCES Member(username)
)ENGINE = INNODB;


CREATE TABLE Answer (
    answer_id 			INT AUTO_INCREMENT,
    answer_string		TEXT,
    datetime_answered	DATETIME NOT NULL,
	parent_question		INT NOT NULL,
	answerer			VARCHAR(20) NOT NULL,
    PRIMARY KEY (answer_id),
    FOREIGN KEY (parent_question) REFERENCES Question(question_id),
    FOREIGN KEY (answerer) REFERENCES Member(username)
)ENGINE = INNODB;


