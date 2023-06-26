package com.ssafy.trip.tour.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.trip.tour.dto.CardContentDto;
import com.ssafy.trip.tour.dto.CardDto;
import com.ssafy.trip.tour.dto.TripDto;

@Mapper
public interface CardDao {
	
	int cardInsert(CardDto cardDto);
	int cardContentInsert(List<CardContentDto> list);
	List<CardDto> cardList(int userSeq);
	List<TripDto> cardDetail(CardDto cardDto);
	int cardDelete(CardDto cardDto);
	
	
}
