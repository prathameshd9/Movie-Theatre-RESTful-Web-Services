package MoviePick.service;

import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import MoviePick.DAO.MoviePickDAO;




/*-------------------------------------DELETE---------------------------------------------------------------
 * 1. Deleting a movie
 *              URI               						  Cons/Prod         Response
 *   /cs8350_2/movie/deleteMovie/{title}					    JSON             200 OK
 *  
 * ---------------------------------------------------------------------------------------------------------



/**
 * MoviePick service implementation class
 * The main path is /cs8350_2/movie/deleteMovie/.
 */
@Path( "/cs8350_2/movie/deleteMovie" )
public class DeleteMovieService
{
	/**
     * Delete a movie using a path parameter
     * @param title path parameter identifying the movie
     * @response {@link Response} - OK
     */
    @DELETE
    @Path( "/{title}" )
    public Response deleteMovieJSON( @PathParam("title") String title) 
    {
    	int i = new MoviePickDAO().deleteMovie(title);
    	
    	if(i > 0){
    		return Response.ok().build();
    	}else{
    		return Response.notModified().build();
    	}
    }
}
