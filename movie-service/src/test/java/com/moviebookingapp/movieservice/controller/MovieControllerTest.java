package com.moviebookingapp.movieservice.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moviebookingapp.movieservice.model.Movie;
import com.moviebookingapp.movieservice.model.MovieDTO;
import com.moviebookingapp.movieservice.model.MovieStatus;
import com.moviebookingapp.movieservice.service.MovieServiceImpl;
import com.moviebookingapp.movieservice.service.TicketServiceImpl;

@SpringBootTest
@AutoConfigureMockMvc
public class MovieControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	private MovieController movieController;

	@Mock
	private MovieServiceImpl movieService;
	
	@Mock
	private TicketServiceImpl ticketService;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(movieController).build();
	}

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
	public void Test_getMovieList_Success() throws Exception {
		List<Movie> movies = new ArrayList<>();
		movies.add(getMovieObject());

		when(movieService.getAllMovies()).thenReturn(movies);
		RequestBuilder request = MockMvcRequestBuilders.get("/api/v1.0/moviebooking/all/movies")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(request).andExpect(status().isOk())
				.andExpect(jsonPath("$.Message").value("success: retrieved all movies"));
	}

	@Test
	public void Test_GetMovieById_Success() throws Exception {
		Movie movie = getMovieObject();
		when(movieService.getMovieById(movie.getMovieId())).thenReturn(movie);

		RequestBuilder request = MockMvcRequestBuilders.get("/api/v1.0/moviebooking/search/1")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(request).andExpect(status().isOk())
				.andExpect(jsonPath("$.Message").value("success: retrived movie by id"));
	}

	@Test
	public void Test_InsertMovie_Success() throws Exception {
		Movie movie = getMovieObject();

		MovieDTO movieDTO = new MovieDTO();
		movieDTO.setMovieName("Titanic");
		movieDTO.setTheaterName("Inox");
		movieDTO.setTotalSeats(120);

		when(movieService.addMovie(Mockito.any(MovieDTO.class))).thenReturn(movie);

		RequestBuilder request = MockMvcRequestBuilders.post("/api/v1.0/moviebooking/admin/addmovie")
				.accept(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(movieDTO))
				.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(request)
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.Message").value("success: movie added"));
	}

	@Test
	public void Test_InsertMovie_Failure() throws Exception {
		MovieDTO movieDTO = new MovieDTO();
		movieDTO.setMovieName("Titanic");
		movieDTO.setTheaterName("Inox");
		movieDTO.setTotalSeats(120);
		when(movieService.addMovie(movieDTO)).thenReturn(null);
		RequestBuilder request = MockMvcRequestBuilders.post("/api/v1.0/moviebooking/admin/addmovie")
				.content(new ObjectMapper().writeValueAsString(movieDTO)).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(request).andExpect(status().isInternalServerError())
				.andExpect(jsonPath("$.Message").value("error: movie not inserted"));
	}

	@Test
	public void Test_RemoveMovie_Success() throws Exception {
		Movie movie = getMovieObject();
		movieService.deleteMovie(movie.getMovieId());
		RequestBuilder request = MockMvcRequestBuilders.delete("/api/v1.0/moviebooking/admin/delete/1")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(request).andExpect(status().isOk())
				.andExpect(jsonPath("$.Message").value("success: movie deleted"))
				.andExpect(jsonPath("$.Payload").value(true));
	}
}
