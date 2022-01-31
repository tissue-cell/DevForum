package com.tissue_cell.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.tissue_cell.config.PropertiesConfig;
import com.tissue_cell.config.RestTemplateConfig;
import com.tissue_cell.dto.UserDTO;

import com.tissue_cell.service.LoginService;
import com.tissue_cell.service.SocialLoginService;

@RequestMapping("/api")
@RestController

public class LoginController {


	@Autowired
	LoginService loginService;
	
	@Autowired
	SocialLoginService socialLoginService;


	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@GetMapping("/getuser") // 토큰에 담겨있는 사용자 정보를 리턴, 토큰이 필요한 경로
	public ResponseEntity<Object> getUser(HttpServletRequest request) {
		try {
			Map<String, Object> tokenInfoMap = (Map<String, Object>) request.getAttribute("PAYLOAD");

			logger.info(tokenInfoMap.get("sub").toString());

			return new ResponseEntity<Object>(tokenInfoMap, HttpStatus.OK);
		} catch (Exception e) {
			System.out.print("/getuser : " + e.getMessage());
			return new ResponseEntity<Object>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/login") // 로그인, 토큰이 필요하지 않는 경로
	public ResponseEntity<Object> login(@RequestBody UserDTO userDto, HttpServletResponse response) {
		try {
			if (loginService.isLogin(userDto)) {
				logger.info("로그인 비밀번호 일치");
				loginService.responseToken(userDto, response);
				return new ResponseEntity<Object>("login Success", HttpStatus.OK);
			} else {
				logger.info("로그인 비밀번호 불일치");
				return new ResponseEntity<Object>("login Fail", HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			return new ResponseEntity<Object>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/signup/insert")
	public ResponseEntity<Object> insert(UserDTO userDto) throws Exception {
		System.out.println("회원가입 요청");

			if (loginService.isUserExist(userDto.getId())) {
				return new ResponseEntity<>("아이디 중복", HttpStatus.CONFLICT);
			}
			
			int count = loginService.signUp(userDto);
			if(count>0) {
				return new ResponseEntity<>("회원가입 완료", HttpStatus.OK);
			}else {
				return new ResponseEntity<>("회원가입 실패", HttpStatus.CONFLICT);
			}
				
				
			
				
	}

	@GetMapping("/signup/duplication")
	public ResponseEntity<Object> duplication(String id,HttpServletRequest request) {
		if (loginService.isUserExist(id)) {
			return new ResponseEntity<Object>("중복된 계정", HttpStatus.CONFLICT);
		} else {
			return new ResponseEntity<Object>("중복 체크 통과", HttpStatus.OK);
		}

	}

	@GetMapping("/auth/github")
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

}
