package com.ssafy.trip.user.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto {
	private int userSeq;
	private String userEmail;
	private String userPassword;
	private String userNewPassword;
	private String userNewNickname;
	private String userNewProfileImageUrl;
}
