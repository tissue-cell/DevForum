package com.tissue_cell.service;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.tissue_cell.config.PropertiesConfig;

@Service
public class SocialLoginServiceImpl implements SocialLoginService {
	
	@Autowired
	PropertiesConfig propertiesConfig;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Override
	public ResponseEntity<String> requestAccessToken(String code) {
		System.out.println(propertiesConfig.toString());
		System.out.println(code);

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("code", code);
		params.add("client_id", "a2ad4d4c8e745a11fdf6");
		params.add("client_secret", propertiesConfig.getGithubSecretKey());
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", "application/json");
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
		System.out.println(propertiesConfig.getGithubSecretKey());
		ResponseEntity<String> responseGithubToken = restTemplate.exchange(
				"https://github.com/login/oauth/access_token", // {요청할 서버 주소}
				HttpMethod.POST, // {요청할 방식}
				entity, // {요청할 때 보낼 데이터}
				String.class // {요청시 반환되는 데이터 타입}
		);
		
		return responseGithubToken;
	}
	
	@Override
	public Map<String, String> requestEmail(ResponseEntity<String> responseEntity) {
		Map<String, String> response = new HashMap<String, String>();
		response.put("test", "1234");
		JSONObject json = new JSONObject(responseEntity.getBody());
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "token " + json.getString("access_token"));

		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(null, headers);

		ResponseEntity<String> responseGithubEmail = restTemplate.exchange("https://api.github.com/user/public_emails", // {요청할
																														// 서버
																														// 주소}
				HttpMethod.GET, // {요청할 방식}
				entity, // {요청할 때 보낼 데이터}
				String.class // {요청시 반환되는 데이터 타입}
		);
		try {
			JSONArray jsonArray = new JSONArray(responseGithubEmail.getBody());
			System.out.println(jsonArray);
			System.out.println(String.valueOf(jsonArray));
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = (JSONObject) jsonArray.get(i);
				if (jsonObject.get("primary").toString().equals("true")) {
					response.put("email", jsonObject.getString("email"));
					response.put("github_token", json.getString("access_token"));

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return response;
	}
}
