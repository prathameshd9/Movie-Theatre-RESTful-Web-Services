package MoviePick;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import MoviePick.service.PostMovieService;
import MoviePick.service.DeleteMovieService;
import MoviePick.service.DeleteTheatreService;
import MoviePick.service.GetMovieService;
import MoviePick.service.UpdateMovieService;


/**
 * This is the Application class for the Movie pick Web RESTful service
 * It contains singleton objects representing the resource classes.
 *
 */
public class MoviePickApplication extends Application
{
    private Set<Object> singletons = new HashSet<Object>();
    private Set<Class<?>> empty = new HashSet<Class<?>>();

    public MoviePickApplication() {
        singletons.add(new PostMovieService());
        singletons.add(new DeleteMovieService());
        singletons.add(new DeleteTheatreService());
        singletons.add(new UpdateMovieService());
        singletons.add(new GetMovieService());
    }

    @Override
    public Set<Class<?>> getClasses() {
        return empty;
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
