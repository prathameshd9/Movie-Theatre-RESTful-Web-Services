
// This client is using the new JAX-RS 2.0 client interface

import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MovieClient {

	public static void main(String[] args) {

		String movie1 = "{\"title\":\"Avengers\",\"genre\":\"action\",\"rating\":4}";
		String theatre1 = "{\"name\":\"Georgia_Theatre\",\"address\":\"215 N Lumpkin St\"}";
		ClientResponse<String> response = null;
		ClientRequest request;
		try {

			/* === Create movie using POST request (JSON) === */

			System.out.println("Creating a movie (JSON):\n" + prettyPrintJSON(movie1));
			request = new ClientRequest("http://uml.cs.uga.edu:8080/CS8350_2_movies/rest/cs8350_2/movie/createMovie/");
			request.body(MediaType.APPLICATION_JSON, movie1);
			request.accept(MediaType.APPLICATION_JSON);
			response = request.post(String.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("POST Request failed: HTTP code: " + response.getStatus());
			} else {
				System.out.println("Movie created.");
			}

			response.releaseConnection(); // this response must be closed before
											// we use client object

			/* === Create theatre using POST request (JSON) === */

			System.out.println();
			System.out.println("Creating a theatre (JSON):\n" + prettyPrintJSON(theatre1));
			request = new ClientRequest("http://uml.cs.uga.edu:8080/CS8350_2_movies/rest/cs8350_2/movie/createTheatre/");
			request.body(MediaType.APPLICATION_JSON, theatre1);
			request.accept(MediaType.APPLICATION_JSON);
			response = request.post(String.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("POST Request failed: HTTP code: " + response.getStatus());
			} else {
				System.out.println("Theatre created.");
			}

			response.releaseConnection(); // this response must be closed before
											// we can
			// reuse the client object

			/*
			 * === Retrieve the movie details using a GET request and JSON
			 * representation ===
			 */

			System.out.println();
			System.out.println("Retrieving movie details (JSON representation)");
			request = new ClientRequest("http://uml.cs.uga.edu:8080/CS8350_2_movies/rest/cs8350_2/movie/getMovie/");
			response = request.get(String.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("GET Request failed: HTTP code: " + response.getStatus());
			} else {

				System.out.println("OK: Retrieved movie details");
				String p = response.getEntity(String.class);
				System.out.println(prettyPrintJSONArray(p));
			}
			response.releaseConnection();

			/*
			 * === Retrieve the movie details by title using a GET request and
			 * JSON representation ===
			 */

			System.out.println();
			System.out.println("Retrieving movie details by title (JSON representation)");

			request = new ClientRequest("http://uml.cs.uga.edu:8080/CS8350_2_movies/rest/cs8350_2/movie/getMovie/title/The_Wolverine");
			response = request.get(String.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("GET Request failed: HTTP code: " + response.getStatus());
			} else {
				System.out.println("OK: Retrieved movie by title");
				String p = response.getEntity(String.class);
				System.out.println(prettyPrintJSON(p));
			}
			response.releaseConnection();

			/*
			 * * === Retrieve the movie details by genre using a GET request and
			 * JSON representation ===
			 */

			System.out.println();
			System.out.println("Retrieving movie details by genre (JSON representation)");

			request = new ClientRequest("http://uml.cs.uga.edu:8080/CS8350_2_movies/rest/cs8350_2/movie/getMovie/genre/action");
			response = request.get(String.class);
			if (response.getStatus() != 200) {
				throw new RuntimeException("GET Request failed: HTTP code: " + response.getStatus());
			} else {
				System.out.println("OK: Retrieved movie by genre");
				String p = response.getEntity(String.class);
				System.out.println(prettyPrintJSONArray(p));
			}
			response.releaseConnection();

			/**
			 * === Retrieve the movie details by rating using a GET request and
			 * JSON representation ===
			 */

			System.out.println();
			System.out.println("Retrieving movie details by rating (JSON representation)");

			request = new ClientRequest("http://uml.cs.uga.edu:8080/CS8350_2_movies/rest/cs8350_2/movie/getMovie/rating/4.0");
			response = request.get(String.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("GET Request failed: HTTP code: " + response.getStatus());
			} else {
				System.out.println("OK: Retrieved movie by rating");
				String p = response.getEntity(String.class);
				System.out.println(prettyPrintJSONArray(p));
			}
			response.releaseConnection();

			/*
			 * * === Retrieve the movie details by theatre using a GET request
			 * and JSON representation ===
			 */

			System.out.println();
			System.out.println("Retrieving movie details by theatre (JSON representation)");

			request = new ClientRequest("http://uml.cs.uga.edu:8080/CS8350_2_movies/rest/cs8350_2/movie/getMovie/theatre/Beechwood_Cinemas");
			response = request.get(String.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("GET Request failed: HTTP code: " + response.getStatus());
			} else {
				System.out.println("OK: Retrieved movie by theatre");
				String p = response.getEntity(String.class);
				System.out.println(prettyPrintJSON(p));
			}
			response.releaseConnection();

			/*
			 * * === Update rating of a movie using a PUT request and JSON
			 * representation ===
			 */

			System.out.println();
			System.out.println("Updating movie rating.");
			// create a new target

			request = new ClientRequest("http://uml.cs.uga.edu:8080/CS8350_2_movies/rest/cs8350_2/movie/updateMovie/Rings/4.7");
			response = request.put(String.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("PUT Request failed: HTTP code: " + response.getStatus());
			} else {
				System.out.println("OK: Updated the Movie");
			}
			response.releaseConnection();

			/**
			 * === Update theatre showtime of a movie using a PUT request and
			 * JSON representation ===
			 */

			System.out.println();
			System.out.println("Updating theatre and showtime for a movie.");
			// create a new target
			request = new ClientRequest("http://uml.cs.uga.edu:8080/CS8350_2_movies/rest/cs8350_2/movie/updateMovie/Rings/updateTheatre/University_16/2pm_5pm");
			response = request.put(String.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("PUT Request failed: HTTP code: " + response.getStatus());
			} else
				System.out.println("OK: Updated the theatre");
			response.releaseConnection();

			/**
			 * === Assign a showTime to a movie using a PUT request and JSON
			 * representation ===
			 */

			System.out.println();
			System.out.println("Assigning showtime to a movie in a theatre.");
			// create a new target
			request = new ClientRequest("http://uml.cs.uga.edu:8080/CS8350_2_movies/rest/cs8350_2/movie/updateMovie/Rings/updateShowTime/Ovation_12/1pm_4pm_7pm");
			response = request.put(String.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("PUT Request failed: HTTP code: " + response.getStatus());
			} else
				System.out.println("OK: Updated the show time");
			response.releaseConnection();

			/* === Delete the movie using a DELETE request === */

			System.out.println();
			System.out.println("Deleting a movie.");

			request = new ClientRequest("http://uml.cs.uga.edu:8080/CS8350_2_movies/rest/cs8350_2/movie/deleteMovie/Avengers");
			response = request.delete(String.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("DELETE Request failed: HTTP code: " + response.getStatus());
			} else
				System.out.println("OK: Deleted the movie");
			response.releaseConnection();

			/* === Delete the theatre using a DELETE request === */

			System.out.println();
			System.out.println("Deleting a theatre.");

			request = new ClientRequest("http://uml.cs.uga.edu:8080/CS8350_2_movies/rest/cs8350_2/movie/deleteTheatre/Georgia_Theatre");
			response = request.delete(String.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("DELETE Request failed: HTTP code: " + response.getStatus());
			} else
				System.out.println("OK: Deleted the theatre");
			response.releaseConnection();

		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}

	}

	public static String prettyPrintJSON(String input) {
		JsonObject obj = new JsonParser().parse(input).getAsJsonObject();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		return gson.toJson(obj);
	}
	
	public static String prettyPrintJSONArray(String input) {
		JsonArray obj = new JsonParser().parse(input).getAsJsonArray();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		return gson.toJson(obj);
	}

}
