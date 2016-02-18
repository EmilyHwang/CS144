package edu.ucla.cs.cs144;

import java.util.ArrayList;

public class Item {
	private String name;
	private String id;
	private String currently;
	private String first_bid;
	private String buy_price;
	private String num_bids;
	private ArrayList<String> location; // Location, Latitude, Longitude
	private String country;
	private String started;
	private String ends;
	private ArrayList<String> seller; // SellerID, Rating
	private String description;
	private ArrayList<ArrayList<String>> bids; // UserID, Rating, Location, Country, Time, Amount

	public Item(String name, String id, String currently, String first_bid, String buy_price, String num_bids,
							ArrayList<String> location, String country, String started, String ends, 
							ArrayList<String> seller, String description, ArrayList<ArrayList<String>> bids){
		this.name = name;
		this.id = id;
		this.currently = currently;
		this.first_bid = first_bid;
		this.buy_price = buy_price;
		this.num_bids = num_bids;
		this.location = location;
		this.country = country;
		this.started = started;
		this.ends = ends;
		this.seller = seller;
		this.description = description;
		this.bids = bids;
	}

	public String getName (){
		return name;
	}
	public String getId (){
		return id;
	}
	public String getCurrently (){
		return currently;
	}
	public String getFirst_bid (){
		return first_bid;
	}
	public String getBuy_price (){
		return buy_price;
	}
	public String getNum_bids (){
		return num_bids;
	}
	public ArrayList<String> getLocation (){
		return location;
	}
	public String getCountry (){
		return country;
	}
	public String getStarted (){
		return started;
	}
	public String getEnds (){
		return ends;
	}
	public ArrayList<String> getSeller (){
		return seller;
	}
	public String getDescription (){
		return description;
	}
	public ArrayList<ArrayList<String>> getBids (){
		return bids;
	}

}