package com.ssafy.trip.user.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.trip.user.dto.UserDto;
import com.ssafy.trip.user.dto.UserUpdateDto;

@Mapper
public interface UserDao {
	int userRegister(UserDto userDto);

	String validNickname(String userNickname);

	String validEmail(String userEmail);
	
	int delete(String userEmail);
	int pwdUpdate(UserUpdateDto dto);
	int infoUpdate(UserUpdateDto dto);
}
