package com.tissue_cell.module.jwt;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.tissue_cell.dao.LoginDAO;
import com.tissue_cell.dto.UserDTO;

@Component
public class JwtInterceptor implements HandlerInterceptor {

	@Autowired
	private JwtServiceImpl jwtService;
	@Autowired
	private AuthorizationExtractor authExtractor;
	@Autowired
	private LoginDAO login;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		System.out.println(">>> interceptor.preHandle 호출");
		System.out.println(">>> " + request.getHeader(authExtractor.AUTHORIZATION));
		System.out.println(">>> " + request.getParameter("refresh_token"));
		String accessToken = authExtractor.extract(request, "Bearer");
		String refreshToken = request.getParameter("refresh_token");


		try {
			if (StringUtils.isEmpty(refreshToken)) {
//        리프레시 토큰이 비어있을경우 액세스토큰 인증절차만 밟음
				if (!jwtService.validateToken(accessToken)) {
					System.out.println(">>> 액세스 토큰 유효성검사 실패");
					throw new IllegalAccessException("Acess Token Error!!!");
				} else {
					System.out.println(">>> 액세스 토큰 유효성검사 성공");
					Map<String, Object> payload = jwtService.getInfo(accessToken);
					request.setAttribute("PAYLOAD", payload);
					return true;
				}
			} else {
//  	  리프레시 토큰이 비어있지 않을경우 액세스토큰 갱신 or 로그인 강제 해제
				System.out.println(">>> 리프레시 토큰이 비어있지 않을경우 진입");
				String sub = jwtService.getSubject(accessToken);
				System.out.println(">>> sub : " + sub);
				UserDTO user = login.selectUser(sub);

				if (user.getToken().equals(refreshToken)) {

					if (!jwtService.validateToken(refreshToken)) {
						System.out.println(">>> 리프레시 토큰 유효성검사 실패. 로그인이 필요합니다.");
						throw new IllegalAccessException("Refresh Token Error!!!");
					} else {
						System.out.println(">>> 리프레시 토큰 유효성검사 성공. 액세스 토큰 발급");
						String newAccessToken = jwtService.createAccessToken(user.getId());
						response.addHeader("JWT-ACCESS-TOKEN", newAccessToken);
						Map<String, Object> payload = jwtService.getInfo(newAccessToken);
						request.setAttribute("PAYLOAD", payload);
						return true;
					}

				} else {

					throw new IllegalAccessException("Refresh Token Error!!!");

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print("인터셉터 에러 :" + e);
			response.getWriter().write("{ \"error_description\": \"Invalid Value\"}");
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.setStatus(400);
			return false;
		}

	}

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