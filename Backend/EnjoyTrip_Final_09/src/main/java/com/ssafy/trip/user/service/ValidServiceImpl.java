package com.ssafy.trip.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.trip.user.dao.ValidDao;
@Service
public class ValidServiceImpl implements ValidService{
	
	@Autowired
	ValidDao validDao;

	@Override
	public int duplicateEmail(String userEmail) {
		return validDao.duplicateEmail(userEmail);
	}

	@Override
	public int duplicateNickname(String userNickname) {
		// TODO Auto-generated method stub
		return validDao.duplicateNickname(userNickname);
	}
	
	
}
