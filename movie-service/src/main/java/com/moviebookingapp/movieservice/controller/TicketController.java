package com.moviebookingapp.movieservice.controller;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moviebookingapp.movieservice.model.Movie;
import com.moviebookingapp.movieservice.model.Ticket;
import com.moviebookingapp.movieservice.model.TicketDTO;
import com.moviebookingapp.movieservice.response.ResponseHandler;
import com.moviebookingapp.movieservice.service.MovieService;
import com.moviebookingapp.movieservice.service.ProducerServiceImpl;
import com.moviebookingapp.movieservice.service.TicketService;

@RestController
@RequestMapping("/api/v1.0/moviebooking")
@CrossOrigin(origins = "*")
public class TicketController {

	@Autowired
	private TicketService ticketService;

	@Autowired
	private MovieService movieService;
	
	@Autowired
	private ProducerServiceImpl producer;
	
	
	@RolesAllowed({"ROLE_USER"})
	@PostMapping("/book/{movieId}")
	public ResponseEntity<?> bookTicket(@RequestBody TicketDTO ticket, @PathVariable("movieId") Integer movieId) {
//		Get movie entity by it's id
		Movie movieById = movieService.getMovieById(movieId);

//		validating movie exists in database or not 
		if (movieById != null && ticket.getNumberOfTickets()!=null &&ticket.getNumberOfTickets() <= movieById.getTotalAvailableSeats()) {

//			inserting booking details in ticket table
			Ticket newTransaction = ticketService.addTicket(ticket, movieById);
			
//			updating bookedseats and available seats in movie service
			movieService.updateMovieSeats(movieId, ticket.getNumberOfTickets());

////		validating booking is successfull
			if (newTransaction == null){
				return ResponseHandler.generateResponse("error: tickets not booked", HttpStatus.INTERNAL_SERVER_ERROR,
						null);
			} else {
//				producer sending messages to consumer
				producer.bookTicketMessage("Tickets booked for "+newTransaction.getMovieName()+" = "+newTransaction.getNumberOfTickets());
				return ResponseHandler.generateResponse("success: tickets Booked... message sended to consumer", HttpStatus.CREATED, newTransaction);
			}

		} 
		else {
			throw new IllegalArgumentException("error: seats required are not available!!");
		}

	}

	@RolesAllowed({"ROLE_ADMIN"})
	@GetMapping("/all/tickets/{movieId}")
	public ResponseEntity<?> getTickets(@PathVariable Integer movieId) {
		List<Ticket> listTicket = ticketService.getAllTickets(movieId);
		return ResponseHandler.generateResponse("success: retrived tickets list", HttpStatus.OK, listTicket);
	}
}
