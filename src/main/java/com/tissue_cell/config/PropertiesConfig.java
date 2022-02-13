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
	
	@Value("#{property['google.token.uri']}")
	private String googleTokenUri;
	
	@Value("#{property['google.client']}")
	private String googleClientId;
	
	@Value("#{property['google.secret']}")
	private String googleSecret;
	
	@Value("#{property['google.redirect.uri']}")
	private String googleRedirectUri;
	
	@Value("#{property['google.scope']}")
	private String ScopeUrl;
	
}
