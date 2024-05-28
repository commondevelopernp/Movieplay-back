package api.movieplay.controller;

import api.movieplay.model.dao.MovieTitleAndImageDTO;
import api.movieplay.model.entity.Movie;
import api.movieplay.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/titles-and-images")
    public List<MovieTitleAndImageDTO> getMovieTitlesAndFirstImages() {
        return movieService.getMovieTitlesAndFirstImages();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        Optional<Movie> movie = movieService.getMovieById(id);
        return movie.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Movie createMovie(@RequestBody Movie movie) {
        return movieService.saveMovie(movie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody Movie movieDetails) {
        Optional<Movie> movieOptional = movieService.getMovieById(id);
        if (!movieOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Movie movie = movieOptional.get();
        movie.setTitle(movieDetails.getTitle());
        movie.setGenre(movieDetails.getGenre());
        movie.setDuration(movieDetails.getDuration());
        movie.setDirector(movieDetails.getDirector());

        Movie updatedMovie = movieService.saveMovie(movie);
        return ResponseEntity.ok(updatedMovie);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        if (!movieService.getMovieById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }
}
