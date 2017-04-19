package MoviePick.DAO;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import MoviePick.resource.Movie;
import MoviePick.resource.Theatre;

public class MoviePickDAO {

	Connection connection;

	public MoviePickDAO() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://uml.cs.uga.edu:3306/team2", "team2", "interface");
		} catch (ClassNotFoundException ex) {
			System.err.println(ex.getMessage());
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int updateMovieRating(String title, float rating) {

		PreparedStatement preparedStmt = null;
		int i = 0;

		String message = "";
		try {
			preparedStmt = (PreparedStatement) connection.prepareStatement("update Movie set rating=? where title=?");

			preparedStmt.setString(2, title);
			preparedStmt.setFloat(1, rating);

			i = preparedStmt.executeUpdate();

			preparedStmt.close();
			connection.close();

		} catch (SQLException e) {
			System.out.println("SQl exception  " + e.getMessage());
			return 0;
		}

		return i;
	}

	public int updateMovieShowTime(String title, String theatreName, String showTime) {

		PreparedStatement preparedStmt = null;
		int i = 0;

		String movieTitle = title;
		String tName = theatreName;
		String mShowTime = showTime;

		String message = "";

		try {

			String query1 = "select showTimes from MovieShowTimings where theatreId= (select theatreId from Theatre where name=?) AND movieId=(select movieId from Movie where title=?)";
			preparedStmt = (PreparedStatement) connection.prepareStatement(query1);

			preparedStmt.setString(1, tName);
			preparedStmt.setString(2, movieTitle);

			ResultSet rs = preparedStmt.executeQuery();
			rs.next();

			String presentShowTimes = rs.getString(1);

			if (!(presentShowTimes.equals(mShowTime))) {
				String query = "update MovieShowTimings set showTimes=? where theatreId= (select theatreId from Theatre where name=?) AND movieId=(select movieId from Movie where title=?)";
				preparedStmt = (PreparedStatement) connection.prepareStatement(query);

				presentShowTimes = presentShowTimes + "_" + mShowTime;
				preparedStmt.setString(1, presentShowTimes);
				preparedStmt.setString(2, tName);
				preparedStmt.setString(3, movieTitle);

				i = preparedStmt.executeUpdate();

			} else {
				return 0;
			}
			preparedStmt.close();
			connection.close();

		} catch (SQLException e) {
			System.out.println("SQl exception  " + e.getMessage());
			return 0;
		}
		return i;
	}

	public int updateTheatre(String title, String theatreName, String showTimes) {
		PreparedStatement preparedStmt = null;
		int i = 0;
		String message = "";

		try {
			preparedStmt = (PreparedStatement) connection.prepareStatement("select max(id) from MovieShowTimings");
			ResultSet rs = preparedStmt.executeQuery();
			rs.next();
			int id = rs.getInt(1) + 1;

			preparedStmt = (PreparedStatement) connection
					.prepareStatement("select theatreId from Theatre where name=?");
			preparedStmt.setString(1, theatreName);
			rs = preparedStmt.executeQuery();
			rs.next();
			int theatreId = rs.getInt(1);

			preparedStmt = (PreparedStatement) connection.prepareStatement("select movieId from Movie where title=?");
			preparedStmt.setString(1, title);
			rs = preparedStmt.executeQuery();
			rs.next();
			int movieId = rs.getInt(1);

			if (movieId > 1000 && theatreId > 100) {
				preparedStmt = (PreparedStatement) connection.prepareStatement(
						"insert into MovieShowTimings (id,theatreId,movieId,showTimes) values(?,?,?,?)");

				preparedStmt.setInt(1, id);
				preparedStmt.setInt(2, theatreId);
				preparedStmt.setInt(3, movieId);
				preparedStmt.setString(4, showTimes);

				i = preparedStmt.executeUpdate();

			} else {
				return 0;
			}
		} catch (SQLException e) {
			System.out.println("SQl exception  " + e.getMessage());
			return 0;
		}

		return i;
	}

	// Delete a movie
	public int deleteMovie(String title) {
		PreparedStatement preparedStmt = null;
		
		try {

			String delQuery = "delete from MovieShowTimings where movieId= (select movieId from Movie where title=?);";
			preparedStmt = (PreparedStatement) connection.prepareStatement(delQuery);

			preparedStmt.setString(1, title);
			int i = preparedStmt.executeUpdate();

			// Then delete the movie from Movie table
			String query1 = "delete from Movie where title=?;";
			preparedStmt = (PreparedStatement) connection.prepareStatement(query1);

			preparedStmt.setString(1, title);
			int j = preparedStmt.executeUpdate();

			if (i == 1 || j == 1)
				return 1;
			else
				return 0;

		} catch (SQLException e) {
			System.out.println("SQl exception  " + e.getMessage());
			return 0;
		}
	}

	// Delete a theatre
	public int deleteTheatre(String theatreName) {
		PreparedStatement preparedStmt = null;
		try {

			String delQuery = "delete from MovieShowTimings where theatreId= (select theatreId from Theatre where name=?);";
			preparedStmt = (PreparedStatement) connection.prepareStatement(delQuery);

			preparedStmt.setString(1, theatreName);
			int i = preparedStmt.executeUpdate();

			// Then delete the movie from Movie table
			String query1 = "delete from Theatre where name=?;";
			preparedStmt = (PreparedStatement) connection.prepareStatement(query1);

			preparedStmt.setString(1, theatreName);
			int j = preparedStmt.executeUpdate();

			if (i == 1 || j == 1)
				return 1;
			else
				return 0;

		} catch (SQLException e) {
			System.out.println("SQl exception  " + e.getMessage());
			return 0;
		}
	}

	// Create a new Movie Object
	public int createMovie(Movie mv) {
		PreparedStatement preparedStmt = null;
		int movieId = 0;
		String message = "";

		ResultSet rs = null;
		try {
			preparedStmt = (PreparedStatement) connection.prepareStatement("SELECT max(movieId) FROM Movie");
			rs = preparedStmt.executeQuery();
			rs.next();
			movieId = rs.getInt(1) + 1;

			preparedStmt = (PreparedStatement) connection.prepareStatement("insert into Movie values(?,?,?,?)");

			preparedStmt.setInt(1, movieId);
			preparedStmt.setString(2, mv.getTitle());
			preparedStmt.setString(3, mv.getGenre());
			preparedStmt.setFloat(4, mv.getRating());

			int i = preparedStmt.executeUpdate();

			message = "Movie " + movieId + "--" + mv.getTitle() + " created";

		} catch (SQLException e) {
			System.out.println("SQl exception  " + e.getMessage());
		} finally {
			if (preparedStmt != null) {
				try {
					preparedStmt.close();
				} catch (SQLException e) {
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
				}
			}
		}

		System.out.println(message);
		return movieId;
	}

	// Create a new Theatre object
	public int createTheatre(Theatre th) {
		PreparedStatement preparedStmt = null;
		int theatreId = 0;
		String message = "";
		int i = 0;
		ResultSet rs = null;
		try {
			preparedStmt = (PreparedStatement) connection.prepareStatement("SELECT max(theatreId) FROM Theatre");
			rs = preparedStmt.executeQuery();
			rs.next();
			theatreId = rs.getInt(1) + 1;

			preparedStmt = (PreparedStatement) connection.prepareStatement("insert into Theatre values(?,?,?)");

			preparedStmt.setInt(1, theatreId);
			preparedStmt.setString(2, th.getName());
			preparedStmt.setString(3, th.getAddress());

			i = preparedStmt.executeUpdate();

			message = "Theatre " + theatreId + "--" + th.getName() + " created";

		} catch (SQLException e) {
			System.out.println("SQl exception  " + e.getMessage());
		} finally {
			if (preparedStmt != null) {
				try {
					preparedStmt.close();
				} catch (SQLException e) {
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
				}
			}
		}

		System.out.println(message);
		return i;
	}

	// Show details of movies and theatre show timings
	public ArrayList<Movie> getAllMovieDetails() {
		PreparedStatement preparedStmt = null;
		ArrayList<Movie> movieList = new ArrayList<Movie>();
		List<Theatre> theatres;
		Boolean flag = false;
		ResultSet rs = null;
		Theatre theatre;
		Movie movie;
		try {

			String queryPart1 = "select m.title,m.genre,m.rating, t.name, t.address, mst.showTimes from Movie m ";
			String queryPart2 = "Join MovieShowTimings mst on m.movieId=mst.movieId Join Theatre t on t.theatreId=mst.theatreId order by m.title;";
			String query = queryPart1 + queryPart2;
			preparedStmt = (PreparedStatement) connection.prepareStatement(query);
			rs = preparedStmt.executeQuery();

			while (rs.next()) {
				if (movieList.isEmpty()) {
					movie = new Movie();
					theatre = new Theatre();
					movie.setTitle(rs.getString("title"));
					movie.setGenre(rs.getString("genre"));
					movie.setRating(rs.getFloat("rating"));
					theatre.setName(rs.getString("name"));
					theatre.setAddress(rs.getString("address"));
					theatre.setShowTime(rs.getString("showTimes"));
					theatres = new ArrayList<Theatre>();
					theatres.add(theatre);
					movie.setTheatres(theatres);
					movieList.add(movie);
				}

				else {
					for (Movie m : movieList) {
						if (m.getTitle().equalsIgnoreCase(rs.getString("title"))) {
							theatres = new ArrayList<Theatre>();
							theatres = m.getTheatres();
							theatre = new Theatre();
							theatre.setName(rs.getString("name"));
							theatre.setAddress(rs.getString("address"));
							theatre.setShowTime(rs.getString("showTimes"));
							theatres.add(theatre);
							flag = false;
							break;
						} else {
							flag = true;
						}
					}

					if (flag) {
						movie = new Movie();
						theatre = new Theatre();
						movie.setTitle(rs.getString("title"));
						movie.setGenre(rs.getString("genre"));
						movie.setRating(rs.getFloat("rating"));
						theatre.setName(rs.getString("name"));
						theatre.setAddress(rs.getString("address"));
						theatre.setShowTime(rs.getString("showTimes"));
						theatres = new ArrayList<Theatre>();
						theatres.add(theatre);
						movie.setTheatres(theatres);
						movieList.add(movie);
					}
				}
			}

		} catch (SQLException e) {
			System.out.println("SQl exception  " + e.getMessage());
		}

		return movieList;
	}

	// Show details of movies and theatre show timings
	public Movie getMovieDetailsByTitle(String title) {
		PreparedStatement preparedStmt = null;
		Movie movie = new Movie();
		ResultSet rs = null;
		List<Theatre> theatres = new ArrayList<Theatre>();
		Theatre theatre;
		try {

			String queryPart1 = "select m.title,m.genre,m.rating, t.name, t.address, mst.showTimes from Movie m ";
			String queryPart2 = "Join MovieShowTimings mst on m.movieId=mst.movieId Join Theatre t on t.theatreId=mst.theatreId AND m.title='"
					+ title + "';";
			String query = queryPart1 + queryPart2;
			preparedStmt = (PreparedStatement) connection.prepareStatement(query);
			rs = preparedStmt.executeQuery();

			while (rs.next()) {
				movie.setTitle(title);
				movie.setGenre(rs.getString("genre"));
				movie.setRating(rs.getFloat("rating"));
				theatre = new Theatre();
				theatre.setName(rs.getString("name"));
				theatre.setAddress(rs.getString("address"));
				theatre.setShowTime(rs.getString("showTimes"));
				theatres.add(theatre);

			}
			movie.setTheatres(theatres);

		} catch (SQLException e) {
			System.out.println("SQl exception  " + e.getMessage());
		}

		return movie;
	}

	// Show details of movies and theatre show timings
	public ArrayList<Movie> getMovieDetailsByGenre(String genre) {
		PreparedStatement preparedStmt = null;
		ArrayList<Movie> movieList = new ArrayList<Movie>();
		ResultSet rs = null;
		Movie movie;
		try {

			String query = "select * from Movie m where m.genre='" + genre + "';";
			preparedStmt = (PreparedStatement) connection.prepareStatement(query);
			rs = preparedStmt.executeQuery();

			while (rs.next()) {
				movie = new Movie();
				movie.setTitle(rs.getString("title"));
				movie.setRating(rs.getFloat("rating"));
				movieList.add(movie);
			}

		} catch (SQLException e) {
			System.out.println("SQl exception  " + e.getMessage());
		} finally {
			if (preparedStmt != null) {
				try {
					preparedStmt.close();
				} catch (SQLException e) {
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
				}
			}
		}
		return movieList;
	}

	// Show details of movies and theatre show timings
	public ArrayList<Movie> getMovieDetailsByRating(Float rating) {
		PreparedStatement preparedStmt = null;
		ArrayList<Movie> movieList = new ArrayList<Movie>();
		ResultSet rs = null;
		Movie movie;
		try {

			String query = "select * from Movie m where m.rating='" + rating + "';";
			preparedStmt = (PreparedStatement) connection.prepareStatement(query);
			rs = preparedStmt.executeQuery();

			while (rs.next()) {
				movie = new Movie();
				movie.setTitle(rs.getString("title"));
				movie.setGenre(rs.getString("genre"));
				movie.setRating(rs.getFloat("rating"));
				movieList.add(movie);

			}

		} catch (SQLException e) {
			System.out.println("SQl exception  " + e.getMessage());
		}

		return movieList;
	}

	// Show details of movies and theatre show timings
	public Theatre getMovieDetailsByTheatre(String theatreName) {
		PreparedStatement preparedStmt = null;
		ArrayList<Movie> movieList = new ArrayList<Movie>();
		Movie movie;
		Theatre theatre = new Theatre();
		ResultSet rs = null;
		try {

			String queryPart1 = "select m.title,m.genre,m.rating, mst.showTimes from Movie m ";
			String queryPart2 = "Join MovieShowTimings mst on m.movieId=mst.movieId Join Theatre t on t.theatreId=mst.theatreId AND t.name='"
					+ theatreName + "'order by t.name;";
			String query = queryPart1 + queryPart2;
			preparedStmt = (PreparedStatement) connection.prepareStatement(query);
			rs = preparedStmt.executeQuery();

			while (rs.next()) {
				movie = new Movie();
				movie.setTitle(rs.getString("title"));
				movie.setGenre(rs.getString("genre"));
				movie.setRating(rs.getFloat("rating"));
				movie.setShowTime(rs.getString("showTimes"));
				movieList.add(movie);
				theatre.setMovies(movieList);
			}

		} catch (SQLException e) {
			System.out.println("SQl exception  " + e.getMessage());
		}

		return theatre;
	}
}
