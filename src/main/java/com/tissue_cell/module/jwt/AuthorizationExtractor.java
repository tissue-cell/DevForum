package com.tissue_cell.module.jwt;

import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component
public class AuthorizationExtractor {
	public static final String AUTHORIZATION = "Authorization";
	public static final String ACCESS_TOKEN_TYPE = AuthorizationExtractor.class.getSimpleName() + ".ACCESS_TOKEN_TYPE";

	// AUTHORIZATION 헤더에서 토큰 추출
	public String extractHeader(HttpServletRequest request, String type) {
		Enumeration<String> headers = request.getHeaders(AUTHORIZATION);
		while (headers.hasMoreElements()) {
			String value = headers.nextElement();
			if (value.toLowerCase().startsWith(type.toLowerCase())) {
				return value.substring(type.length()).trim();
			}
		}

		return null;
	}

//    쿠키에서 토큰 추출
	public String extractCookie(HttpServletRequest request, String cookieName) {
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(cookieName)) {
				return cookie.getValue();
			}
		}
		return null;
	}
}