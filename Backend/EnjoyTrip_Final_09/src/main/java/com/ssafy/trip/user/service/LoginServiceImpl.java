package com.ssafy.trip.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.trip.user.dao.LoginDao;
import com.ssafy.trip.user.dto.UserDto;


@Service
public class LoginServiceImpl implements LoginService{
	
	@Autowired
	LoginDao loginDao;
	
	@Override
	public UserDto login(UserDto userDto) {
		UserDto dto = loginDao.login(userDto.getUserEmail());
		
		if(dto != null && dto.getUserPassword().equals(userDto.getUserPassword())) {
			dto.setUserPassword(null);
			return dto;
		}
		return null;
	}

}
