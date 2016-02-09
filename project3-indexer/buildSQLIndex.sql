CREATE TABLE Location(
	ItemID INT NOT NULL,
	LatLong POINT NOT NULL
	) ENGINE=MYISAM;

INSERT INTO Location (ItemID, LatLong)
	SELECT ItemID, POINT(Latitude, Longitude)
	From Item
	WHERE Longitude != 0;

CREATE SPATIAL INDEX sp_index ON Location(LatLong);