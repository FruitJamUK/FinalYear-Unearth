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


public class PlacesController extends Controller {
	
	private static final String key="AIzaSyCx8qY5PsAax6gpZiXADiHBo68As6YDaPQ";

  public static Result getPlacesLatLon(String lat, String lon) {
    
	  	String url=generateURL(lat,lon);
    
		JsonNode json = getJson(url);
      
        return getRestaurants(json);
    }
  
  public static Result getPlacesString(String search) {
	    
		String url=generateURL(search);
	    
		JsonNode json = getJson(url);
	    
	    return getRestaurants(json);
	    }
  
public static Result jsonToString(JsonNode json){
	return ok(json.toString());
}
  
  public static String generateURL(String search) {
		if(search==null)return null;
			String s=search.replace(" ", "+");
		    String url= ("https://maps.googleapis.com/maps/api/place/textsearch/json?"+
		                "key="+key+
		                "&query=vegetarian+restaurant"+
		                "+"+s+"&sensor=false");
		  return url;
	  }
  
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
  
  public static Result getURL(String lat, String lon) {
       return ok(generateURL(lat,lon));
  }
  
  public static Result getRestaurants(JsonNode json){
	  Restaurant[] r = parseJson(json);
	  //if not ok return test
	  if(r==null) return getRestaurantsTest();
	  else return ok(views.html.search.render(r));
  }
  
public static Result getRestaurantsTest(){
	JsonNode json=null;
	try {json = TestJson.pullTestJson();}
	catch (JsonProcessingException e) {e.printStackTrace();}
	catch (IOException e) {e.printStackTrace();}
	Restaurant[] r=parseJson(json);
	return ok(views.html.search.render(r));
  }
  
  public static JsonNode getJson(String url){
	  WS.WSRequestHolder RequestHolder = new WS.WSRequestHolder(url);  
	  Promise<WS.Response> res = RequestHolder.get();
	  return res.get().asJson(); //promise.get() is deprecated, consider changing
  }
  
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
  
  public static String getIP() throws UnknownHostException{
	  String url="http://instance-data/latest/meta-data/public-hostname";
	  try{
	  Promise<WS.Response> res = WS.url(url).get();
	  return res.get().toString();
	  }
	  catch (Exception e){return "Error: IP not found";}
  }
  public static Result returnIP() throws UnknownHostException{
	  return ok(getIP());
  }
    
}