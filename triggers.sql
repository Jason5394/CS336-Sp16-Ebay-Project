USE projDB;
DROP TRIGGER IF EXISTS hiddenmin;
DROP TRIGGER IF EXISTS topbid;
DROP TRIGGER IF EXISTS autobid;


DELIMITER |
CREATE TRIGGER hiddenmin AFTER INSERT ON Bid
FOR EACH ROW 
BEGIN
	DECLARE hidden_min FLOAT(8,2) DEFAULT 0;
	SET hidden_min = 
    (SELECT hidden_minimum_price FROM Auction
		WHERE auction_id = NEW.auction_id);
	IF (NEW.bid_amount >= hidden_min OR hidden_min IS NULL) THEN
		UPDATE Auction SET Auction.winner = NEW.bidder
        WHERE Auction.auction_id = NEW.auction_id;
	ELSEIF (NEW.bid_amount < hidden_min) THEN
		UPDATE Auction SET Auction.winner = NULL
        WHERE Auction.auction_id = NEW.auction_id;
	END IF;
END;
|
DELIMITER ;

DELIMITER | 
CREATE TRIGGER topbid AFTER INSERT ON Bid 
FOR EACH ROW
BEGIN
	DECLARE top_bid FLOAT(8,2) DEFAULT 0;
    SET top_bid = 
    (SELECT top_bid FROM Auction WHERE auction_id=NEW.auction_id);
    IF (NEW.bid_amount > top_bid OR top_bid IS NULL) THEN
		UPDATE Auction SET Auction.top_bid = NEW.bid_amount,
        Auction.bidder=NEW.bidder
        WHERE Auction.auction_id = NEW.auction_id;
    END IF;
END;
|
DELIMITER ;


DELIMITER |
CREATE TRIGGER autobid AFTER INSERT ON UpperLimit
FOR EACH ROW
BEGIN
	DECLARE min_bid FLOAT(8,2) DEFAULT 0;
    DECLARE top_bid FLOAT(8,2) DEFAULT 0;
    DECLARE min_incr FLOAT(8,2) DEFAULT 0;
    DECLARE max_others FLOAT(8,2) DEFAULT 0;
    DECLARE max_final FLOAT(8,2) DEFAULT 0;
    SELECT a.top_bid, a.minimum_increment_price INTO top_bid, min_incr FROM Auction a WHERE a.auction_id=NEW.auction_id;
    SET min_bid = top_bid + min_incr;
    SET max_others = (
		SELECT MAX(u.upper_limit) FROM 
			(SELECT * FROM UpperLimit u2 WHERE u2.bidder <> NEW.bidder AND u2.auction_id = NEW.auction_id) AS u 
    );
    IF (max_others > min_bid) THEN
		SET max_final = max_others;
	ELSEIF (min_bid >= max_others) THEN
		SET max_final = min_bid;
	END IF;
    IF (NEW.upper_limit >= max_final) THEN
		INSERT INTO Bid(bid_id, bid_amount, creation_datetime, bidder, auction_id) 
        VALUES (NULL, max_final, NOW(), NEW.bidder, NEW.auction_id);
	END IF;
END;
|
DELIMITER ;
