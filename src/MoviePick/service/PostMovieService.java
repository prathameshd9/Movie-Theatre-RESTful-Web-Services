package MoviePick.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import MoviePick.DAO.MoviePickDAO;
import MoviePick.resource.Movie;
import MoviePick.resource.Theatre;

//------------------------------------- POST--------------------------------------------------------
//URI               					Cons/Prod         Action       Return
///cs8350_2/movie/createMovie        	XML               Create       200 OK
///cs8350_2/movie/createMovie        	JSON              Create       200 OK

///cs8350_2/movie/createTheatre         XML               Create       200 OK
///cs8350_2/movie/createTheatre         JSON              Create       200 OK
//--------------------------------------------------------------------------------------------------

/**
 *	GET movie service class
 * 	The main path is /cs8350_2/movie/
 */
@Path( "/cs8350_2/movie" )
public class PostMovieService {

	MoviePickDAO movieDAO;

	/**
	 * Create a new movie entry using an JSON representation. 
	 * @param is input stream for reading the payload
	 * @return a response encoding
	 * @response OK
	 **/
	@POST
	@Path("/createMovie")
	@Consumes( MediaType.APPLICATION_JSON )
	public Response createMovieJSON( Movie movie ) 
	{

		movieDAO=new MoviePickDAO();
		int i = movieDAO.createMovie(movie);

		if(i > 0){
			return Response.ok().build();
					
		}else{
			return Response.notModified().build();
		}
	}

	/**
	 * Create a new theatre entry using an JSON representation. 
	 * @param is input stream for reading the payload
	 * @return a response encoding
	 * @response  OK
	 **/
	@POST
	@Path("/createTheatre")
	@Consumes( MediaType.APPLICATION_JSON )
	public Response createTheatreJSON( Theatre theatre ) 
	{

		movieDAO=new MoviePickDAO();
		int i = movieDAO.createTheatre(theatre);

		if(i > 0){
			return Response.ok().build();
		}else{
			return Response.notModified().build();
		}

	}
}
