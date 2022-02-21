package com.tissue_cell.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tissue_cell.dto.UserDTO;
import com.tissue_cell.service.LoginService;

@RequestMapping("/api")
@RestController
public class MemberController {


	@Autowired
	LoginService loginService;

	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

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
		if(response.containsHeader("Authorization")) {
			return new ResponseEntity<Object>("you Already Login", HttpStatus.RESET_CONTENT);
		}
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

	

}
