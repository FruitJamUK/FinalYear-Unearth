package controllers;

//import play.mvc.Action;
import play.mvc.Controller;
import play.mvc.Result;
//import play.api.libs.json.Json;
import com.fasterxml.jackson.databind.JsonNode;
//import play.api.Routes;
import play.libs.WS;
import play.libs.F.Promise;

//String Message;

public class PlacesController extends Controller {
	
	private static final String key="AIzaSyBAMBeVhvKG8NoXsuzEnLvW2x1KcU8fxTw";

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
  
  public static JsonNode getJson(String url){
	  WS.WSRequestHolder RequestHolder = new WS.WSRequestHolder(url);  
	  Promise<WS.Response> res = RequestHolder.get();
	  return res.get().asJson(); //promise.get() is deprecated, consider changing
  }
  
  public static Restaurant[] parseJson(JsonNode json){
	  //if error, return null, indicates error/no results
	  if(!json.get("status").textValue().equals("OK")){
		  System.out.println(json.get("status").textValue()+":"+json.get("error_message").textValue());
		  return null;
	  }
	  //else split into arrays
	  json=json.get("results");
	  int size=json.size();
	  Restaurant[] arr= new Restaurant[size];
	  //for all restaurants, new restaurant
	  for(int i=0;i<size;i++)
	  {
		  arr[i].readJson(json.get(i));
	  }
	  return arr;
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