package api.movieplay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import api.movieplay.model.dao.MovieRepository;
import api.movieplay.model.dao.MovieTitleAndImageDTO;
import api.movieplay.model.entity.Movie;
import java.util.List;
import java.util.Optional; 
import java.util.stream.Collectors;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public List<MovieTitleAndImageDTO> getMovieTitlesAndFirstImages() {
        return movieRepository.findAll().stream()
                .map(movie -> new MovieTitleAndImageDTO(movie.getTitle(), 
                             movie.getImages() != null && !movie.getImages().isEmpty() ? movie.getImages().get(0) : null))
                .collect(Collectors.toList());
    }

    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }

    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }
}
