package com.moviebookingapp.movieservice.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.moviebookingapp.movieservice.model.Movie;
import com.moviebookingapp.movieservice.model.MovieStatus;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class MovieRepositoryTest {

	@Autowired
	private MovieRepository movieRepo;
	
	private Movie movie = new Movie();
	
	@BeforeEach
	public void init()
	{
		movie.setMovieId(201);
		movie.setMovieName("Kerala");
		movie.setTheaterName("Fun");
		movie.setTotalSeats(120);
		movie.setTotalBookedSeats(0);
		movie.setTotalAvailableSeats(120);
		movie.setMovieStatus(MovieStatus.BOOK_ASAP);
	}
	
//	@Test
//	public void getMovie() {	
//			
//		movieRepo.save(movie);
//		Optional<Movie> movie1 = movieRepo.findById(movie.getMovieId());
//		assertEquals(movie1.get().getMovieName(), movie.getMovieName());
//	}
	
	
}
