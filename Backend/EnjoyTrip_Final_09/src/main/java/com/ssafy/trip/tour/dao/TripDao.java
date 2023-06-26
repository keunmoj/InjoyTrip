package com.ssafy.trip.tour.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.trip.tour.dto.GugunDto;
import com.ssafy.trip.tour.dto.SidoDto;
import com.ssafy.trip.tour.dto.TripDto;
import com.ssafy.trip.tour.dto.TripParamDto;

@Mapper
public interface TripDao {
	// 시구군 리스트
	List<SidoDto> sidoList();
	List<GugunDto> gugunList(GugunDto gugunDto);
	
	//=================== 여행지
	// 여행 목록
	List<TripDto> tripList(TripParamDto tripParamDto);
	int tripListTotalCount(TripParamDto tripParamDto);
	// 여행 검색 리스트
	List<TripDto> tripListSearchWord(TripParamDto tripParamDto);
	int tripListSearchWordTotalCount(TripParamDto tripParamDto);
	
	// 여행 목록 시도
	List<TripDto> tripListSido(TripParamDto tripParamDto);
	int tripListSidoTotalCount(TripParamDto tripParamDto);
	// 여행 목록 시도/군구
	List<TripDto> tripListSidoGugun(TripParamDto tripParamDto);
	int tripListSidoGugunTotalCount(TripParamDto tripParamDto);
	// 여행 검색 리스트
	List<TripDto> tripListSearchWordSidoGugun(TripParamDto tripParamDto);
	int tripListSearchWordSidoGugunTotalCount(TripParamDto tripParamDto);
	// 여행 검색 리스트 (시 만 선택)
	List<TripDto> tripListSearchWordSido(TripParamDto tripParamDto);
	int tripListSearchWordSidoTotalCount(TripParamDto tripParamDto);

	TripDto tripDetail(TripParamDto tripParamDto);
	
	//==================== 콘텐츠 ( 음식점 -39 숙박시설 -32 액티비티 -28 )
	List<TripDto> contentList(TripParamDto tripParamDto);
	int contentListTotalCount(TripParamDto tripParamDto);

	// 콘텐츠 목록 시도
	List<TripDto> contentListSido(TripParamDto tripParamDto);
	int contentListSidoTotalCount(TripParamDto tripParamDto);
	// 콘텐츠 목록 시도/군구
	List<TripDto> contentListSidoGugun(TripParamDto tripParamDto);
	int contentListSidoGugunTotalCount(TripParamDto tripParamDto);
	// 콘텐츠 상세
	TripDto contentDetail(TripParamDto tripParamDto);

	
}
