package com.ssafy.trip.tour.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.trip.tour.dao.CardDao;
import com.ssafy.trip.tour.dto.CardContentDto;
import com.ssafy.trip.tour.dto.CardDto;
import com.ssafy.trip.tour.dto.CardResultDto;
import com.ssafy.trip.tour.dto.TripDto;

@Service
public class CardServiceImpl implements CardService {

	@Autowired
	CardDao dao;

	private static final int SUCCESS = 1;
	private static final int FAIL = -1;

	@Override
	@Transactional
	public CardResultDto cardInsert(CardDto cardDto) {
		CardResultDto cardResultDto = new CardResultDto();
		
		System.out.println("카드 삽입 수행");
		try {
			int result1 = dao.cardInsert(cardDto);
			List<TripDto> tripList = cardDto.getList();
			List<CardContentDto> cardContentList = new ArrayList<>();
			for (TripDto tripDto : tripList) {
				cardContentList.add(new CardContentDto(cardDto.getCardSeq(),tripDto.getContentId()));
			}
			
			for (CardContentDto cardContentDto : cardContentList) {
				System.out.println(cardDto.getCardSeq());
				System.out.println(cardContentDto);
			}
			
			int result2 = dao.cardContentInsert(cardContentList);
			
			
			if(result1 ==1&&result2>0) {
				System.out.println("수행완료");
				cardResultDto.setResult(SUCCESS);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			cardResultDto.setResult(FAIL);
		}
		
		return cardResultDto;
	}
	
	@Override
	public CardResultDto cardList(int userSeq) {
		CardResultDto cardResultDto = new CardResultDto();
		System.out.println("카드 검색 수행");
		try {
			List<CardDto> list = dao.cardList(userSeq);
			System.out.println("수행완료");
			cardResultDto.setList(list);
			cardResultDto.setResult(SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			cardResultDto.setResult(FAIL);
		}

		return cardResultDto;
	}

	@Override
	public CardResultDto cardDetail(CardDto cardDto) {
		CardResultDto cardResultDto = new CardResultDto();
		try {
			List<TripDto> list = dao.cardDetail(cardDto);
			System.out.println("카드 상세 수행");
			cardResultDto.setTripList(list);
			cardResultDto.setResult(SUCCESS);

		} catch (Exception e) {
			e.printStackTrace();
			cardResultDto.setResult(FAIL);
		}

		return cardResultDto;
	}

	@Override
	public CardResultDto cardDelete(CardDto cardDto) {
		CardResultDto cardResultDto = new CardResultDto();
		System.out.println("카드 삭제 수행");
		try {
			int result = dao.cardDelete(cardDto);
			System.out.println("here");
			if (result == 1) {
				cardResultDto.setResult(SUCCESS);
			}

		} catch (Exception e) {
			e.printStackTrace();
			cardResultDto.setResult(FAIL);
		}

		return cardResultDto;
	}



}
