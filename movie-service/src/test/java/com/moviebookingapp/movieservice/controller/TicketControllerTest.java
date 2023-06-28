package com.moviebookingapp.movieservice.controller;

import static org.mockito.Mockito.when;
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
import com.moviebookingapp.movieservice.model.MovieStatus;
import com.moviebookingapp.movieservice.model.Ticket;
import com.moviebookingapp.movieservice.model.TicketDTO;
import com.moviebookingapp.movieservice.service.MovieServiceImpl;
import com.moviebookingapp.movieservice.service.ProducerServiceImpl;
import com.moviebookingapp.movieservice.service.TicketServiceImpl;

@SpringBootTest
@AutoConfigureMockMvc
public class TicketControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	private TicketController ticketController;

	@Mock
	private TicketServiceImpl ticketService;
	
	@Mock
	private MovieServiceImpl movieService;
	
	@Mock
	private ProducerServiceImpl producer;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(ticketController).build();
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
	
	public Ticket getTicketObject() {
		Ticket newTicket = new Ticket();
		newTicket.setMovieFk(1);
		newTicket.setMovieName("Titanic");
		newTicket.setTheaterName("Inox");
		newTicket.setTotalTickets(120);
		newTicket.setNumberOfTickets(10);
		newTicket.setAvailableTickets(110);
		return newTicket;
	}
	
	@Test
	public void Test_BookTicket_Success() throws Exception {
		Movie movie = getMovieObject();
		
		Ticket ticket = getTicketObject();
		
		TicketDTO ticketDTO = new TicketDTO();
		ticketDTO.setNumberOfTickets(10);
		
		when(movieService.getMovieById(movie.getMovieId())).thenReturn(movie);
		when(ticketService.addTicket(Mockito.any(TicketDTO.class), Mockito.any(Movie.class))).thenReturn(ticket);
		
		RequestBuilder request = MockMvcRequestBuilders.post("/api/v1.0/moviebooking/book/1")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(ticketDTO));

		mockMvc.perform(request)
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.Message").value("success: tickets Booked... message sended to consumer"));
	
	}
	
	@Test
	public void Test_GetTickets_Success() throws Exception {
		List<Ticket> list = new ArrayList<>();
		list.add(getTicketObject());
		
		when(ticketService.getAllTickets(1)).thenReturn(list);
		
		RequestBuilder request = MockMvcRequestBuilders.get("/api/v1.0/moviebooking/all/tickets/1")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(request)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.Message").value("success: retrived tickets list"));
		
	}
	
}

