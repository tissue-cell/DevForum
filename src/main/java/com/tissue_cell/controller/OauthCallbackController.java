package com.tissue_cell.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.tissue_cell.config.PropertiesConfig;
import com.tissue_cell.dto.GoogleOAuthRequest;
import com.tissue_cell.dto.GoogleOAuthResponse;
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
	
	@Autowired
	PropertiesConfig config;
	
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
	public String loginGoogle(String code, HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException {
		RestTemplate restTemplate = new RestTemplate();

		//Google OAuth Access Token 요청을 위한 파라미터 세팅
		GoogleOAuthRequest googleOAuthRequestParam = GoogleOAuthRequest
				.builder()
				.clientId(config.getGoogleClientId())
				.clientSecret(config.getGoogleSecret())
				.code(code)
				.redirectUri(config.getGoogleRedirectUri())
				.grantType("authorization_code").build();

		//JSON 파싱을 위한 기본값 세팅
		//요청시 파라미터는 스네이크 케이스로 세팅되므로 Object mapper에 미리 설정해준다.
		ObjectMapper mapper = new ObjectMapper();
		mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
		mapper.setSerializationInclusion(Include.NON_NULL);

		//AccessToken 발급 요청
		ResponseEntity<String> resultEntity = restTemplate.postForEntity(config.getGoogleTokenUri(), googleOAuthRequestParam, String.class);

		//Token Request
		GoogleOAuthResponse result = mapper.readValue(resultEntity.getBody(), new TypeReference<GoogleOAuthResponse>() {
		});
		
		System.out.println(resultEntity.getBody());
		
		String responseUri = config.getGoogleAccessUri() + "?access_token=" + result.getAccessToken();
		
		String resultString = restTemplate.getForObject(responseUri, String.class);
		//access request
		Map<String, Object> accessResult = mapper.readValue(resultString, new TypeReference<HashMap<String, Object>>() {
		});
		
		System.out.println(accessResult);
		
		return (String) accessResult.get("email");
	}
	
	
}
