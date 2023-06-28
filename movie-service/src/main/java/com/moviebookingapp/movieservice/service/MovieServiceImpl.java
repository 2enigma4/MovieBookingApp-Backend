package com.moviebookingapp.movieservice.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moviebookingapp.movieservice.exceptions.DuplicateEntityFoundException;
import com.moviebookingapp.movieservice.model.Movie;
import com.moviebookingapp.movieservice.model.MovieDTO;
import com.moviebookingapp.movieservice.model.MovieStatus;
import com.moviebookingapp.movieservice.repository.MovieRepository;

import static com.moviebookingapp.movieservice.utility.ServiceHelper.minTotalSeat;
import static com.moviebookingapp.movieservice.utility.ServiceHelper.maxTotalSeat;


@Service
public class MovieServiceImpl implements MovieService {

	@Autowired
	private MovieRepository movieRepo;

	public boolean movieAlreadyExists(MovieDTO movie) {
		Movie movieByNameAndTheater = movieRepo.getMovieByNameAndTheater(movie.getMovieName(), movie.getTheaterName());
		return movieByNameAndTheater != null ? true : false;
	}

	
	@Override
	public List<Movie> getAllMovies() {
		List<Movie> allMovies = movieRepo.findAll().stream()
				.sorted(Comparator.comparing(Movie::getMovieName))
				.collect(Collectors.toList());
		
		if (allMovies.isEmpty())
			throw new EntityNotFoundException("No Movies Available");
		else
			return allMovies;

	}

	
	@Override
	public Movie addMovie(MovieDTO movie) throws DuplicateEntityFoundException {
		
		boolean isNullValueFound = movie.checkNull();
		
		if (isNullValueFound)
			throw new NullPointerException("Null Value found");
		
//		check movie and theater already exists in database
		if (movieAlreadyExists(movie))
			throw new DuplicateEntityFoundException("Entity Already exists");
		
//		check total seats are between 100-1000
		if(!minTotalSeat.and(maxTotalSeat).test(movie))
			throw new IllegalArgumentException("Required Seats Between 100 to 1000");
		
		Movie newMovie = new Movie();
		newMovie.setMovieName(movie.getMovieName());
		newMovie.setTheaterName(movie.getTheaterName());
		newMovie.setTotalSeats(movie.getTotalSeats());
		newMovie.setTotalBookedSeats(0);
		newMovie.setTotalAvailableSeats(movie.getTotalSeats());
		newMovie.setMovieStatus(MovieStatus.BOOK_ASAP);
		Movie savedMovie = movieRepo.saveAndFlush(newMovie);
		return savedMovie;
		
	}

	
	@Override
	public void deleteMovie(Integer movieId) {
		Movie movie = movieRepo.findById(movieId)
				.orElseThrow(() -> new EntityNotFoundException("Invalid Id Was Provided"));
		movieRepo.delete(movie);
	}

	
	@Override
	public Movie getMovieById(Integer movieId) {
		if(movieId == null)
			throw new IllegalArgumentException("Null Value Found");
		
		return movieRepo.findById(movieId)
				.orElseThrow(() -> new EntityNotFoundException("Invalid Movie Id Was Provided"));
	}

	

	@Override
	public Movie updateMovieSeats(Integer movieId, Integer bookSeats) {
		Movie movie = getMovieById(movieId);
		Integer totalBookedSeats = movie.getTotalBookedSeats()+bookSeats;
		movie.setTotalBookedSeats(totalBookedSeats);
		
		Integer totalAvailableSeats = movie.getTotalSeats()-movie.getTotalBookedSeats();
		movie.setTotalAvailableSeats(totalAvailableSeats);
		
		if(movie.getTotalAvailableSeats()<=0)
			movie.setMovieStatus(MovieStatus.SOLD_OUT);
		
		Movie updatedMovie = movieRepo.save(movie);
		
		return updatedMovie;
	}

}
