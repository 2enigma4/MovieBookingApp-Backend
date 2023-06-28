package com.moviebookingapp.movieservice.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.ColumnDefault;

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
@Table(name = "movie", uniqueConstraints = @UniqueConstraint(name = "UniqueMovieAndTheater", columnNames = {
		"movie_Name", "theater_Name" }))
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "movie_Id")
	private Integer movieId;

	@Column(name = "movie_Name", nullable = false)
	private String movieName;

	@Column(name = "theater_Name", nullable = false)
	private String theaterName;

	@Column(name = "total_Seats", nullable = false)
	private Integer totalSeats;
	
	@Column(name = "booked_Seats",nullable = false)
	private Integer totalBookedSeats;
	
	@Column(name = "available_Seats",nullable = false)
	private Integer totalAvailableSeats;
	
	@Column(name = "movie_Status",nullable = false)
	private MovieStatus movieStatus;
	
	@OneToMany(targetEntity= Ticket.class, fetch = FetchType.LAZY)
	private List<Ticket> tickets = new ArrayList<>();

	public void addTicketToMovie(Ticket t) {
		this.tickets.add(t);
	}

}
