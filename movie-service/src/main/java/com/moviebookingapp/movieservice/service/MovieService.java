package com.moviebookingapp.movieservice.service;

import java.util.List;

import com.moviebookingapp.movieservice.exceptions.DuplicateEntityFoundException;
import com.moviebookingapp.movieservice.model.Movie;
import com.moviebookingapp.movieservice.model.MovieDTO;

public interface MovieService {

	public List<Movie> getAllMovies();

	public Movie addMovie(MovieDTO movie) throws DuplicateEntityFoundException;

	public void deleteMovie(Integer movieId);

	public Movie getMovieById(Integer movieId);
	
	public Movie updateMovieSeats(Integer movieId, Integer bookSeats);
}
