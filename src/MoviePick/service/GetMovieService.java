package MoviePick.service;

import java.util.ArrayList;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.google.gson.Gson;
import MoviePick.resource.Movie;
import MoviePick.resource.Theatre;
import MoviePick.DAO.MoviePickDAO;



//---------------------------------------GET--------------------------------------------------------

//URI               					Cons/Prod         Action       Return
///cs8350_2/movie/getMovie		  			XML               Read         XML representation of all the movies - title, theatre name and its address, show timings, genre and rating
///cs8350_2/movie/getMovie					JSON              Read         JSON representation of movie - title, theatre name and its address, show timings, genre and rating

//URI               					Cons/Prod         Action       Return
///cs8350_2/movie/getMovie/title/{title}  	XML               Read         XML representation of movie - title, theatre name and its address, show timings, genre and rating
///cs8350_2/movie/getMovie/title/{title}		JSON              Read         JSON representation of movie - title, theatre name and its address, show timings, genre and rating

//URI               					Cons/Prod         Action       Return
///cs8350_2/movie/getMovie/genre/{genre}   	XML               Read         XML representation of movies by genre - title and rating
///cs8350_2/movie/getMovie/genre/{genre}		JSON              Read         JSON representation of movies by genre -title and rating

//URI               					Cons/Prod         Action       Return
///cs8350_2/movie/getMovie/rating/{rating}   	XML               Read         XML representation of movies by rating - title and genre
///cs8350_2/movie/getMovie/rating/{rating}	JSON              Read         JSON representation of movies by rating - title and genre

//URI               					Cons/Prod         Action       Return
///cs8350_2/movie/getMovie/theatre/{theatre}  XML               Read         XML representation of movies by theatre name - title, theatre name and its address, show timings, genre and rating
///cs8350_2/movie/getMovie/theatre/{theatre}	JSON              Read         JSON representation of movies by theatre name - title, theatre name and its address, show timings, genre and rating 

//---------------------------------------------------------------------------------------------------


/**
 *	GET movie service class
 * 	The main path is /cs8350_2/movie/
 */
@Path( "/cs8350_2/movie/getMovie" )
public class GetMovieService {

	  /**
     * Retrieve the movie entries and return it as a streaming output, using a JSON representation
     * @return a string; it will be converted to JSON using a JSON provider (Jackson)
     */
    @GET
    @Path("/")
    @Produces( MediaType.APPLICATION_JSON )
    public String getMovieJSON() 
    {
    	ArrayList<Movie> movieList = new MoviePickDAO().getAllMovieDetails();
    	String json= new Gson().toJson(movieList);
        return json;
    }
    
    /**
     * Retrieve the movie entries by title given by user and return it as a streaming output, using a JSON representation
     * @param title of the movie for which details will be returned
     * @return a string; it will be converted to JSON using a JSON provider (Jackson)
     */
    @GET
    @Path("/title/{title}")
    @Produces( MediaType.APPLICATION_JSON )
    public String getMovieByTitleJSON( @PathParam("title") String title) 
    {
    	Movie movie = new MoviePickDAO().getMovieDetailsByTitle(title);
    	String json= new Gson().toJson(movie);
        return json;
    }
    
    /**
     * Retrieve the movie entries by title given by user and return it as a streaming output, using a JSON representation
     * @param genre of the movie for which details will be returned
     * @return a string; it will be converted to JSON using a JSON provider (Jackson)
     */
    @GET
    @Path("/genre/{genre}")
    @Produces( MediaType.APPLICATION_JSON )
    public String getMovieByGenreJSON( @PathParam("genre") String genre) 
    {
    	ArrayList<Movie> movieList = new MoviePickDAO().getMovieDetailsByGenre(genre);
    	String json= new Gson().toJson(movieList);
        return json;
    }
    
    /**
     * Retrieve the movie entries by title given by user and return it as a streaming output, using a JSON representation
     * @param rating of the movie for which details will be returned
     * @return a string; it will be converted to JSON using a JSON provider (Jackson)
     */
    @GET
    @Path("/rating/{rating}")
    @Produces( MediaType.APPLICATION_JSON )
    public String getMovieByRatingJSON( @PathParam("rating") float rating) 
    {
    	ArrayList<Movie> movieList = new MoviePickDAO().getMovieDetailsByRating(rating);
    	String json= new Gson().toJson(movieList);
        return json;
    }
    
    /**
     * Retrieve the movie entries by title given by user and return it as a streaming output, using a JSON representation
     * @param theatre for which movie details will be returned
     * @return a string; it will be converted to JSON using a JSON provider (Jackson)
     */
    @GET
    @Path("/theatre/{theatre}")
    @Produces( MediaType.APPLICATION_JSON )
    public String getMovieByTheatreJSON( @PathParam("theatre") String theatre) 
    {
    	Theatre th = new MoviePickDAO().getMovieDetailsByTheatre(theatre);
    	String json= new Gson().toJson(th);
        return json;
    }
    
 }
