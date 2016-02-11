LOAD DATA LOCAL INFILE "item.csv.tmp" IGNORE INTO TABLE Item FIELDS TERMINATED BY "|*|" LINES TERMINATED BY "\n";
LOAD DATA LOCAL INFILE "seller.csv.tmp" IGNORE INTO TABLE Seller FIELDS TERMINATED BY "|*|" LINES TERMINATED BY "\n";
LOAD DATA LOCAL INFILE "bidder.csv.tmp" IGNORE INTO TABLE Bidder FIELDS TERMINATED BY "|*|" LINES TERMINATED BY "\n";
LOAD DATA LOCAL INFILE "bid.csv.tmp" IGNORE INTO TABLE Bid FIELDS TERMINATED BY "|*|" LINES TERMINATED BY "\n";
LOAD DATA LOCAL INFILE "category.csv.tmp" IGNORE INTO TABLE ItemCategory FIELDS TERMINATED BY "|*|" LINES TERMINATED BY "\n";