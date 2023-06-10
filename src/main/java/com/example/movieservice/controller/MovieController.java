package com.example.movieservice.controller;

import com.example.movieservice.exception.MovieNotFoundException;
import com.example.movieservice.model.Movie;
import com.example.movieservice.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
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
    @Operation(summary = "Get all movies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all movies",
                    content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Movie.class))}),
            @ApiResponse(responseCode = "404", description = "Movies not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Unknown Exception",
                    content = @Content)})
    public ResponseEntity<List<Movie>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @GetMapping("/movies/{id}")
    @Operation(summary = "Get movie by provided id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found movie",
                    content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Movie.class))}),
            @ApiResponse(responseCode = "404", description = "Movie not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Unknown Exception",
                    content = @Content)})
    public ResponseEntity<Movie> getMovieByID(@PathVariable("id") Long id) throws MovieNotFoundException {
        if(movieService.findMovieByID(id) != null) return ResponseEntity.ok(movieService.findMovieByID(id));
        else return ResponseEntity.status(404).build();
    }

    @PostMapping("/movies")
    @Operation(summary = "Add movie to database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie added successfully",
                    content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Movie.class))}),
            @ApiResponse(responseCode = "404", description = "Could not add movie",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Unknown Exception",
                    content = @Content)})
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
        if(movie != null) {
            movieService.addMovie(movie);
            return ResponseEntity.ok(movie);
        }
        else return ResponseEntity.badRequest().build();
    }

    @PutMapping("/movies/{id}")
    @Operation(summary = "Update movie with id with data provided")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie updated successfully",
                    content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Movie.class))}),
            @ApiResponse(responseCode = "404", description = "Movie not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Unknown Exception",
                    content = @Content)})
    public ResponseEntity<Movie> updateMovie(@PathVariable("id") Long id, @RequestBody Movie movie) throws MovieNotFoundException {
        Movie updatedMovie = movieService.findMovieByID(id);
        if(movieService.findMovieByID(id) != null && movie != null) {
            movieService.updateMovie(movie, id);
            return ResponseEntity.ok(updatedMovie);
        }
        else return ResponseEntity.badRequest().build();
    }

    @PutMapping("/movies/{id}/rent")
    @Operation(summary = "Rent movie with provided id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie rented",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Movie not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Unknown Exception",
                    content = @Content)})
    public void updateMovieAvailability(@PathVariable("id") Long id) throws MovieNotFoundException {
        movieService.rentMovie(id);
    }

    @PutMapping("/movies/{id}/return")
    @Operation(summary = "Return movie with provided id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie returned",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Movie not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Unknown Exception",
                    content = @Content)})
    public void returnMovie(@PathVariable("id") Long id) throws MovieNotFoundException {
        movieService.returnMovie(id);
    }

    @DeleteMapping("/movies/{id}")
    @Operation(summary = "Delete movie from database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie deleted successfully",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Movie not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Unknown Exception",
                    content = @Content)})
    public ResponseEntity<Void> deleteMovie(@PathVariable("id") Long id) throws MovieNotFoundException {
        if(movieService.findMovieByID(id) != null) {
            movieService.deleteMovie(id);
            return ResponseEntity.status(204).build();
        }
        else return ResponseEntity.status(404).build();
    }
}
