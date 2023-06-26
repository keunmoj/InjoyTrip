package com.ssafy.trip.user.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ValidDao {
	int duplicateEmail(String userEmail);
	int duplicateNickname(String userNickname);
}
