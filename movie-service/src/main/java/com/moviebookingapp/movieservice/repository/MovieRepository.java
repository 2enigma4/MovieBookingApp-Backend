package com.moviebookingapp.movieservice.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.moviebookingapp.movieservice.model.Movie;

@Repository
@Transactional
public interface MovieRepository extends JpaRepository<Movie, Integer>{

	@Query(value = "select u from Movie u where u.movieName = ?1 and u.theaterName = ?2")
	public Movie getMovieByNameAndTheater(String name, String theater);
}
