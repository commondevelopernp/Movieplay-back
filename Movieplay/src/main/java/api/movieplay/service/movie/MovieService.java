package api.movieplay.service.movie;
import api.movieplay.model.entity.Movie;

import java.util.List;


public interface MovieService {
    List<Movie> getAllMovies(String genre, String title, String actor, String sort, String order, Integer rate, int page, int pageSize);
    Movie getMovieById(String id);
    Movie saveMovie(Movie movie);

}
