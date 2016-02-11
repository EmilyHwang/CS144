1)List your relations. Please specify all keys that hold on each relation. You need not specify attribute types at this stage.

Item(ItemID*, Name, Currently, First_bid, Buy_Price, Number_of_Bids, Location, Latitude, Longitude, Country, Started, Ends, SellerID, Description)

Bidder(UserID*, Rating, Location, Country)

Seller(UserID*, Rating)

Bid(BidderID*, ItemID, Time*, Amount)

ItemCategory(ItemID*, Category*)

*denotes keys

2)List all completely nontrivial functional dependencies that hold on each relation, excluding those that effectively specify keys.

Latitude, Longitude -> Location, Country

3)Are all of your relations in Boyce-Codd Normal Form (BCNF)? If not, either redesign them and start over, or explain why you feel it is advantageous to use non-BCNF relations.

No.
We believe it is unnecessary to have an extra table merely for latitude and longitude. Since we are not guaranteed to always have latitude and longitude, these columns would not make good keys for a location table. It is unstated if you can have two locations with the same name but different latitude and longitude, so location and coutry would not be good keys either. In addition, not having this extra table will be advantageous space-wise. Whether it is time efficient highly depends on the type of queries ran against this database.

4)Are all of your relations in Fourth Normal Form (4NF)? If not, either redesign them and start over, or explain why you feel it is advantageous to use non-4NF relations.

yes, all relations are in 4NF
