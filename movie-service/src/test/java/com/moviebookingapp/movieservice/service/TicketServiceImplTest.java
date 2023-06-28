package com.moviebookingapp.movieservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.moviebookingapp.movieservice.model.Movie;
import com.moviebookingapp.movieservice.model.MovieStatus;
import com.moviebookingapp.movieservice.model.Ticket;
import com.moviebookingapp.movieservice.model.TicketDTO;
import com.moviebookingapp.movieservice.repository.TicketRepository;

@SpringBootTest
public class TicketServiceImplTest {

	@InjectMocks
	private TicketServiceImpl ticketService;

	@Mock
	private TicketRepository ticketRepo;

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
		newTicket.setNumberOfTickets(5);
		newTicket.setAvailableTickets(115);
		return newTicket;
	}

	@Test
	public void Test_addTicket_Success() {
		Ticket ticket = getTicketObject();
		Movie movie = getMovieObject();
		TicketDTO dto = new TicketDTO();
		dto.setNumberOfTickets(5);
		when(ticketRepo.saveAndFlush(Mockito.any(Ticket.class))).thenReturn(ticket);
		Ticket actualResult = ticketService.addTicket(dto, movie);
		assertEquals(ticket, actualResult);
		verify(ticketRepo, times(1)).saveAndFlush(Mockito.any(Ticket.class));
	}

	@Test
	public void Test_addTicket_Throws_IllegalArgumentException_Booking_Null() {
		Ticket ticket = getTicketObject();
		Movie movie = getMovieObject();
		TicketDTO dto = new TicketDTO();
		dto.setNumberOfTickets(null);
		when(ticketRepo.saveAndFlush(Mockito.any(Ticket.class))).thenReturn(ticket);
		assertThrows(IllegalArgumentException.class, () -> ticketService.addTicket(dto, movie));
		verify(ticketRepo, times(0)).saveAndFlush(Mockito.any(Ticket.class));
	}

	@Test
	public void Test_addTicket_Throws_IllegalArgumentException_Booking_Zero() {
		Ticket ticket = getTicketObject();
		Movie movie = getMovieObject();
		TicketDTO dto = new TicketDTO();
		dto.setNumberOfTickets(0);
		when(ticketRepo.saveAndFlush(Mockito.any(Ticket.class))).thenReturn(ticket);
		assertThrows(IllegalArgumentException.class, () -> ticketService.addTicket(dto, movie));
		verify(ticketRepo, times(0)).saveAndFlush(Mockito.any(Ticket.class));
	}

	@Test
	public void Test_GetALLTickets_Success() {
		List<Ticket> allTickets = new ArrayList<>();
		allTickets.add(getTicketObject());
		when(ticketRepo.selectByMovieId(1)).thenReturn(allTickets);
		List<Ticket> actualResult = ticketService.getAllTickets(1);
		assertEquals(allTickets, actualResult);
		verify(ticketRepo, times(1)).selectByMovieId(1);
	}
	
	@Test
	public void Test_GetALLTickets_Throws_EntityNotFoundException_ListEmpty() {
		List<Ticket> allTickets = new ArrayList<>();
		when(ticketRepo.selectByMovieId(1)).thenReturn(allTickets);
		assertThrows(EntityNotFoundException.class, ()-> ticketService.getAllTickets(1));
		verify(ticketRepo, times(1)).selectByMovieId(1);
	}

}
