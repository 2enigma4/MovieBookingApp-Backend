package com.moviebookingapp.movieservice.service;

import java.util.List;

import com.moviebookingapp.movieservice.model.Movie;
import com.moviebookingapp.movieservice.model.Ticket;
import com.moviebookingapp.movieservice.model.TicketDTO;

public interface TicketService {

	public Ticket addTicket(TicketDTO ticket, Movie movie);
	
	public List<Ticket> getAllTickets(Integer MovieId);
	
	public void deleteTicketsForMovie(Integer movieId);
}
