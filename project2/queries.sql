SELECT COUNT(*) FROM (SELECT UserID FROM Bidder UNION SELECT UserID FROM Seller) AS Users;

SELECT COUNT(*) FROM Item WHERE BINARY Location="New York";

SELECT COUNT(*) FROM (SELECT ItemID FROM ItemCategory GROUP BY ItemID HAVING Count(*)=4) as T;

SELECT ItemID FROM Item WHERE Ends > '2001-12-20 00:00:01' AND Number_of_Bids > 0 AND Currently = (SELECT MAX(Currently) FROM Item WHERE Ends > '2001-12-20 00:00:01' AND Number_of_Bids > 0);

SELECT Count(*) FROM Seller WHERE Rating > 1000;

SELECT COUNT(*) FROM Bidder B, Seller S WHERE B.UserID = S.UserID;

SELECT COUNT(DISTINCT Category) FROM ItemCategory INNER JOIN Bid ON ItemCategory.ItemID=Bid.ItemID WHERE Amount > 100;

