import java.io.IOException;

import controllers.routes;
import controllers.PlacesController;
import models.Restaurant;
import models.TestJson;

import org.junit.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import play.mvc.*;
import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;


/**
 * Simple JUnit tests for the PlacesController and Restaurant model.
 */
public class PlacesControllerAndRestaurantTest {

	private static final String key="AIzaSyCx8qY5PsAax6gpZiXADiHBo68As6YDaPQ";
	
    @Test
    public void simpleCheck() {
        int a = 1 + 1;
        assertThat(a).isEqualTo(2);
    }
    /*
    @Test
    public void jsonToStringTest() {
        JsonNode json = TestJson.pullTestJson();
        assertThat(json.toString()).isEqualTo();
    }
    */
    
    @Test
    public void generateURLTest() {
        String test="test";
        
        String a = PlacesController.generateURL(test);
        assertThat(a).isEqualTo("https://maps.googleapis.com/maps/api/place/textsearch/json?"+
                "key="+key+
                "&query=vegetarian+restaurant"+
                "+"+test+"&sensor=false");
        String b = PlacesController.generateURL(test,test);
        assertThat(b).isEqualTo("https://maps.googleapis.com/maps/api/place/textsearch/json?"+
        		"key="+key+
        		"&query=vegetarian+restaurant"+
        		"&location="+test+","+test+
        		"&radius=8000"+
        		"&sensor=true");
    }
   /*
    @Test
    public void parseJsonTest(){
        JsonNode json=null;
		try {json = TestJson.pullTestJson();}
		catch (JsonProcessingException e) {e.printStackTrace();}
		catch (IOException e) {e.printStackTrace();}
        Restaurant[] r = PlacesController.parseJson(json);
        
        assertThat(r).isNotNull();
        assertThat(r[0].getName()).isEqualTo("Terre A Terre");
        assertThat(r[3].getAddress()).isEqualTo("39 Ship St, Brighton, United Kingdom");
        assertThat(r[0].getLat()).isEqualTo("50.820219");
        assertThat(r[0].getLon()).isEqualTo("-0.139085");
        assertThat(r[8].getRating()).isEqualTo("4.5");
        
        JsonNode fakeJson=null;
        String validJson = "{\r\n \"testA\" : [],\r\n \"testB\" : \"testC\"}";
        ObjectMapper mapper = new ObjectMapper();
		try {
			fakeJson = mapper.readTree(validJson);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Restaurant [] rNull = PlacesController.parseJson(fakeJson);
		assertThat(rNull).isNull();
    } */
 

    @Test
    public void searchTemplateContainsLatLong() {
        running(fakeApplication(), new Runnable() {
            public void run() {
            	JsonNode json = null;
            	try {json = TestJson.pullTestJson();}
        		catch (JsonProcessingException e) {e.printStackTrace();}
        		catch (IOException e) {e.printStackTrace();}
                Restaurant[] r = PlacesController.parseJson(json);
                
            	Content html = views.html.search.render(r);
                assertThat(contentType(html)).isEqualTo("text/html");
                String test = "#"+r[0].getLat()+"c"+r[0].getLon();
                test=test.replace(".", "d").replace("-", "m");
                assertThat(contentAsString(html)).contains(test);
            }
        });
    }
/*
    @Test
    public void getRestaurantsRunsTestJsonWhenNull() {
        running(fakeApplication(), new Runnable() {
            public void run() {
            	JsonNode fakeJson;
                String validJson = "{\r\n \"status\" : \"testStatus\",\r\n \"error_message\" : \"testError\"}";
                try{ObjectMapper mapper = new ObjectMapper();
        		fakeJson = mapper.readTree(validJson);}
				catch (JsonProcessingException e) {e.printStackTrace();}
				catch (IOException e) {e.printStackTrace();}
        		
                Result result = callAction(routes.ref.PlacesController.getRestaurants(fakeJson));
                assertThat(status(result)).isEqualTo(OK);
                assertThat(contentType(result)).isEqualTo("text/html");
                assertThat(charset(result)).isEqualTo("utf-8");
                assertThat(contentAsString(result)).contains("Terre A Terre");
            }
        });
    }*/

}
