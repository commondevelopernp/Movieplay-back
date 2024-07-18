package api.movieplay.service.movie;

import api.movieplay.model.dao.MovieDao;
import api.movieplay.model.entity.Movie;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieServiceImpl implements MovieService {
	@Autowired
    private final MovieDao movieDao;

    public MovieServiceImpl(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    @Override
    public List<Movie> getAllMovies(String genre, String title, String actor, String sort, String order, Integer rate,int page,int pageSize) {
        return movieDao.getAllMovies(genre, title, actor, sort, order,rate , page, pageSize);
    }

    @Override
    public Movie getMovieById(Long id) {
        return movieDao.getMovieById(id);
    }
    @Override
    public List<Movie> getMoviesByIds(List<Long> ids) {
        return movieDao.findAllById(ids);
    }
	@Override
	public Movie saveMovie(Movie movie) {
		return movieDao.save(movie);
	}

}
