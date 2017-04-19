package MoviePick.service;


import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import MoviePick.DAO.MoviePickDAO;


/*--------------------------------PUT/UPDATE-----------------------------------------------
 * 1. Updating a movie rating
 *              URI               						Cons/Prod        Response
 *   /cs8350_2/movie/updateMovie/{title}/{rating}				  JSON         	  200 OK
 *   
 * 2. Updating the show timings
 * 				URI               				Cons/Prod         Response
 * /cs8350_2/movie/updateMovie/{title}/updateTheatre/{theatre}/{showTime}	  JSON         	   200 OK
 * 
 * 3. Updating the theatre
 * 				URI               				Cons/Prod       Response
 * /cs8350_2/movie/updateMovie/{title}/updateShowTime/{theatre}/{showTime}	  JSON         	 200 OK 
 * 
 * -----------------------------------------------------------------------------------------



/**
 *	Update movie service class
 * 	The main path is /cs8350_2/movie/updateMovie
 */
@Path( "/cs8350_2/movie/updateMovie" )
public class UpdateMovieService
{    
   /**
     * Update a movie rating using a query parameter
     * @param title path parameter identifying the movie
     * @param rating query parameter updating the rating
     * @response {@link Response} - OK
     */
    @PUT
    @Path( "/{title}/{rating}" )
    public Response updateMovieJSON( @PathParam("title") String title, @PathParam("rating") float rating) 
    {

    	int i = new MoviePickDAO().updateMovieRating(title, rating);
    	
    	if(i > 0){
    		return Response.ok().build();
    	}else{
    		return Response.notModified().build();
    	}
    }

    
   /**
     * Update a movie with a theatre using a query parameter
     * @param title path parameter identifying the movie
     * @param theatre query parameter identifying the theatre
     * @param showTimings query parameter adding a show time
     * @response {@link Response} - OK
     */
    @PUT
    @Path( "/{title}/updateTheatre/{theatre}/{showTime}" )
    public Response updateTheatreJSON( @PathParam("title") String title, @PathParam("theatre") String theatreName, @PathParam("showTime") String showTimings) 
    {
    	int i = new MoviePickDAO().updateTheatre(title, theatreName, showTimings);
		
    	if(i > 0){
    		return Response.ok().build();
    	}else{
    		return Response.notModified().build();
    	}
        
    }

    /**
     * Update a movie show time for a theatre using an JSON Representation
     * @param title path parameter identifying the movie
     * @param is input stream for reading the paylod
     * @return a response encoding
     */
    @PUT
    @Path( "/{title}/updateShowTime/{theatre}/{showTime}" )
    public Response updateShowTimeJSON( @PathParam("title") String title,  @PathParam("theatre") String theatreName, @PathParam("showTime") String showTimings) 
    {
    	int i = new MoviePickDAO().updateMovieShowTime(title, theatreName, showTimings);
		
    	if(i > 0){
    		return Response.ok().build();
    	}else{
    		return Response.notModified().build();
    	}
    }
}
