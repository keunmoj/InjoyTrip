package com.ssafy.trip.user.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.trip.user.dto.UserDto;


@Mapper
public interface LoginDao {
	UserDto login(String userEmail);
}
