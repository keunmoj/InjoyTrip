package com.ssafy.trip.tour.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trip.tour.dto.GugunDto;
import com.ssafy.trip.tour.dto.SidoDto;
import com.ssafy.trip.tour.dto.TripParamDto;
import com.ssafy.trip.tour.dto.TripResultDto;
import com.ssafy.trip.tour.service.TripService;

// 관광타입(12:관광지, 14:문화시설, 15:축제공연행사, 25:여행코스, 28:레포츠, 32:숙박, 38:쇼핑, 39:음식점)

@RestController
@CrossOrigin(origins = "http://localhost:5500", allowCredentials = "true", allowedHeaders = "*", methods = {
		RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS,
		RequestMethod.HEAD })
public class TripController {

	@Autowired
	TripService service;

	private static final int SUCCESS = 1;

	// ====================== 시도 구군 API=================================
	@GetMapping(value = "/sido")
	public List<SidoDto> sidoList() {

		List<SidoDto> list = service.sidoList();
		System.out.println("Sido list");
		return list;

	}

	@GetMapping(value = "/gugun")
	public List<GugunDto> GugunList(GugunDto gugunDto) {

		List<GugunDto> list = service.gugunList(gugunDto);

		return list;

	}

	// ====================== 여행 관광지 API=================================
	// 여행 목록 + 시구군 선택
	@GetMapping(value = "/trips")
	public ResponseEntity<TripResultDto> tripList(TripParamDto tripParamDto) {
		TripResultDto tripResultDto;

		System.out.println("리스트 : " + tripParamDto);
		if (tripParamDto.getSidoCode() != 0 && tripParamDto.getGugunCode() != 0) {
			System.out.println("시구군 선택 로직 실행");
			if (tripParamDto.getSearchWord().isEmpty()) {
				tripResultDto = service.tripListSidoGugun(tripParamDto);
			} else {
				System.out.println("검색 기능 유효 : " + tripParamDto);
				tripResultDto = service.tripListSearchWordSidoGugun(tripParamDto);
			}

			if (tripResultDto.getResult() == SUCCESS) {
				return ResponseEntity.ok().body(tripResultDto);
			} else {
				return ResponseEntity.notFound().build();
			}
		} else if (tripParamDto.getSidoCode() != 0 && tripParamDto.getGugunCode() == 0) {
			System.out.println("시도 선택 로직 실행");
			if (tripParamDto.getSearchWord().isEmpty()) {
				tripResultDto = service.tripListSido(tripParamDto);
			} else {
				System.out.println("검색 기능 유효 : " + tripParamDto);
				tripResultDto = service.tripListSearchWordSido(tripParamDto);
			}

			if (tripResultDto.getResult() == SUCCESS) {
				return ResponseEntity.ok().body(tripResultDto);
			} else {
				return ResponseEntity.notFound().build();
			}
		} else {
			if (tripParamDto.getSearchWord().isEmpty()) {
				tripResultDto = service.tripList(tripParamDto);
			} else {
				System.out.println("검색 기능 유효 : " + tripParamDto);
				tripResultDto = service.tripListSearchWord(tripParamDto);
			}

			if (tripResultDto.getResult() == SUCCESS) {
				return ResponseEntity.ok().body(tripResultDto);
			} else {
				return ResponseEntity.notFound().build();
			}
		}
	}

	// ====================== 음식점 API=================================
	@GetMapping(value = "/foods")
	public ResponseEntity<TripResultDto> foodList(TripParamDto tripParamDto) {
		TripResultDto tripResultDto;

		System.out.println("음식리스트 : " + tripParamDto);
		if (tripParamDto.getSidoCode() != 0 && tripParamDto.getGugunCode() != 0) {
			System.out.println("시구군 선택 로직 실행");

			tripResultDto = service.contentListSidoGugun(tripParamDto);

			if (tripResultDto.getResult() == SUCCESS) {
				return ResponseEntity.ok().body(tripResultDto);
			} else {
				return ResponseEntity.notFound().build();
			}
		} else if (tripParamDto.getSidoCode() != 0 && tripParamDto.getGugunCode() == 0) {
			System.out.println("시도 선택 로직 실행");

			tripResultDto = service.contentListSido(tripParamDto);

			if (tripResultDto.getResult() == SUCCESS) {
				return ResponseEntity.ok().body(tripResultDto);
			} else {
				return ResponseEntity.notFound().build();
			}
		} else {

			tripResultDto = service.contentList(tripParamDto);

			if (tripResultDto.getResult() == SUCCESS) {
				return ResponseEntity.ok().body(tripResultDto);
			} else {
				return ResponseEntity.notFound().build();
			}
		}
	}
	// ====================== 숙박시설 API=================================
	@GetMapping(value = "/lodgements")
	public ResponseEntity<TripResultDto> lodgementList(TripParamDto tripParamDto) {
		TripResultDto tripResultDto;

		System.out.println("숙박시설리스트 : " + tripParamDto);
		if (tripParamDto.getSidoCode() != 0 && tripParamDto.getGugunCode() != 0) {
			System.out.println("시구군 선택 로직 실행");

			tripResultDto = service.contentListSidoGugun(tripParamDto);

			if (tripResultDto.getResult() == SUCCESS) {
				return ResponseEntity.ok().body(tripResultDto);
			} else {
				return ResponseEntity.notFound().build();
			}
		} else if (tripParamDto.getSidoCode() != 0 && tripParamDto.getGugunCode() == 0) {
			System.out.println("시도 선택 로직 실행");

			tripResultDto = service.contentListSido(tripParamDto);

			if (tripResultDto.getResult() == SUCCESS) {
				return ResponseEntity.ok().body(tripResultDto);
			} else {
				return ResponseEntity.notFound().build();
			}
		} else {

			tripResultDto = service.contentList(tripParamDto);

			if (tripResultDto.getResult() == SUCCESS) {
				return ResponseEntity.ok().body(tripResultDto);
			} else {
				return ResponseEntity.notFound().build();
			}
		}
	}	
	
	// ====================== 액티비티 API=================================
	@GetMapping(value = "/activities")
	public ResponseEntity<TripResultDto> activityList(TripParamDto tripParamDto) {
		TripResultDto tripResultDto;

		System.out.println("문화생활리스트 : " + tripParamDto);
		if (tripParamDto.getSidoCode() != 0 && tripParamDto.getGugunCode() != 0) {
			System.out.println("시구군 선택 로직 실행");

			tripResultDto = service.contentListSidoGugun(tripParamDto);

			if (tripResultDto.getResult() == SUCCESS) {
				return ResponseEntity.ok().body(tripResultDto);
			} else {
				return ResponseEntity.notFound().build();
			}
		} else if (tripParamDto.getSidoCode() != 0 && tripParamDto.getGugunCode() == 0) {
			System.out.println("시도 선택 로직 실행");

			tripResultDto = service.contentListSido(tripParamDto);

			if (tripResultDto.getResult() == SUCCESS) {
				return ResponseEntity.ok().body(tripResultDto);
			} else {
				return ResponseEntity.notFound().build();
			}
		} else {

			tripResultDto = service.contentList(tripParamDto);

			if (tripResultDto.getResult() == SUCCESS) {
				return ResponseEntity.ok().body(tripResultDto);
			} else {
				return ResponseEntity.notFound().build();
			}
		}
	}
	
	
	// ======================== 관광지 상세 모달 api ===============================
	// 여행 관광지 상세
	@GetMapping(value = "/trips/{contentId}")
	public ResponseEntity<TripResultDto> tripDetail(@PathVariable int contentId) {
		TripParamDto tripParamDto = new TripParamDto();
		tripParamDto.setContentId(contentId);

		TripResultDto tripResultDto = service.tripDetail(tripParamDto);

		if (tripResultDto.getResult() == SUCCESS) {
			return ResponseEntity.ok().body(tripResultDto);
		}

		return ResponseEntity.notFound().build();

	}

	// 축제 관광지 상세
	@GetMapping(value = "/festivals/{contentId}")
	public ResponseEntity<TripResultDto> festivalDetail(@PathVariable int contentId) {
		TripParamDto tripParamDto = new TripParamDto();
		tripParamDto.setContentId(contentId);

		TripResultDto tripResultDto = service.tripDetail(tripParamDto);

		if (tripResultDto.getResult() == SUCCESS) {
			return ResponseEntity.ok().body(tripResultDto);
		}

		return ResponseEntity.notFound().build();

	}

	// 축제 관광지 상세
	@GetMapping(value = "/accommodations/{contentId}")
	public ResponseEntity<TripResultDto> accommodationDetail(@PathVariable int contentId) {
		TripParamDto tripParamDto = new TripParamDto();
		tripParamDto.setContentId(contentId);

		TripResultDto tripResultDto = service.tripDetail(tripParamDto);

		if (tripResultDto.getResult() == SUCCESS) {
			return ResponseEntity.ok().body(tripResultDto);
		}

		return ResponseEntity.notFound().build();

	}

	// 축제 목록 + 시구군 선택
	@GetMapping(value = "/festivals")
	public ResponseEntity<TripResultDto> festivalList(TripParamDto tripParamDto) {
		TripResultDto tripResultDto;

		if (tripParamDto.getSearchWord().isEmpty()) {
			tripResultDto = service.tripList(tripParamDto);
		} else {
			System.out.println("검색 기능 유효 : " + tripParamDto);
			tripResultDto = service.tripListSearchWord(tripParamDto);
		}

		if (tripResultDto.getResult() == SUCCESS) {
			return ResponseEntity.ok().body(tripResultDto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// 숙박 목록 + 시구군 선택
	@GetMapping(value = "/accommodations")
	public ResponseEntity<TripResultDto> lodgmentList(TripParamDto tripParamDto) {
		TripResultDto tripResultDto;

		if (tripParamDto.getSearchWord().isEmpty()) {
			tripResultDto = service.tripList(tripParamDto);
		} else {
			System.out.println("검색 기능 유효 : " + tripParamDto);
			tripResultDto = service.tripListSearchWord(tripParamDto);
		}

		if (tripResultDto.getResult() == SUCCESS) {
			return ResponseEntity.ok().body(tripResultDto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// ====================== 모스트 관광지 API=================================
//	@GetMapping(vlue="")
}
