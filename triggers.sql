USE projDB;
DROP TRIGGER IF EXISTS hiddenmin;
DROP TRIGGER IF EXISTS topbid;

DELIMITER |
CREATE TRIGGER hiddenmin AFTER INSERT ON Bid
FOR EACH ROW 
BEGIN
	DECLARE hidden_min FLOAT(8,2);
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
	DECLARE top_bid FLOAT(8,2);
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