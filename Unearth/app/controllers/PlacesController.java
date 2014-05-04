package controllers;


import java.io.IOException;
import java.net.UnknownHostException;

import models.Restaurant;
import models.TestJson;
import play.mvc.Controller;
import play.mvc.Result;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import play.libs.WS;
import play.libs.F.Promise;


/**
 * @author David Sharp <dps15@uni.brighton.ac.uk>
 * The PlacesController is designed to serve up pages using Google Maps JSON
 * and return the result using Views as Play Results
 */
public class PlacesController extends Controller {
	/**
	 * Google Server Key
	 */
	private static final String key="AIzaSyCx8qY5PsAax6gpZiXADiHBo68As6YDaPQ";
	
/**
 * 
 * @param lat Latitude of the user (provided by geolocation)
 * @param lon Longitude of the user (provided by geolocation)
 * @return  The result of the getRestaurants method using the created JSON
 */
  public static Result getPlacesLatLon(String lat, String lon) {
    
	  	String url=generateURL(lat,lon);
    
		JsonNode json = getJson(url);
      
        return getRestaurants(json);
    }
/**
 * 
 * @param search Search query string given by the user
 * @return  The result of the getRestaurants method using the created JSON
 */
  public static Result getPlacesString(String search) {
	    
		String url=generateURL(search);
	    
		JsonNode json = getJson(url);
	    
	    return getRestaurants(json);
	    }
  
  /**
   * 
   * @param json A JsonNode JSON object
   * @return  A textual representation of the JSON to be printed on a web page
   */
public static Result jsonToString(JsonNode json){
	return ok(json.toString());
}
  /**
   * Generates a Google Places JSON URL based on a search query
   * @param search Search query string given by the user
   * @return The Google URL generated from the given input
   */
  public static String generateURL(String search) {
		if(search==null)return null;
			String s=search.replace(" ", "+");
		    String url= ("https://maps.googleapis.com/maps/api/place/textsearch/json?"+
		                "key="+key+
		                "&query=vegetarian+restaurant"+
		                "+"+s+"&sensor=false");
		  return url;
	  }
  /**
   * Generates a Google Places JSON URL based on a Latitude and Longitude
   * @param lat Latitude of the user (provided by geolocation)
   * @param lon Longitude of the user (provided by geolocation)
   * @return The Google URL generated from the given input
   */
  public static String generateURL(String lat, String lon) {
	if(lat==null||lon==null)return null;
	    String url= ("https://maps.googleapis.com/maps/api/place/textsearch/json?"+
	                "key="+key+
	                "&query=vegetarian+restaurant"+
	                "&location="+lat+","+lon+
	                "&radius=8000"+
	                "&sensor=true"); //set to true if accessed through geolocation
	  return url;
  }
  /**
   * Debug class
   * @param lat Latitude of the user (provided by geolocation)
   * @param lon Longitude of the user (provided by geolocation)
   * @return The generated URL as a String
   */
  public static Result getURL(String lat, String lon) {
       return ok(generateURL(lat,lon));
  }
  /**
   * 
   * @param json A JSON node representing Restaurant information from Google
   * @return If JSON parsing has failed, a test result is returned
   * @return A web view displaying search.scala.html, with a Restaurant Array as it's param
   */
  public static Result getRestaurants(JsonNode json){
	  Restaurant[] r = parseJson(json);
	  //if not ok return test
	  if(r==null) return getRestaurantsTest();
	  else return ok(views.html.search.render(r));
  }
  /**
   * 
   * @return A web view displaying search.scala.html, with the Test JSON as a param
   */
public static Result getRestaurantsTest(){
	JsonNode json=null;
	try {json = TestJson.pullTestJson();}
	catch (JsonProcessingException e) {e.printStackTrace();}
	catch (IOException e) {e.printStackTrace();}
	Restaurant[] r=parseJson(json);
	return ok(views.html.search.render(r));
  }
  
/**
 * 
 * @param url A Google URL from which JSON can be returned
 * @return A JsonNode object representing the JSON from Google
 */
  public static JsonNode getJson(String url){
	  WS.WSRequestHolder RequestHolder = new WS.WSRequestHolder(url);  
	  Promise<WS.Response> res = RequestHolder.get();
	  return res.get().asJson(); //Promise.get() is deprecated, consider changing
  }
  
  /**
   * 
   * @param json A JsonNode containing Google Places restaurant information
   * @return 'null' which is used to catch unsuccessful requests
   * @return An array of the restaurants found within the JSON
   */
  public static Restaurant[] parseJson(JsonNode json){
	  //if error, return null, indicates error/no results
	  if(!json.get("status").asText().equals("OK")){
		  System.out.println(json.get("status").asText()+":"+json.get("error_message").asText());
		  return null;
	  }
	  //else split into array
	  json=json.get("results");
	  int size=json.size();
	  Restaurant[] arr= new Restaurant[size];
	  //for all restaurants, new restaurant
	  for(int i=0;i<size;i++)
	  {
		  arr[i] = new Restaurant();
		  arr[i].readJson(json.get(i));
	  }
	  return arr;
  }
  
}