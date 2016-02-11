package edu.ucla.cs.cs144;

import java.util.Calendar;
import java.util.Date;
import java.lang.Thread;

import edu.ucla.cs.cs144.AuctionSearch;
import edu.ucla.cs.cs144.SearchRegion;
import edu.ucla.cs.cs144.SearchResult;

public class AuctionSearchTest {
	public static void main(String[] args1)
	{
		AuctionSearch as = new AuctionSearch();

		System.out.println("================= Echo Test ================");	

		String message = "Test message";
		String reply = as.echo(message);
		System.out.println("Reply: " + reply);
		
		System.out.println("================= basicSearch Test ================");	
		
		String query = "kitchenware";
		SearchResult[] basicResults = as.basicSearch(query, 0, 2000);
		System.out.println("Basic Search Query: " + query);
		/*for(SearchResult result : basicResults) {
			System.out.println(result.getItemId() + ": " + result.getName());
		}*/
		System.out.println("Received " + basicResults.length + " results");
		
		query = "superman";
		basicResults = as.basicSearch(query, 0, 2000);
		System.out.println("Basic Search Query: " + query);
		/*for(SearchResult result : basicResults) {
			System.out.println(result.getItemId() + ": " + result.getName());
		}*/
		System.out.println("Received " + basicResults.length + " results");
		
		query = "star trek";
		System.out.println("Basic Search Query: " + query);
		basicResults = as.basicSearch(query, 0, 20000);
		/*for(SearchResult result : basicResults) {
			System.out.println(result.getItemId());
		}*/
		
		System.out.println("Received " + basicResults.length + " results");
		
		query = "superman";
		System.out.println("Basic Search Query: " + query + ", Query Size: " + 20);
		basicResults = as.basicSearch(query, 10, 20);
		/*for(SearchResult result : basicResults) {
			System.out.println(result.getItemId() + ": " + result.getName());
		}*/
		System.out.println("Received " + basicResults.length + " results");
		
		System.out.println("================= SpatialSearch Test ================");		
		SearchRegion region =
		    new SearchRegion(33.774, -118.63, 34.201, -117.38); 
		SearchResult[] spatialResults = as.spatialSearch("camera", region, 0, 2000);
		System.out.println("Spatial Search: camera");
		System.out.println("Received " + spatialResults.length + " results");

		spatialResults = as.spatialSearch("cameras", region, 0, 2000);
		System.out.println("Spatial Search: cameras");
		System.out.println("Received " + spatialResults.length + " results");
		
		System.out.println("================= getXMLDataForItemId Test ================");

		String itemId = "1497595357";
		String item = as.getXMLDataForItemId(itemId);
		
		System.out.println("XML data for ItemId: " + itemId);
		System.out.println(item);

		// Latitude and Longitute
		itemId = "1043402767";
		item = as.getXMLDataForItemId(itemId);
		
		System.out.println("XML data for ItemId: " + itemId);
		System.out.println(item);

		// Multiple bids
		itemId = "1043495702";
		item = as.getXMLDataForItemId(itemId);
		
		System.out.println("XML data for ItemId: " + itemId);
		System.out.println(item);
		
	}
}
