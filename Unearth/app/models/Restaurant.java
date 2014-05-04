package models;

import com.fasterxml.jackson.databind.JsonNode;
/**
 * 
 * @author David Sharp <dps15@uni.brighton.ac.uk>
 * Containing appropriate getters
 */
public class Restaurant {
	String formAdd;
	String lat;
	String lon;
	String name;
	/**
	 * Placeholder value
	 */
	String rating="4.5";
	
	/**
	 * The readJson class takes a subset of Google served JSON (representing a single restaurant)
	 * and attributes its contents to the local variables within
	 * @param json JsonNode representing an individual restaurant of the JSON returned by Google
	 */
	public void readJson(JsonNode json)
	{
		JsonNode LatLon;
		
		formAdd=json.get("formatted_address").asText();
		LatLon=json.get("geometry").get("location");
		lat=LatLon.get("lat").asText();
		lon=LatLon.get("lng").asText();
		name=json.get("name").asText();
		//rating=json.get("rating").asText();
	}
	public String getAddress(){return formAdd;}
	public String getLat(){return lat;}
	public String getLon(){return lon;}
	public String getName(){return name;}
	public String getRating(){return rating;}
	
	/**
	 * @return A test string to debug the contents of Restaurant
	 */
	public String testString(){return formAdd+lat+lon+name+rating;}
}
