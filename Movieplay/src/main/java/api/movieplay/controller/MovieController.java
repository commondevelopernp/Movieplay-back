package api.movieplay.controller;


import api.movieplay.model.entity.Movie;
import api.movieplay.service.movie.MovieServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieServiceImpl movieService;

    @GetMapping
    public List<Movie> getAllMovies(@RequestParam(required = false) String genre,
                                    @RequestParam(required = false) String title,
                                    @RequestParam(required = false) String actor,
                                    @RequestParam(required = false) String sort,
                                    @RequestParam(required = false) Integer rate,
                                    @RequestParam(required = false, defaultValue = "asc") String order,
                                    @RequestParam(required = false, defaultValue = "1") int page,
                                    @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return movieService.getAllMovies(genre, title, actor, sort, order,rate, page, pageSize);
    }

    @PostMapping
    public Movie createMovie(@RequestBody Movie movie) {
        return movieService.saveMovie(movie);
    }
    @GetMapping("/{id}")
    public Movie getMovieById(@PathVariable String id) {
        Movie movie = movieService.getMovieById(id);
        return movie;
    }



}
