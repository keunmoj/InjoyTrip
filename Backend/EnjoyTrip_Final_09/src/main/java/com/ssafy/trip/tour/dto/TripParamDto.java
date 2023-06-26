package com.ssafy.trip.tour.dto;

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
public class TripParamDto {
	// 여행 목록
	private int limit;
	private int offset;
	private String searchWord;
	private int contentTypeId;
	
	// 쿼리 스트링
	private int sidoCode;
	private int gugunCode;
	
	// 여행 상세
	private int contentId;
	private int memberId;
}
