package com.moviebookingapp.movieservice.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moviebookingapp.movieservice.model.Movie;
import com.moviebookingapp.movieservice.model.Ticket;
import com.moviebookingapp.movieservice.model.TicketDTO;
import com.moviebookingapp.movieservice.repository.TicketRepository;

@Service
public class TicketServiceImpl implements TicketService {
	
	
	@Autowired
	private TicketRepository ticketRepo;

	@Override
	public Ticket addTicket(TicketDTO ticket, Movie movie) {
		if(ticket.getNumberOfTickets()!= null && ticket.getNumberOfTickets()>0) {
			Ticket newTicket = new Ticket();
			newTicket.setMovieFk(movie.getMovieId());
			newTicket.setMovieName(movie.getMovieName());
			newTicket.setTheaterName(movie.getTheaterName());
			newTicket.setTotalTickets(movie.getTotalSeats());
			newTicket.setNumberOfTickets(ticket.getNumberOfTickets());
			newTicket.setAvailableTickets(movie.getTotalAvailableSeats()-ticket.getNumberOfTickets());
			
//			adding ticket to target movie list
			movie.addTicketToMovie(newTicket);
			
			Ticket savedTransaction = ticketRepo.saveAndFlush(newTicket);
			return savedTransaction;
		}
		else {
			throw new IllegalArgumentException("Illegal Value Found For Number Of Tickets To Be Booked");
		}
	}

	@Override
	public List<Ticket> getAllTickets(Integer movieId) {
		List<Ticket> ticketList = ticketRepo.selectByMovieId(movieId);;
		if(ticketList.isEmpty())
			throw new EntityNotFoundException("No Transaction Details Are Available");
		return ticketList;
	}

	@Override
	public void deleteTicketsForMovie(Integer movieId) {
		List<Ticket> allTicketsById = ticketRepo.findAll().stream()
				.filter((ticket)-> ticket.getMovieFk()==movieId)
				.collect(Collectors.toList());
		
		if(!allTicketsById.isEmpty())
			ticketRepo.deleteByMovieId(movieId);
	}

}
