package com.example.movieservice.service;

import com.example.movieservice.exception.MovieNotFoundException;
import com.example.movieservice.model.Movie;
import com.example.movieservice.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie findMovieByID(Long id) throws MovieNotFoundException {
        return movieRepository.findById(id).orElseThrow(MovieNotFoundException::new);
    }

    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public void updateMovie(Movie movieWithUpdatedData, Long id) throws MovieNotFoundException {
        Movie movie = findMovieByID(id);
        if (movie != null) {
            movie.setDirector(movieWithUpdatedData.getDirector());
            movie.setName(movieWithUpdatedData.getName());
            movie.setCategory(movieWithUpdatedData.getCategory());
            movieRepository.save(movie);
        }
    }

    public void rentMovie(Long id) throws MovieNotFoundException {
        Movie movie = findMovieByID(id);
        movie.setAvailable(!movie.isAvailable());
        movieRepository.save(movie);
    }

    public void deleteMovie(Long id) throws MovieNotFoundException {
        Movie movie = findMovieByID(id);
        if (movie != null) movieRepository.deleteById(id);
        else throw new IllegalArgumentException("Could not find movie with id " + id);
    }
}
