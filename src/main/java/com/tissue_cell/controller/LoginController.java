package com.tissue_cell.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tissue_cell.dto.UserDTO;
import com.tissue_cell.module.jwt.JwtServiceImpl;
import com.tissue_cell.service.LoginService;

@Controller
public class LoginController {
	
	@Autowired
	JwtServiceImpl jwtService;
	@Autowired
	LoginService login;
	
	@Autowired
	BCryptPasswordEncoder bcryptPasswordEncoder;
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@GetMapping("/getUser") // 토큰에 담겨있는 사용자 정보를 리턴, 토큰이 필요한 경로
	public ResponseEntity<Object> getUser(HttpServletRequest request) {
		try {
			String token = request.getHeader("jwt-auth-token");
			Map<String, Object> tokenInfoMap = jwtService.getInfo(token);
			
			UserDTO user = new ObjectMapper().convertValue(tokenInfoMap.get("user"), UserDTO.class);
			
			return new ResponseEntity<Object>(user, HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
	}
	
	@PostMapping("/api/login") // 로그인, 토큰이 필요하지 않는 경로
	public ResponseEntity<Object> login(@RequestBody UserDTO user, HttpServletResponse response) {
		try {
			UserDTO DBUser = new UserDTO(); // 원래는 DB에 저장되어 있는 사용자 정보 가져와야 하는 부분
			DBUser.setId("test");
			DBUser.setPassword("1234");
			logger.info(user.getId());
			logger.info(user.getPassword());
			if(DBUser.getId().equals(user.getId()) && DBUser.getPassword().equals(user.getPassword())) { // 유효한 사용자일 경우
				String token = jwtService.createToken(user); // 사용자 정보로 토큰 생성
				response.setHeader("jwt-auth-token", token); // client에 token 전달
				return new ResponseEntity<Object>("login Success", HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>("login Fail", HttpStatus.OK);
			}
		} catch(Exception e) {
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping(value = "/signup/insert", method = RequestMethod.GET)
	@ResponseBody
	public void regist(UserDTO user, Model model) throws Exception{
		System.out.println("회원가입 요청");
		
		System.out.println("암호화 전 : " + user);
		user.setPassword(bcryptPasswordEncoder.encode(user.getPassword()));
		System.out.println("암호화 후 : " + user);
		
		int result = login.InsertAccount(user);
		
		if(result > 0) {
			System.out.println("회원가입 성공");
		}else {
			System.out.println("회원가입 실패");
		}
	}
}
