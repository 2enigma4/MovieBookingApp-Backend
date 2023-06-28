package com.moviebookingapp.movieservice.model;

import java.util.Objects;
import java.util.stream.Stream;

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
public class MovieDTO {

	private String movieName;
	private String theaterName;
	private Integer totalSeats;
	
	public boolean checkNull() {
		return Stream.of(movieName,theaterName,totalSeats)
				.anyMatch(Objects::isNull);
	}
}
