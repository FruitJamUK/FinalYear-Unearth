package controllers;

import play.mvc.Controller;
import play.mvc.Result;

public class MainController extends Controller {
    
    public static Result index() {
        return ok(views.html.index.render("Welcome to Unearth! <br/>"
        		+ "To search for restaurants either enter a place name above,"
        		+ " or press \'Local Search\' for a quick local search."));
    }
    
}
