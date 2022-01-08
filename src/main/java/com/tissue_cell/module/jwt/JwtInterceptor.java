package com.tissue_cell.module.jwt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class JwtInterceptor implements HandlerInterceptor{
	
	@Autowired
	private JwtServiceImpl jwtService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		if(request.getMethod().equals("OPTIONS")) { // preflight로 넘어온 options는 통과
			return true;
		} else {
			String token = request.getHeader("jwt-auth-token"); // client에서 요청할 때 header에 넣어둔 "jwt-auth-token"이라는 키 값을 확인
			if(token != null && token.length() > 0) {
				jwtService.checkValid(token); // 토큰 유효성 검증
				return true;
			} else { // 유효한 인증토큰이 아닐 경우
				throw new Exception("유효한 인증토큰이 존재하지 않습니다.");
			}
		}
	}
//??

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
}