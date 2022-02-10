package com.tissue_cell.config;


import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;


@Getter
@Component
public class PropertiesConfig {
	
	@Value("#{property['github.secretkey']}")
	private String githubSecretKey;
	
	@Value("#{property['jwt.secretkey']}")
	private String jwtSecretKey;
	
	@Value("#{property['google.login.url']}")
	private String googleLoginUrl;
	
	@Value("#{property['google.client']}")
	private String googleClientId;
	
	@Value("#{property['google.secret']}")
	private String googleSecret;
	
	@Value("#{property['google.redirect.uri']}")
	private String googleRedirectUri;
	
	@Value("#{property['google.scope']}")
	private String ScopeUrl;
	
	@Value("#{property['google.call']}")
	private String googleCall;
	
	public String googleInitUrl() {		//테스트용
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("client_id", getGoogleClientId());
		params.put("redirect_uri", getGoogleRedirectUri());
		params.put("response_type", "code");
		params.put("scope", getScopeUrl());
		
		String paramStr = params.entrySet().stream().map(param -> param.getKey() + "=" + param.getValue()).collect(Collectors.joining("&"));
		
		return getGoogleLoginUrl() + "?" + paramStr;
	}
}
