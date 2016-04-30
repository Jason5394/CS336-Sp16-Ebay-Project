USE projDB;
DROP TRIGGER IF EXISTS hiddenmin;

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