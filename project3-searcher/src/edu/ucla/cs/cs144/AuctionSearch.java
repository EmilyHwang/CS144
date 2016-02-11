package edu.ucla.cs.cs144;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.text.SimpleDateFormat;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.lucene.document.Document;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import edu.ucla.cs.cs144.DbManager;
import edu.ucla.cs.cs144.SearchRegion;
import edu.ucla.cs.cs144.SearchResult;

public class AuctionSearch implements IAuctionSearch {

	/* 
         * You will probably have to use JDBC to access MySQL data
         * Lucene IndexSearcher class to lookup Lucene index.
         * Read the corresponding tutorial to learn about how to use these.
         *
	 * You may create helper functions or classes to simplify writing these
	 * methods. Make sure that your helper functions are not public,
         * so that they are not exposed to outside of this class.
         *
         * Any new classes that you create should be part of
         * edu.ucla.cs.cs144 package and their source files should be
         * placed at src/edu/ucla/cs/cs144.
         *
         */
	
	
	public SearchResult[] basicSearch(String query, int numResultsToSkip, 
			int numResultsToReturn) {
			
		// TODO: Your code here!
		SearchResult[] searchResults;
		
		try{
			IndexSearcher searcher = new IndexSearcher(DirectoryReader.open(FSDirectory.open(new File("/var/lib/lucene/index-directory/"))));
			QueryParser parser = new QueryParser("fullSearchableText", new StandardAnalyzer());
			
			Query parsedQuery = parser.parse(query);
			TopDocs topDocs = searcher.search(parsedQuery, numResultsToSkip + numResultsToReturn);
			
			ScoreDoc[] hits = topDocs.scoreDocs;
			
			ArrayList<SearchResult> resultList = new ArrayList<SearchResult>();
			
			for (int i = numResultsToSkip ; i < hits.length; i++) {
				Document doc = searcher.doc(hits[i].doc);
				SearchResult result = new SearchResult(doc.get("itemID"), doc.get("name"));
				resultList.add(result);
			}
			
			searchResults = new SearchResult[resultList.size()];
			return resultList.toArray(searchResults);

		} catch (IOException ex){
			System.err.println("IOException: " + ex.getMessage());
		} catch (Exception ex) {
			System.err.println("IOException: " + ex.getMessage());
		}
		return null;
	}

	public SearchResult[] spatialSearch(String query, SearchRegion region,
			int numResultsToSkip, int numResultsToReturn) {
		List<SearchResult> allResults = new ArrayList();
		List<SearchResult> finalResultsList = new ArrayList();
		SearchResult[] finalResults = null;

		// TODO: Your code here!
		try {
			Connection conn = DbManager.getConnection(true);
			Statement stm = conn.createStatement();
			// TODO: Query using basic Search
			
			SearchResult[] basicResults = basicSearch(query, 0, Integer.MAX_VALUE);
			HashMap<String, String> basicHash = new HashMap<String, String>();

			for (int i = 0; i < basicResults.length; i++){
				basicHash.put(basicResults[i].getItemId(), basicResults[i].getName());
			}
			
			// Query using spatial index
			String sqlQuery = "select Item.ItemID, Name From Item INNER JOIN Location On Item.ItemId = Location.itemID WHERE MBRContains(GeomFromText('MULTIPOINT(" 
													+ String.valueOf(region.getLx()) + " " + String.valueOf(region.getLy()) + "," + String.valueOf(region.getRx()) + " " + String.valueOf(region.getRy()) 
													+ ")'), Location.LatLong);";

			ResultSet rs = stm.executeQuery(sqlQuery);

			// Now rs contains the reuslts of the spatial query, we want to put the results inside SearchResult array	
			while (rs.next()){
				String id = rs.getString("ItemID");
				String name = rs.getString("Name");
				SearchResult temp = new SearchResult(id, name);

				// System.out.println(temp.getItemId() + ": " + temp.getName());
				// TODO: Make sure that before inserting to array, we need to see if id exists in the IDSet
				if(basicHash.containsKey(id)){
					allResults.add(temp);
				}
			}

			for(int i = numResultsToSkip; i < allResults.size() && i < numResultsToReturn + numResultsToSkip; i++){
				finalResultsList.add(allResults.get(i));
			}
			
			finalResults = new SearchResult[finalResultsList.size()];
			return finalResultsList.toArray(finalResults);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return finalResults;
	}

		public String getXMLDataForItemId(String itemId) {
		// TODO: Your code here!
		String result = "";

		try{
			Connection conn = DbManager.getConnection(true);
			Statement stm = conn.createStatement();
			String sqlQuery = "SELECT * FROM Item WHERE ItemID=" + itemId;
			ResultSet itemRs = stm.executeQuery(sqlQuery);

			if (itemRs.next()){
				// First we get all the information from the item query
				result += "<Item ItemID=\"" + itemId + "\">\n" 
								+ "\t<Name>" + xml_escape(itemRs.getString("Name")) + "</Name>\n";
				
				String curToNum = "\t<Currently>$" + String.valueOf(itemRs.getString("Currently")) + "</Currently>\n";
				if (!itemRs.getString("Buy_Price").equals("0.00")) curToNum += "\t<Buy_Price>$" + String.valueOf(itemRs.getString("Buy_Price")) + "</Buy_Price>\n";
				curToNum += "\t<First_Bid>$" + String.valueOf(itemRs.getString("First_bid")) + "</First_Bid>\n";
				curToNum += "\t<Number_of_Bids>" + String.valueOf(itemRs.getString("Number_of_Bids")) + "</Number_of_Bids>\n";

				String locationToEnds = "";
				if (!itemRs.getString("Latitude").equals("")) {
					locationToEnds = "\t<Location Latitude=\"" + itemRs.getString("Latitude") 
																+ "\" Longitude=\"" + itemRs.getString("Longitude") +"\">"
																+ xml_escape(itemRs.getString("Location")) + "</Location>\n";
				} else {
					locationToEnds = "\t<Location>" + xml_escape(itemRs.getString("Location")) + "</Location>\n";
				}
				locationToEnds += "\t<Country>" + xml_escape(itemRs.getString("Country")) + "<Country>\n";

				String time_t = itemRs.getString("Started");
				Date date = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss").parse(time_t);
        time_t = new SimpleDateFormat ("MMM-dd-yy HH:mm:ss").format(date);
				locationToEnds += "\t<Started>" + time_t + "</Started>\n";					

				time_t = itemRs.getString("Ends");
				date = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss").parse(time_t);
        time_t = new SimpleDateFormat ("MMM-dd-yy HH:mm:ss").format(date);								
				locationToEnds += "\t<Ends>" + time_t + "</Ends>\n";

				String description = "\t<Description>" + xml_escape(itemRs.getString("Description")) + "</Description>\n</Item>";

				String sellerInfo = formSeller(itemRs.getString("SellerID"));

				result += formCategory(itemId) + curToNum + formBids(itemId) + locationToEnds + sellerInfo + description;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}

	public String formSeller(String sellerId){
		String result = "";
		try{
			Connection conn = DbManager.getConnection(true);
			Statement stm = conn.createStatement();

			String sqlQuery = "SELECT Rating From Seller WHERE UserID=\"" + sellerId + "\"";
			ResultSet sellerRs = stm.executeQuery(sqlQuery);

			if (sellerRs.next()){
				result += "\t<Seller Rating=\"" + sellerRs.getString("Rating") + " UserID=\"" + sellerId + "\" />\n";
			}

		} catch (Exception e){
			e.printStackTrace();
		}

		return result;
	}
	
	public String formCategory(String itemId){
		String result = "";
		try{
			Connection conn = DbManager.getConnection(true);
			Statement stm = conn.createStatement();

			String sqlQuery = "SELECT Category From ItemCategory WHERE ItemID=" + itemId;
			ResultSet categoryRs = stm.executeQuery(sqlQuery);
			while (categoryRs.next()){
				result += "\t<Category>" + xml_escape(categoryRs.getString("Category")) + "</Category>\n";
			}
		} catch (Exception e){
			e.printStackTrace();
		}

		return result;
	}

	public String formBids(String itemId){
		String result = "";
		try{
			Connection conn = DbManager.getConnection(true);
			Statement stm = conn.createStatement();
			List<List<String>> bid = new ArrayList();

			ResultSet bidRs = stm.executeQuery("SELECT BidderID, Time, Amount FROM Bid WHERE ItemID = " + itemId);
			while (bidRs.next()){
				List<String> temp = new ArrayList();
				temp.add(bidRs.getString("BidderID"));

				String time_t = bidRs.getString("Time");
				Date date = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss").parse(time_t);
        time_t = new SimpleDateFormat ("MMM-dd-yy HH:mm:ss").format(date);
				temp.add(time_t);

				temp.add(String.valueOf(bidRs.getString("Amount")));
				bid.add(temp);
			}

			if (bid.size() == 0) return "\t<Bids />\n";

			for (int i = 0 ; i < bid.size(); i++){
				String bidString = "";
				ResultSet bidderRs = stm.executeQuery("SELECT Rating, Location, Country FROM Bidder WHERE UserID = \"" + bid.get(i).get(0) + "\"");
				if (bidderRs.next()){
					bidString = "\t<Bids>\n\t\t<Bid>\n\t\t\t<Bidder Rating=\"" + bidderRs.getString("Rating")
										+ "\" UserID=\"" + bid.get(i).get(0) + "\">\n"
										+ "\t\t\t\t<Location>" + xml_escape(bidderRs.getString("Location")) + "</Location>\n" 
										+ "\t\t\t\t<Country>" + xml_escape(bidderRs.getString("Country")) + "</Country>\n"
										+ "\t\t\t</Bidder>\n"
										+ "\t\t\t<Time>" + bid.get(i).get(1) + "</Time>\n"
										+ "\t\t\t<Amount>$" + bid.get(i).get(2) + "</Amount>\n"
										+ "\t\t</Bid>\n";
				}
				result += bidString;
			}
			result += "\t</Bids>\n";
		} catch (Exception e){
			e.printStackTrace();
		}

		return result;
	}

	public String xml_escape(String pcdata){
		pcdata = pcdata.replace("\"", "&quot;");
		pcdata = pcdata.replace("'", "&apos;");
		pcdata = pcdata.replace("&", "&amp;");
		pcdata = pcdata.replace("<", "&lt;");
		pcdata = pcdata.replace(">", "&gt;");

		return pcdata;
	}

	public String echo(String message) {
		return message;
	}

}
