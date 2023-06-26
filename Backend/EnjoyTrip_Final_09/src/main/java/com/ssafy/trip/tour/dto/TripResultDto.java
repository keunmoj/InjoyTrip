package com.ssafy.trip.tour.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TripResultDto {
	private TripDto dto;
	private List<TripDto> list;
	private int result;
	private int count;
}
