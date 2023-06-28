package com.moviebookingapp.movieservice.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MovieTest {

	Movie movie = new Movie();

	@Test
	public void Test_getMovieId() {
		movie.setMovieId(1);
		assertEquals(1, movie.getMovieId());
	}

	@Test
	public void Test_MovieName() {
		movie.setMovieName("Titanic");
		assertEquals("Titanic", movie.getMovieName());
	}

	@Test
	public void Test_TheaterName() {
		movie.setTheaterName("Inox");
		assertEquals("Inox", movie.getTheaterName());
	}

	@Test
	public void Test_TotalSeats() {
		movie.setTotalSeats(100);
		assertEquals(100, movie.getTotalSeats());
	}

	@Test
	public void Test_TotalBookedSeats() {
		movie.setTotalBookedSeats(0);
		assertEquals(0, movie.getTotalBookedSeats());
	}

	@Test
	public void Test_TotalAvailableSeats() {
		movie.setTotalAvailableSeats(100);
		assertEquals(100, movie.getTotalAvailableSeats());
	}

	@Test
	public void Test_Tickets() {
		List<Ticket> list = new ArrayList<>();
		movie.setTickets(list);
		assertEquals(list, movie.getTickets());
	}
}
