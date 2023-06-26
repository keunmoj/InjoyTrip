package com.ssafy.trip.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.trip.user.dao.UserDao;
import com.ssafy.trip.user.dto.UserDto;
import com.ssafy.trip.user.dto.UserResultDto;
import com.ssafy.trip.user.dto.UserUpdateDto;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserDao userDao;
	
	private static final int SUCCESS = 1;
	private static final int FAIL = -1;
	@Override
	public UserResultDto userRegister(UserDto userDto) {
		UserResultDto userResultDto = new UserResultDto();
		if( userDao.userRegister(userDto) == SUCCESS ) {
			userResultDto.setResult(SUCCESS);
		}else {
			userResultDto.setResult(FAIL);
		}
		
		return userResultDto;
	}
	@Override
	public int validNickname(String userNickname) {
		String result = userDao.validNickname(userNickname);
		
		if(result == null || result.equals("")) {
			return FAIL;
		}else {
			return SUCCESS;
		}
	}
	@Override
	public int validEmail(String userEmail) {
		String result = userDao.validEmail(userEmail);
		
		if(result == null || result.equals("")) {
			return FAIL;
		}else {
			return SUCCESS;
		}
	}
	@Override
	public int delete(String userEmail) {
		// TODO Auto-generated method stub
		return userDao.delete(userEmail);
	}
	@Override
	public int pwdUpdate(UserUpdateDto dto) {
		// TODO Auto-generated method stub
		return userDao.pwdUpdate(dto);
	}
	@Override
	public int infoUpdate(UserUpdateDto dto) {
		// TODO Auto-generated method stub
		return userDao.infoUpdate(dto);
	}
	
	
	

}
