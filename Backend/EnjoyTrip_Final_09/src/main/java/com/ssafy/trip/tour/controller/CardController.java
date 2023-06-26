package com.ssafy.trip.tour.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trip.board.dto.BoardResultDto;
import com.ssafy.trip.tour.dto.CardDto;
import com.ssafy.trip.tour.dto.CardResultDto;
import com.ssafy.trip.tour.service.CardService;
import com.ssafy.trip.user.dto.UserDto;

// 관광타입(12:관광지, 14:문화시설, 15:축제공연행사, 25:여행코스, 28:레포츠, 32:숙박, 38:쇼핑, 39:음식점)

@RestController
@CrossOrigin(origins = "http://localhost:5500", allowCredentials = "true", allowedHeaders = "*", methods = {
		RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS,
		RequestMethod.HEAD })
public class CardController {

	@Autowired
	CardService service;

	private static final int SUCCESS = 1;

	@PostMapping(value = "/cards")
	public ResponseEntity<CardResultDto> cardInsert(@RequestBody CardDto cardDto, HttpSession session) {
		CardResultDto cardResultDto;
//		CardDto cardDto = new CardDto();
		UserDto userDto = (UserDto) session.getAttribute("userDto");
		System.out.println(userDto.getUserSeq());
		cardDto.setUserSeq(userDto.getUserSeq());
//		cardDto.setList(list);
		System.out.println("here is cardinsert");
		System.out.println(cardDto);
		cardResultDto = service.cardInsert(cardDto);

		if (cardResultDto.getResult() == SUCCESS) {
			return ResponseEntity.ok().body(cardResultDto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// ====================== 마이페이지 카드 리스트 =================================
	@GetMapping(value = "/cards")
	public ResponseEntity<CardResultDto> cardList(@RequestParam int userSeq) {
		CardResultDto cardResultDto;

		System.out.println("카드리스트 : " + userSeq);

		cardResultDto = service.cardList(userSeq);
		if (cardResultDto.getResult() == SUCCESS) {
			return ResponseEntity.ok().body(cardResultDto);
		} else {
			return ResponseEntity.notFound().build();

		}
	}

	@GetMapping(value = "/card")
	public ResponseEntity<CardResultDto> cardDetail(CardDto cardDto) {
		CardResultDto cardResultDto;

		System.out.println("카드리스트 : " + cardDto);

		cardResultDto = service.cardDetail(cardDto);
		if (cardResultDto.getResult() == SUCCESS) {
			return ResponseEntity.ok().body(cardResultDto);
		} else {
			return ResponseEntity.notFound().build();

		}
	}
	
	@DeleteMapping(value="/card/{cardSeq}") 
	public ResponseEntity<CardResultDto> cardDelete(@PathVariable(value="cardSeq") int cardSeq){
		CardResultDto cardResultDto;
		CardDto cardDto=new CardDto();
		cardDto.setCardSeq(cardSeq);
		cardResultDto = service.cardDelete(cardDto);
		
		if( cardResultDto.getResult() == SUCCESS ) {
			return ResponseEntity.ok().body(cardResultDto);
		}else {
			return ResponseEntity.notFound().build();
		}		 
	}

}
