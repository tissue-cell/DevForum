package com.tissue_cell.controller;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tissue_cell.dto.UserDTO;
import com.tissue_cell.module.jwt.JwtServiceImpl;
import com.tissue_cell.service.LoginService;

@RequestMapping("/api")
@RestController
public class LoginController {
	
	@Autowired
	JwtServiceImpl jwtService;
	@Autowired
	LoginService login;
	
	@Autowired
	BCryptPasswordEncoder bcryptPasswordEncoder;
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@GetMapping("/getuser") // 토큰에 담겨있는 사용자 정보를 리턴, 토큰이 필요한 경로
	public ResponseEntity<Object> getUser(HttpServletRequest request) {
		try {
			Map<String, Object> tokenInfoMap = (Map<String, Object>) request.getAttribute("PAYLOAD");

			logger.info(tokenInfoMap.get("userid").toString());
//			UserDTO user = new ObjectMapper().convertValue(tokenInfoMap.get("userid"), UserDTO.class);
			
			return new ResponseEntity<Object>(tokenInfoMap, HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
	}
	
	@PostMapping("/login") // 로그인, 토큰이 필요하지 않는 경로
	public ResponseEntity<Object> login(@RequestBody UserDTO user, HttpServletResponse response) {
		try {
			UserDTO DBUser = login.selectUser(user.getId());
			if(bcryptPasswordEncoder.matches(user.getPassword(),DBUser.getPassword())) {
				logger.info("로그인 비밀번호 일치");
				
				String accessToken = jwtService.createAccessToken(user.getId()); // 사용자 정보로 액세스토큰 생성
				String refreshToken = jwtService.createRefreshToken(user.getId()); // 사용자 정보로 리프레시토큰 생성
				response.setHeader("jwt-access-token","Bearer" + accessToken); // client에 token 전달
			    // create a cookie
			    Cookie cookie = new Cookie("jwt-refresh-token","Bearer" +refreshToken);
			    // expires in 14 days
			    cookie.setMaxAge(14 * 24 * 60 * 60);
			    // optional properties
			    cookie.setSecure(true);
			    cookie.setHttpOnly(true);
			    cookie.setPath("/");
			    // add cookie to response
			    response.addCookie(cookie);
				
				DBUser.setToken(refreshToken);
				login.updateToken(DBUser);
				
				return new ResponseEntity<Object>("login Success", HttpStatus.OK);
			}else {
				logger.info("로그인 비밀번호 불일치");
				return new ResponseEntity<Object>("login Fail", HttpStatus.OK);
			}
		} catch(Exception e) {
			return new ResponseEntity<Object>(null, HttpStatus.UNAUTHORIZED);
		}
	}
	

	@GetMapping("/signup/insert")
	public ResponseEntity<Object> insert(UserDTO user) throws Exception{
		System.out.println("회원가입 요청");
		
		try {
			ResponseEntity<Object> response = duplication(user.getId());
			if(!response.getStatusCode().equals(HttpStatus.ACCEPTED)) {
				return response;
			}
			user.setPassword(bcryptPasswordEncoder.encode(user.getPassword()));
			int result = login.insertAccount(user);

			if (result > 0) {
				return new ResponseEntity<>("회원가입 완료",HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>("DB 등록 실패",HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}catch (NullPointerException e) {
			return new ResponseEntity<>("회원가입 실패",HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/signup/duplication")
	public ResponseEntity<Object> duplication(String id){
		System.out.println("중복 체크");
		
		try {
			int count = login.selectDuplication(id);
			
			if(count > 0) {
				return new ResponseEntity<Object>("중복된 계정",HttpStatus.I_AM_A_TEAPOT);
			}
			
			return new ResponseEntity<Object>("중복 체크 통과",HttpStatus.ACCEPTED);
			
		}catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.FORBIDDEN);
		}
	}
}
