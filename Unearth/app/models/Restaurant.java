package models;

import com.fasterxml.jackson.databind.JsonNode;

public class Restaurant {
	String formAdd;
	String lat;
	String lon;
	String name;
	String rating;
	
	
	//public static void setLatLon(String LatLon){}
	public void readJson(JsonNode json)
	{
		JsonNode LatLon;
		
		formAdd=json.get("formatted_address").asText();
		LatLon=json.get("geometry").get("location");
		lat=LatLon.get("lat").asText();
		lon=LatLon.get("lng").asText();
		name=json.get("name").asText();
		rating=json.get("rating").asText();
	}
	public static String buildHtml(){return null;}
}
