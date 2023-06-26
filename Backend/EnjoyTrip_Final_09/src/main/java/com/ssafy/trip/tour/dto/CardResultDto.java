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
public class CardResultDto {
	private CardDto dto;
	private List<CardDto> list;
	private List<TripDto> tripList;
	private int result;
	private int count;
}
