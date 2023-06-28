package com.moviebookingapp.movieservice.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TicketTest {

	Ticket ticket = new Ticket();
	
	@Test
	public void Test_TicketId() {
		ticket.setTransactionId(101);
		assertEquals(101,ticket.getTransactionId());
	}
	
	
	@Test
	public void Test_MovieName() {
		ticket.setMovieName("Titanic");;
		assertEquals("Titanic",ticket.getMovieName());
	}
	
	@Test
	public void Test_TheaterName() {
		ticket.setTheaterName("Inox");
		assertEquals("Inox",ticket.getTheaterName());
	}
	
	@Test
	public void Test_MovieFk() {
		ticket.setMovieFk(1);
		assertEquals(1,ticket.getMovieFk());
	}
	
	@Test
	public void Test_TotalTickets() {
		ticket.setTotalTickets(100);
		assertEquals(100,ticket.getTotalTickets());
	}
	
	@Test
	public void Test_NumberOfTickets() {
		ticket.setNumberOfTickets(10);
		assertEquals(10,ticket.getNumberOfTickets());
	}
	
	@Test
	public void Test_TotalAvailableTickets() {
		ticket.setAvailableTickets(90);
		assertEquals(90,ticket.getAvailableTickets());
	}
	
}
