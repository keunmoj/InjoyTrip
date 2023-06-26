package com.ssafy.trip.user.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trip.user.service.ValidService;

@RestController
@RequestMapping("/valid")
public class ValidController {
	
	@Autowired
	ValidService validService;
	private static final int SUCCESS = 1;
	
	@GetMapping("/email/{userEmail}")
	public Map<String, String> duplicateEmail(@PathVariable String userEmail){
		System.out.println(userEmail);
		
		int ret = validService.duplicateEmail(userEmail);
		
		Map<String, String> map = new HashMap<>();
		
		if(ret == SUCCESS) {
			map.put("result", "dup");
			return map;
		}else {
			map.put("result", "notdup");
			return map;
		}
	}
	
	@GetMapping("/nickname/{userNickname}")
	public Map<String, String> duplicateNickname(@PathVariable String userNickname){
		System.out.println(userNickname);
		
		int ret = validService.duplicateNickname(userNickname);
		
		Map<String, String> map = new HashMap<>();
		
		if(ret == SUCCESS) {
			map.put("result", "dup");
			return map;
		}else {
			map.put("result", "notdup");
			return map;
		}
	}
}
