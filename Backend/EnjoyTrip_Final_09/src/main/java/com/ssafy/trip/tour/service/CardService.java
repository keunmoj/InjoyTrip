package com.ssafy.trip.tour.service;

import com.ssafy.trip.tour.dto.CardDto;
import com.ssafy.trip.tour.dto.CardResultDto;

public interface CardService {
	CardResultDto cardInsert(CardDto cardDto);

	CardResultDto cardList(int userSeq);

	CardResultDto cardDetail(CardDto cardDto);

	CardResultDto cardDelete(CardDto cardDto);

}
