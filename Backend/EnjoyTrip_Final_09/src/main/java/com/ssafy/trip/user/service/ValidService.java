package com.ssafy.trip.user.service;



public interface ValidService {
	int duplicateEmail(String userEmail);
	int duplicateNickname(String userNickname);
}
