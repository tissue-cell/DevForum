package com.tissue_cell.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tissue_cell.dto.UserDTO;
import com.tissue_cell.service.LoginService;
import com.tissue_cell.service.OAuthService;

@RestController
@RequestMapping("/oauth")
public class OauthCallbackController {
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	OAuthService google;
	@Autowired
	OAuthService github;
	
	@GetMapping("/github")
	public ResponseEntity<Object> loginGithub(String code,HttpServletResponse response) {
		System.out.println(response.getStatus());
		String userEmail = "";
		
		try {
			userEmail = github.getUserEmail(github.getAccessToken(code));
			System.out.println(userEmail);
		}catch (Exception e) {
			return new ResponseEntity<Object>("Response at Github API server : "+e.toString(), HttpStatus.BAD_REQUEST);
		}
		
		if(loginService.isUserExist(userEmail)) {		//로그인 처리
			UserDTO user = new UserDTO();
			user.setId(userEmail);
			loginService.responseToken(user, response);		//토큰 생성(로그인)
			
			return new ResponseEntity<>("이미 회원가입한 상태 jwt 토큰 발급", HttpStatus.OK);
		}else {		//회원가입 처리
			return new ResponseEntity<>("회원가입",HttpStatus.OK);
		}
	}
	
	@GetMapping("/google")
	public ResponseEntity<Object> loginGoogle(String code, HttpServletResponse response){
		
		String userEmail = "";
		
		try {
			userEmail = google.getUserEmail(google.getAccessToken(code));
		}catch (Exception e) {		//구글 서버 요청 실패시
			return new ResponseEntity<Object>("Response at Google API server : "+e.toString(), HttpStatus.BAD_REQUEST);
		}
		if(loginService.isUserExist(userEmail)) {		//로그인
			UserDTO user = new UserDTO();
			user.setId(userEmail);
			loginService.responseToken(user, response);
			
			return new ResponseEntity<Object>("로그인",HttpStatus.OK);
		}else {		//회원가입 처리
			return new ResponseEntity<Object>("회원가입",HttpStatus.OK);
		}
		//System.out.println(userEmail);
		

	}
	
	
}
