package com.ssafy.trip.board.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.trip.board.dto.BoardDto;
import com.ssafy.trip.board.dto.BoardFileDto;
import com.ssafy.trip.board.dto.BoardParamDto;

@Mapper
public interface BoardDao {

	// 상세
	BoardDto boardDetail(BoardParamDto boardParamDto);

	List<BoardFileDto> boardDetailFileList(int boardId);

	// 게시글을 어떤 사용자가 조회했었는지 확인
	int boardUserReadCount(BoardParamDto boardParamDto); // mapper 에서 map 파라미터

	// 신규로 게시글 조회 history 등록
	int boardUserReadInsert(@Param("boardId") int boardId, @Param("userSeq") int userSeq); // mapper 에서 map 파라미터
	int boardReadCountUpdate(int boardId); // 게시글 조회수 증가

	////// 삭제
	int boardDelete(int boardId);
	int boardFileDelete(int boardId);
	int boardReadCountDelete(int boardId);
	List<String> boardFileUrlDeleteList(int boardId); // 첨부파일의 물리적인 저장 url 목록

	////// 등록
	int boardInsert(BoardDto dto);
	int boardFileInsert(BoardFileDto dto);

	////// 목록
	List<BoardDto> boardList(BoardParamDto boardParamDto);
	int boardListTotalCount(); // 총 건수

	////// 목록 - 검색어
	List<BoardDto> boardListSearchWord(BoardParamDto boardParamDto);
	int boardListSearchWordTotalCount(BoardParamDto boardParamDto); // 검색어 총 건수
	
	////// 수정
	int boardUpdate(BoardDto dto);
	// 첨부파일은 ?? 단순한 UI 인 경우는 = Delete 후 Insert, 개별 첨부파일 처리는 복잡한 UI(파일별 삭제버튼)
	// 하나씩 바꿀거면 각각의 파일에 버튼을 추가해서 하나씩 지워지게끔 해야함

}
