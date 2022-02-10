package com.tissue_cell.controller;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tissue_cell.config.PropertiesConfig;
import com.tissue_cell.dto.UserDTO;
import com.tissue_cell.service.LoginService;
import com.tissue_cell.service.SocialLoginService;

@RestController
@RequestMapping("/oauth")
public class OauthCallbackController {
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	SocialLoginService socialLoginService;
	
	PropertiesConfig config = new PropertiesConfig();
	
	@GetMapping("/github")
	public ResponseEntity<Object> loginGithub(String code,HttpServletResponse response) {
		
		ResponseEntity<String> responseAccessToken = socialLoginService.requestAccessToken(code);
		
		Map<String,String> responseMap = socialLoginService.requestEmail(responseAccessToken);
		
		if(loginService.isUserExist(responseMap.get("email"))) {
			UserDTO user = new UserDTO();
			user.setId(responseMap.get("email"));
			loginService.responseToken(user, response);
			return new ResponseEntity<>("이미 회원가입한 상태 jwt 토큰 발급", HttpStatus.OK);
		}else {
			return new ResponseEntity<>(responseMap, HttpStatus.OK);
		}
	}
	
	@GetMapping("/google")
	public String loginGoogle(String code, HttpServletResponse response) {
		//System.out.println(config.googleInitUrl());
		return "success";
	}
	
	
}
