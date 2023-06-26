package com.ssafy.trip.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ssafy.trip.user.dto.UserDto;

@Component
public class LoginInterceptor implements HandlerInterceptor{
	
	private final String jsonStr = "{\"result\": \"login\"}";
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception{
		
		System.out.println("LoginInterceptor >>>>" + request.getRequestURI());
		System.out.println(handler);
		
		// 프론트엔드 개발시 cors 환경일 경우 ( simple requets 가 아닌 PUT, DELETE의 preflight 요청 대비 )
		if(request.getMethod().equals("OPTIONS")) return true;
		
		HttpSession session = request.getSession();
		UserDto userDto = (UserDto) session.getAttribute("userDto");
		
		// 유저 로그인이 되어 있지 않으면 기본적으로 result에 login이 들어간다.
		if( userDto == null ) {
			response.getWriter().write(jsonStr);
		
			return false;
		}
//		
		return true;
	}
	
}
