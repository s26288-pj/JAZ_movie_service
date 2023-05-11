package com.example.movieservice.controller;

import com.example.movieservice.model.Movie;
import com.example.movieservice.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movieService")
public class MovieController {

    MovieService movieService = new MovieService();

    @GetMapping("/movies")
    public ResponseEntity<List<Movie>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @GetMapping("/movies/{id}")
    public ResponseEntity<Movie> getMovieByID(@PathVariable("id") int id) {
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
    public ResponseEntity<Movie> updateMovie(@PathVariable("id") int id, @RequestBody Movie movie) {
        Movie updatedMovie = movieService.findMovieByID(id);
        if(movieService.findMovieByID(id) != null && movie != null) {
            movieService.updateMovie(movie, id);
            return ResponseEntity.ok(updatedMovie);
        }
        else return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/movies/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable("id") int id) {
        if(movieService.findMovieByID(id) != null) {
            movieService.deleteMovie(id);
            return ResponseEntity.status(204).build();
        }
        else return ResponseEntity.status(404).build();
    }
}
