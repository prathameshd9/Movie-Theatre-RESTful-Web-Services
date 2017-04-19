package MoviePick.service;

import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import MoviePick.DAO.MoviePickDAO;


/*-------------------------------------DELETE----------------------------------------------------------------
 * 1. Deleting a theatre
 *              URI               						  Cons/Prod         Response
 *   /cs8350_2/theatre/deleteTheatre/{name}			       		   JSON              200 OK
 *  
 * ----------------------------------------------------------------------------------------------------------



/**
 * MoviePick service implementation class
 * The main path is /cs8350_2/theatre/deleteTheatre/
 */
@Path( "/cs8350_2/movie/deleteTheatre" )
public class DeleteTheatreService
{
   /**
     * Delete a movie using a path parameter
     * @param title path parameter identifying the movie
     * @response {@link Response} - OK
     */
    @DELETE
    @Path( "/{name}" )
    public Response deleteMovieJSON( @PathParam("name") String theatreName) 
    {
    	int i = new MoviePickDAO().deleteTheatre(theatreName);
    	
    	if(i > 0){
    		return Response.ok().build();
    	}else{
    		return Response.notModified().build();
    	}
    }
}
