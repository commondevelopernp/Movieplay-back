package api.movieplay.model.dao;

import java.util.List;


import api.movieplay.model.entity.Movie;


public interface MovieDao {
    List<Movie> getAllMovies(String genre, String title, String actor, String sort, String order, Integer rate,int page, int pageSize);
    Movie getMovieById(String id);
	Movie save(Movie movie);


}
