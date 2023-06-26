package com.ssafy.trip.user.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trip.user.dto.UserDto;
import com.ssafy.trip.user.service.LoginService;

@RestController
public class LoginController {
	
	@Autowired
	LoginService loginService;
	
	@PostMapping(value="/login")
	public Map<String, Object> login(@RequestBody UserDto dto, HttpSession session) { // json request가 들어옴
		
		UserDto userDto = loginService.login(dto);
		System.out.println(dto);
		Map<String, Object> map = new HashMap<>();
		if( dto != null ) {
			session.setAttribute("userDto", userDto); // 이건 서버에만 정보가 존재하게된다. 따라서 클라언트에게 내려줄 때 session도 같이 내려준다.
			map.put("result", "success");
			map.put("userSeq", userDto.getUserSeq());
			map.put("userName", userDto.getUserName());
			map.put("userEmail", userDto.getUserEmail());
			map.put("userNickname", userDto.getUserNickname());
			map.put("userProfileImageUrl", userDto.getUserProfileImageUrl());
			// 이정보를 session storage에 저장한다. 인터셉터 같은 경우는 session storage를 뒤져서 name이 있는지 없는지 확인하면된다.
			// session -> server
			// session storage -> front
			
		}else {
			map.put("result", "fail");
		}
		return map;
	}
	
	@GetMapping(value="/logout")
	public Map<String, String> logout(HttpSession session) { // json request가 들어옴
		Map<String, String> map = new HashMap<>();
		session.invalidate();
		map.put("result", "success");
		return map;
	}
}
