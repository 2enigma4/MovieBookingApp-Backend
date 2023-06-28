package com.moviebookingapp.movieservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.moviebookingapp.movieservice.exceptions.DuplicateEntityFoundException;
import com.moviebookingapp.movieservice.model.Movie;
import com.moviebookingapp.movieservice.model.MovieDTO;
import com.moviebookingapp.movieservice.model.MovieStatus;
import com.moviebookingapp.movieservice.repository.MovieRepository;

@SpringBootTest
public class MovieServiceImplTest {

	@InjectMocks
	private MovieServiceImpl movieService;

	@Mock
	private MovieRepository movieRepo;

	public Movie getMovieObject() {
		Movie movie = new Movie();
		movie.setMovieId(1);
		movie.setMovieName("Titanic");
		movie.setTheaterName("Inox");
		movie.setTotalSeats(120);
		movie.setTotalBookedSeats(0);
		movie.setTotalAvailableSeats(120);
		movie.setMovieStatus(MovieStatus.BOOK_ASAP);
		movie.setTickets(new ArrayList<>());
		return movie;
	}

	@Test
	public void Test_GetAllMovies_Success() {

		List<Movie> expected = new ArrayList<Movie>();
		expected.add(getMovieObject());

		when(movieRepo.findAll()).thenReturn(expected);

		List<Movie> actual = movieService.getAllMovies();
		assertEquals(expected, actual);
		verify(movieRepo).findAll();
	}

	@Test
	public void Test_GetAllMovies_Empty() {
		when(movieRepo.findAll()).thenReturn(new ArrayList());
		assertThrows(EntityNotFoundException.class, () -> movieService.getAllMovies());
	}

	@Test
	public void Test_GetMovieById_Success() {

		Movie movie = getMovieObject();
		when(movieRepo.findById(movie.getMovieId())).thenReturn(Optional.of(movie));

		Movie actualResult = movieService.getMovieById(movie.getMovieId());

		assertEquals(movie, actualResult);
		verify(movieRepo, times(1)).findById(movie.getMovieId());
	}

	@Test
	public void Test_GetMovieById_Invalid_Id() {
		when(movieRepo.findById(2)).thenReturn(Optional.empty());
		assertThrows(EntityNotFoundException.class, () -> movieService.getMovieById(2));
		verify(movieRepo, times(1)).findById(2);
	}

	@Test
	public void Test_GetMovieById_Null_Id() {
		assertThrows(IllegalArgumentException.class, () -> movieService.getMovieById(null));
		verify(movieRepo, times(0)).findById(null);
	}

	@Test
	public void Test_addMovie_Success() throws DuplicateEntityFoundException {
		Movie movieOutput = getMovieObject();

		Movie movieInput = new Movie();
		movieInput.setMovieName("Titanic");
		movieInput.setTheaterName("Inox");
		movieInput.setTotalSeats(120);
		movieInput.setTotalBookedSeats(0);
		movieInput.setTotalAvailableSeats(120);
		movieInput.setMovieStatus(MovieStatus.BOOK_ASAP);

		MovieDTO movieDTO = new MovieDTO();
		movieDTO.setMovieName("Titanic");
		movieDTO.setTheaterName("Inox");
		movieDTO.setTotalSeats(120);

		when(movieRepo.getMovieByNameAndTheater(movieDTO.getMovieName(), movieDTO.getTheaterName())).thenReturn(null);

		when(movieRepo.saveAndFlush(Mockito.any(Movie.class))).thenReturn(movieOutput);

		Movie actualResult = movieService.addMovie(movieDTO);
		assertEquals(movieOutput, actualResult);
		verify(movieRepo, times(1)).saveAndFlush(Mockito.any(Movie.class));
		verify(movieRepo, times(1)).getMovieByNameAndTheater(movieDTO.getMovieName(), movieDTO.getTheaterName());
	}
	
	@Test
	public void Test_addMovie_Failure() throws DuplicateEntityFoundException {

		MovieDTO movieDTO = new MovieDTO();
		movieDTO.setMovieName("Titanic");
		movieDTO.setTheaterName("Inox");
		movieDTO.setTotalSeats(120);

		when(movieRepo.getMovieByNameAndTheater(movieDTO.getMovieName(), movieDTO.getTheaterName())).thenReturn(null);

		when(movieRepo.saveAndFlush(Mockito.any(Movie.class))).thenReturn(null);

		assertNull(movieService.addMovie(movieDTO));
		verify(movieRepo, times(1)).saveAndFlush(Mockito.any(Movie.class));
	}

	@Test
	public void Test_DeleteMovie_Success() {
		Movie movie = getMovieObject();
		when(movieRepo.findById(movie.getMovieId())).thenReturn(Optional.of(movie));
		movieService.deleteMovie(movie.getMovieId());
		verify(movieRepo, times(1)).delete(movie);
	}
	
	@Test
	public void Test_DeleteMovie_Failure() {
		Movie movie = getMovieObject();
		when(movieRepo.findById(movie.getMovieId())).thenReturn(Optional.ofNullable(null));
		assertThrows(EntityNotFoundException.class, () -> movieService.deleteMovie(movie.getMovieId()));
		verify(movieRepo, times(0)).delete(movie);
	}
	
	@Test
	public void Test_UpdateMovieSeats_Success() {
		Movie movie = getMovieObject();
		
		Movie updatedMovie = new Movie();
		updatedMovie.setMovieId(1);
		updatedMovie.setMovieName("Titanic");
		updatedMovie.setTheaterName("Inox");
		updatedMovie.setTotalSeats(120);
		updatedMovie.setMovieStatus(MovieStatus.BOOK_ASAP);
		updatedMovie.setTotalBookedSeats(10);
		updatedMovie.setTotalAvailableSeats(110);
		
		when(movieRepo.findById(movie.getMovieId())).thenReturn(Optional.of(movie));
		when(movieRepo.save(movie)).thenReturn(updatedMovie);
		
		Movie actualResult = movieService.updateMovieSeats(movie.getMovieId(), 10);
	
		assertEquals(updatedMovie,actualResult);
		verify(movieRepo, times(1)).findById(1);
		verify(movieRepo, times(1)).save(movie);
	}

}
