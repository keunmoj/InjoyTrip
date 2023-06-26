package com.ssafy.trip.tour.dto;

import java.time.LocalDateTime;
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
public class CardDto {
	private int cardSeq;
	private String cardThumbnailImgUrl;
	private String cardTitle;
	private String cardComment;
	private LocalDateTime cardCreatedate;

	private int userSeq;
	
	private List<TripDto> list;
}
