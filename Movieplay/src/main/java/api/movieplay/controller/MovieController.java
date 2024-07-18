package api.movieplay.controller;

import api.movieplay.model.entity.Movie;
import api.movieplay.model.entity.User;
import api.movieplay.service.movie.MovieServiceImpl;
import api.movieplay.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieServiceImpl movieService;
    @Autowired
    private UserService userService;

    @GetMapping
    public List<Movie> getAllMovies(@RequestParam(required = false) String genre,
                                    @RequestParam(required = false) String title,
                                    @RequestParam(required = false) String actor,
                                    @RequestParam(required = false) String sort,
                                    @RequestParam(required = false) Integer rate,
                                    @RequestParam(required = false, defaultValue = "asc") String order,
                                    @RequestParam(required = false, defaultValue = "1") int page,
                                    @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return movieService.getAllMovies(genre, title, actor, sort, order, rate, page, pageSize);
    }

    @PostMapping
    public Movie createMovie(@RequestBody Movie movie) {
        return movieService.saveMovie(movie);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        Movie movie = movieService.getMovieById(id);
        if (movie != null) {
            return ResponseEntity.ok(movie);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/favorites/{id}")
    public ResponseEntity<List<Movie>> getFavoritesUser(@PathVariable Long id) {
        Optional<User> userOptional = userService.findUserById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<Movie> favoriteMovies = user.getFavorited(); // Asegúrate de que este método existe y devuelve List<Movie>
            if (favoriteMovies != null && !favoriteMovies.isEmpty()) {
                return ResponseEntity.ok(favoriteMovies);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody Movie movie) {
        movie.setId(id); // Asegúrate de que el ID de la película sea el mismo que el del path variable
        Movie updatedMovie = movieService.saveMovie(movie);
        return ResponseEntity.ok(updatedMovie);
    }
}
