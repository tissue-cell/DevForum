package com.tissue_cell.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.tissue_cell.config.PropertiesConfig;
import com.tissue_cell.dto.OAuthRequest;
import com.tissue_cell.dto.OAuthResponse;
import com.tissue_cell.dto.OAuthUser;
import com.tissue_cell.service.LoginService;

@Service("google")
public class GoogleOAuthLogin implements OAuthLogin{

	@Autowired
	LoginService loginService;
	@Autowired
	PropertiesConfig config;
	
	RestTemplate restTemplate;
	ObjectMapper mapper;
	
	@Autowired
	public GoogleOAuthLogin() {
		restTemplate = new RestTemplate();
		mapper = new ObjectMapper();
		
		//JSON 파싱을 위한 기본값 세팅
		//요청시 파라미터는 스네이크 케이스로 세팅되므로 Object mapper에 미리 설정해준다.
		mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
		mapper.setSerializationInclusion(Include.NON_NULL);
	}
	
	@Override
	public String getAccessToken(String code) throws Exception {
		//Access Token 요청을 위한 파라미터 세팅
		OAuthRequest googleOAuthRequestParam = OAuthRequest
				.builder()
				.clientId(config.getGoogleClientId())
				.clientSecret(config.getGoogleSecret())
				.code(code)
				.redirectUri(config.getGoogleRedirectUri())
				.grantType("authorization_code").build();
		
		//AccessToken 발급 요청
		ResponseEntity<String> resultEntity = restTemplate.postForEntity(config.getGoogleTokenUri(), googleOAuthRequestParam, String.class);

		//Token Request
		OAuthResponse tokenresult = mapper.readValue(resultEntity.getBody(), new TypeReference<OAuthResponse>() {});
		
		return tokenresult.getAccessToken();
	}

	@Override
	public String getUserEmail(String accessToken) throws Exception{
		String accessUrl = UriComponentsBuilder.fromHttpUrl(config.getGoogleAccessUri())
				.queryParam("access_token", accessToken)
				.toUriString();
		
		//사용자 정보 요청
		ResponseEntity<String> resultEntity = restTemplate.getForEntity(accessUrl, String.class);
		
		//access request
		OAuthUser accessResult = mapper.readValue(resultEntity.getBody(), new TypeReference<OAuthUser>() {});
		
		return accessResult.getEmail();
	}

}
