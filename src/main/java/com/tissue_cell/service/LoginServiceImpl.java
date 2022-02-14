package com.tissue_cell.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.tissue_cell.dao.LoginDAO;
import com.tissue_cell.dto.UserDTO;
import com.tissue_cell.module.jwt.JwtServiceImpl;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private LoginDAO loginDao;
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;

	private JwtServiceImpl jwtService;

	@Autowired
	public LoginServiceImpl(JwtServiceImpl jwtService) {
		this.jwtService = jwtService;
	}

	@Override
	public boolean isLogin(UserDTO userDto) {
		if (bcryptPasswordEncoder.matches(userDto.getPassword(), loginDao.selectUser(userDto.getId()).getPassword())) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public HttpServletResponse responseToken(UserDTO userDto, HttpServletResponse response) {
		String accessToken = jwtService.createAccessToken(userDto.getId()); // 사용자 정보로 액세스토큰 생성
		String refreshToken = jwtService.createRefreshToken(userDto.getId()); // 사용자 정보로 리프레시토큰 생성

//		 add cookie to response
		response.setHeader("access_token", accessToken);
		response.addCookie(setCookie("refresh_token",refreshToken));
		// 리프레시 토큰 업데이트
		userDto.setToken(refreshToken);
		loginDao.updateToken(userDto);
		return response;
	}
@Override
public Cookie setCookie(String name,String value) {
	// create a cookie
	Cookie cookie = new Cookie(name, value);
	// expires in 14 days
	cookie.setMaxAge(14 * 24 * 60 * 60);
	// optional properties
	cookie.setSecure(true);
	cookie.setHttpOnly(true);
	cookie.setPath("/");
	return cookie;
}
	@Override
	public boolean isUserExist(String id) {
		if (loginDao.selectDuplication(id) > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int signUp(UserDTO userDto) {
		if (userDto.getPassword() != null) {
			userDto.setPassword(bcryptPasswordEncoder.encode(userDto.getPassword()));
		}
		return loginDao.insertAccount(userDto);

	}
}
