package com.example.movieservice.service;

import com.example.movieservice.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieService {
    private final List<Movie> movies = new ArrayList<>();

    public MovieService() {
        movies.add(new Movie(1, "Test 1", "Comedy", "Alan"));
        movies.add(new Movie(2, "Test 23", "Horror", "Ben"));
        movies.add(new Movie(3, "Test 456", "Thriller", "John"));
    }

    public List<Movie> getAllMovies() {
        return movies;
    }

    public Movie findMovieByID(int id) {
        for (Movie movie : movies) {
            if (movie.getID() == id) {
                return movie;
            }
        }
        return null;
    }

    public void addMovie(Movie movie) {
        movies.add(movie);
    }

    public void updateMovie(Movie movieWithUpdatedData, int id) {
        Movie movie = findMovieByID(id);
        if (movie != null) {
            movie.setName(movieWithUpdatedData.getName());
            movie.setCategory(movieWithUpdatedData.getCategory());
            movie.setDirector(movieWithUpdatedData.getDirector());
        }
    }

    public void deleteMovie(int id){
        Movie movie = findMovieByID(id);
        if (movie != null) movies.remove(movie);
        else throw new IllegalArgumentException("Could not find movie with id " + id);
    }
}
