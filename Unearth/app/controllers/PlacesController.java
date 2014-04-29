package controllers;

//import play.mvc.Action;
import java.io.IOException;
import java.net.UnknownHostException;

import models.Restaurant;
import models.TestJson;
import play.mvc.Controller;
import play.mvc.Result;




import com.fasterxml.jackson.core.JsonProcessingException;
//import play.api.libs.json.Json;
import com.fasterxml.jackson.databind.JsonNode;




//import play.api.Routes;
import play.libs.WS;
import play.libs.F.Promise;

//String Message;

public class PlacesController extends Controller {
	
	private static final String key="AIzaSyCx8qY5PsAax6gpZiXADiHBo68As6YDaPQ";

  public static Result getPlacesLatLon(String lat, String lon) {
    
	String url=generateURL(lat,lon);
    
    /*String url= ("https://maps.googleapis.com/maps/api/place/nearbysearch/json?"+
            	"key="+key+
            	"&location="+lat+","+lon+
            	"&radius=8000"+
            	"&sensor=true");*/
                
    //HttpResponse res = WS.url(url).get();
    //Document xmldoc = response.getXml();
    
WS.WSRequestHolder RequestHolder = new WS.WSRequestHolder(url); //necessary? one is created using WS.url()
    
    Promise<WS.Response> res = RequestHolder.get();
    //int status = res.getStatus();
    //String type = res.getContentType();

    JsonNode json = res.get().asJson(); //promise.get() is deprecated
    //JsonElement json = res.getJson();
    //System.out.print(json.get("results"));
    
    /*URL theUrl = new URL("http://maps.googleapis.com/maps/api/geocode/json?address=Paris&sensor=false");
    HttpURLConnection urlConn = (HttpURLConnection) theUrl.openConnection();*/
      
        return ok(json.toString());
    }
  
  public static Result getPlacesString(String search) {
	    
		String url=generateURL(search);
	    
		JsonNode json = getJson(url);
	    
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
	if(lat==null&&lon==null)return null;
	    String url= ("https://maps.googleapis.com/maps/api/place/textsearch/json?"+
	                "key="+key+
	                "&query=vegetarian+restaurant");
	    if(lon==null)url=url+("%20"+lat+"%22&sensor=false"); //passed address
	    else /*if(lat==null)*/url=url+	("&location="+lat+","+lon+
	                					"&radius=8000"+
	                					"&sensor=true"); //set to true if accessed through geolocation
	  return url;
  }
  
  public static Result getURL(String lat, String lon) {
       return ok(generateURL(lat,lon));
  }
  
  public static Result getRestaurants(){
	  
	  return null;
  }
  
public static Result getRestaurantsTest(){
	JsonNode json=null;
	try {json = TestJson.pullTestJson();}
	catch (JsonProcessingException e) {e.printStackTrace();}
	catch (IOException e) {e.printStackTrace();}
	Restaurant[] r=parseJson(json);
	//Restaurant rest=new Restaurant();
	//rest.readJson(json.get("results").get(0));
	  return ok(r[0]!=null?(r.length+r[0].testString()+r[r.length-1].testString()):json.toString());
	//return ok(json.get("results").get(0)/*.get("rating")*/.toString());
	//return ok(Long.toString(json.get("results").get(0).get("geometry").get("location").get("lat").longValue()));
	//return ok(json.get("results").get(0).get("geometry").get("location").get("lat").asText());
	//return ok(rest.testString());
	//return ok(String.valueOf(json.get("results").size()));
	//return ok(Boolean.toString(json.get("status").textValue().equals("OK")));
	//return ok(String.valueOf(r.length));
  }
  
  public static JsonNode getJson(String url){
	  WS.WSRequestHolder RequestHolder = new WS.WSRequestHolder(url);  
	  Promise<WS.Response> res = RequestHolder.get();
	  return res.get().asJson(); //promise.get() is deprecated, consider changing
  }
  
  public static Restaurant[] parseJson(JsonNode json){
	  //if error, return null, indicates error/no results
	  //if(!json.get("status").asText().equals("OK")){
	//	  System.out.println(json.get("status").asText()+":"+json.get("error_message").asText());
	//	  return null;
	//  }
	  //else split into array
	  json=json.get("results");
	  int size=json.size();
	  Restaurant[] arr= new Restaurant[size];
	  //for all restaurants, new restaurant
	  for(int i=0;i<size;i++)
	  {
		  arr[i] = new Restaurant();
		  arr[i].readJson(json.get(i)); //PROBLEM HERE
		  
		  //Restaurant r = new Restaurant();
		  //r.readJson(json.get(i));
		  //arr[i]=r;
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
  
    
    /*public static Result getGeo(){
    	//must read in js result
        return ok();
    }*/
  
  /*public static Result testRestaurant(){
	  Restaurant r;
	  String s;
	  JsonNode j=s.asJson;
	  r.readJson(j);
	  
	  return ok();
  }*/
    
}

/*public static Promise<Result> index() {
    final Promise<Result> resultPromise = WS.url(feedUrl).get().map(
            new Function<WS.Response, Result>() {
                public Result apply(WS.Response response) {
                    return ok("Feed title:" + response.asJson().findPath("title"));
                }
            }
    );
    return resultPromise;
}*/