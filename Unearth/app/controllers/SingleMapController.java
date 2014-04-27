package controllers;

import play.mvc.Controller;
import play.mvc.Result;

public class SingleMapController extends Controller {
    
    public static Result getMap(String lat, String lon) {
        return ok(views.html.map.render(lat,lon));
    }
    
}