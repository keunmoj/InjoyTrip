package com.ssafy.trip.board.service;


// service layer 가 코드가 가장 복잡하고 코드의 양이 많다
// dao 는 join 이 어려운 것

// 첨부파일 처리 request
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ssafy.trip.board.dto.BoardDto;
import com.ssafy.trip.board.dto.BoardParamDto;
import com.ssafy.trip.board.dto.BoardResultDto;

public interface BoardService {

	BoardResultDto boardDetail(BoardParamDto boardParamDto);
	BoardResultDto boardDelete(int boardId);
	BoardResultDto boardInsert(BoardDto dto, MultipartHttpServletRequest request);
	BoardResultDto boardUpdate(BoardDto dto, MultipartHttpServletRequest request);
	
	BoardResultDto boardList(BoardParamDto dto);
	BoardResultDto boardListSearchWord(BoardParamDto dto);
	
	

	
}
