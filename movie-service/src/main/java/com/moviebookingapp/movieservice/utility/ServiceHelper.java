package com.moviebookingapp.movieservice.utility;

import java.util.function.Predicate;


import com.moviebookingapp.movieservice.model.MovieDTO;

public class ServiceHelper {
	
	public static Predicate<MovieDTO> minTotalSeat = (movie) -> {
		return movie.getTotalSeats()>=100;
	};
	
	public static Predicate<MovieDTO> maxTotalSeat = (movie) -> {
		return movie.getTotalSeats()<=1000;
	};
	
	
}
