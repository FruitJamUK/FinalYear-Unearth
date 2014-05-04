import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import controllers.PlacesController;


public class AlgebraicTest {

	@RunWith(org.jcheck.runners.JCheckRunner.class)
    class SimpleURLTest {
        @Test public void queryString(String s) {
            //imply(somearray.length > 1);
        	String testString =
        	"https://maps.googleapis.com/maps/api/place/textsearch/json?"+
            "key=XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"+
            "&query=vegetarian+restaurant+"+
            "&sensor=false";
        	
            int expected= testString.length()+s.length();
            int result= PlacesController.generateURL(s).length();
            
            assertEquals(expected, result);
        }
        
        @Test public void latLonValues(String lt,String ln) {
            //imply(somearray.length > 1);
        	String testString =
        	"https://maps.googleapis.com/maps/api/place/textsearch/json?"+
            "key=XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"+
            "&query=vegetarian+restaurant"+
            "&location=,"+
    		"&radius=8000"+
            "&sensor=true";
        	
            int expected= testString.length()+lt.length()+ln.length();
            int result= PlacesController.generateURL(lt,ln).length();
            
            assertEquals(expected, result);
        }
    }

}
