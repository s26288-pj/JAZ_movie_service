package com.example.movieservice.controller;

import com.example.movieservice.exception.MovieNotFoundException;
import com.example.movieservice.model.Movie;
import com.example.movieservice.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movieService")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/movies")
    public ResponseEntity<List<Movie>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @GetMapping("/movies/{id}")
    public ResponseEntity<Movie> getMovieByID(@PathVariable("id") Long id) throws MovieNotFoundException {
        if(movieService.findMovieByID(id) != null) return ResponseEntity.ok(movieService.findMovieByID(id));
        else return ResponseEntity.status(404).build();
    }

    @PostMapping("/movies")
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
        if(movie != null) {
            movieService.addMovie(movie);
            return ResponseEntity.ok(movie);
        }
        else return ResponseEntity.badRequest().build();
    }

    @PutMapping("/movies/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable("id") Long id, @RequestBody Movie movie) throws MovieNotFoundException {
        Movie updatedMovie = movieService.findMovieByID(id);
        if(movieService.findMovieByID(id) != null && movie != null) {
            movieService.updateMovie(movie, id);
            return ResponseEntity.ok(updatedMovie);
        }
        else return ResponseEntity.badRequest().build();
    }

    @PutMapping("/movies/{id}/rent")
    public void rentMovie(@PathVariable("id") Long id) throws MovieNotFoundException {
        movieService.rentMovie(id);
    }

    @DeleteMapping("/movies/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable("id") Long id) throws MovieNotFoundException {
        if(movieService.findMovieByID(id) != null) {
            movieService.deleteMovie(id);
            return ResponseEntity.status(204).build();
        }
        else return ResponseEntity.status(404).build();
    }
}
