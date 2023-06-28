package com.moviebookingapp.movieservice.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.moviebookingapp.movieservice.model.Ticket;

@Repository
@Transactional
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

	@Modifying
	@Query(value = "delete from Ticket t where t.movieFk = ?1")
	public void deleteByMovieId(Integer moviId);
	
	@Query(value = "select t from Ticket t where t.movieFk = ?1")
	public	List<Ticket> selectByMovieId(Integer moviId);
}
