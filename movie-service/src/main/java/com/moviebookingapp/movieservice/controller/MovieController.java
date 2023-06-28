package com.moviebookingapp.movieservice.controller;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moviebookingapp.movieservice.exceptions.DuplicateEntityFoundException;
import com.moviebookingapp.movieservice.model.Movie;
import com.moviebookingapp.movieservice.model.MovieDTO;
import com.moviebookingapp.movieservice.response.ResponseHandler;
import com.moviebookingapp.movieservice.service.MovieService;
import com.moviebookingapp.movieservice.service.TicketService;

@RestController
@RequestMapping(value = "/api/v1.0/moviebooking")
@CrossOrigin(origins = "*")
public class MovieController {

	@Autowired
	private MovieService movieService;
	
	@Autowired
	private TicketService ticketService;
	
	@RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping("/all/movies")
	public ResponseEntity<?> getMovieList(){
		List<Movie> moviesList = movieService.getAllMovies();
		return ResponseHandler.generateResponse("success: retrieved all movies", HttpStatus.OK, moviesList);
	}
	
	@RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping("/search/{movieId}")
	public ResponseEntity<?> getMovieById(@PathVariable("movieId")Integer movieId){
		Movie movieById = movieService.getMovieById(movieId);
		return ResponseHandler.generateResponse("success: retrived movie by id", HttpStatus.OK, movieById);
	}
	
	@RolesAllowed({"ROLE_ADMIN"})
	@PostMapping("/admin/addmovie")
	public ResponseEntity<?> insertMovie(@RequestBody MovieDTO movie) throws DuplicateEntityFoundException{
		Movie movieInserted = movieService.addMovie(movie);
		if(movieInserted == null)
			return ResponseHandler.generateResponse("error: movie not inserted", HttpStatus.INTERNAL_SERVER_ERROR, null);
		
		return ResponseHandler.generateResponse("success: movie added", HttpStatus.CREATED, movieInserted);
	}
	
	@RolesAllowed({"ROLE_ADMIN"})
	@DeleteMapping("/admin/delete/{movieId}")
	public ResponseEntity<?> removeMovie(@PathVariable("movieId")Integer movieId){
		movieService.deleteMovie(movieId);
		ticketService.deleteTicketsForMovie(movieId);
		return ResponseHandler.generateResponse("success: movie deleted", HttpStatus.OK, Boolean.TRUE);
	}
	
	
}
