package com.ssafy.trip.board.dto;

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
// 얘로 항상 읽어들일 것입니다.
public class BoardParamDto {
	// 목록
	private int limit;
	private int offset;
	private String searchWord;
	
	// 상세
	private int boardId;
	private int userSeq; // 세션에 세팅
}
