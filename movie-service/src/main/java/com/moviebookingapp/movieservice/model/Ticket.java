package com.moviebookingapp.movieservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer transactionId;
	
	@Column(name = "movie_Id", nullable = false)
	private Integer movieFk;
	
	@Column(name = "movie_Name", nullable = false)
	private String movieName;

	@Column(name = "theater_Name", nullable = false)
	private String theaterName;

	@Column(name = "total_Seats", nullable = false)
	private Integer totalTickets;
		
	@Column(name = "book_tickets", nullable = false)
	private Integer numberOfTickets;
	
	@Column(name = "available_Tickets", nullable = false)
	private Integer availableTickets;
	
}
