package com.ssafy.trip.user.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trip.user.dto.UserDto;
import com.ssafy.trip.user.dto.UserResultDto;
import com.ssafy.trip.user.dto.UserUpdateDto;
import com.ssafy.trip.user.service.UserService;


@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	private static final int SUCCESS = 1; // �떖蹂쇰줈�뜥 1�� �꽦怨듭쓣 �쓽誘명븳�떎.
	
	@PostMapping(value="/users")
	public ResponseEntity<Map<String, String>> register(@RequestBody UserDto userDto) {
		
		System.out.println("here is controller");
		System.out.println(userDto);
		
		UserResultDto userResultDto = userService.userRegister(userDto);
		
		Map<String, String> map = new HashMap<>();
		
		if(userResultDto.getResult() == SUCCESS) {
			map.put("result", "success");
			return ResponseEntity.ok().body(map);
		}else {
			map.put("result", "fail");
			return new ResponseEntity<Map<String, String>>(map, HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping(value="/users/password")
	public Map<String, String> pwdUpdate(@RequestBody UserUpdateDto dto) {
		System.out.println("here is controller");
		System.out.println(dto);
		
		int ret = userService.pwdUpdate(dto);
		
		Map<String, String> map = new HashMap<>();
		
		if(ret == SUCCESS) {
			map.put("result", "success");
			return map;
		}else {
			map.put("result", "fail");
			return map;
		}
	}
	
	@PutMapping(value="/users/info")
    public Map<String, String> infoUpdate(@RequestBody UserUpdateDto dto) {
        System.out.println("here is controller");
        System.out.println(dto);
        
        int ret = userService.infoUpdate(dto);
        
        Map<String, String> map = new HashMap<>();
        
        if(ret == SUCCESS) {
            map.put("result", "success");
            return map;
        }else {
            map.put("result", "fail");
            return map;
        }
    }
	
	@DeleteMapping("/users/{userEmail}")
	public ResponseEntity<Map<String, String>> delete(@PathVariable String userEmail){
		System.out.println(userEmail);
		
		int ret = userService.delete(userEmail);
		
		Map<String, String> map = new HashMap<>();
		
		if(ret == SUCCESS) {
			map.put("result", "success");
			return ResponseEntity.ok().body(map);
		}else {
			map.put("result", "fail");
			return new ResponseEntity<Map<String, String>>(map, HttpStatus.NOT_FOUND);
		}
	}
}
