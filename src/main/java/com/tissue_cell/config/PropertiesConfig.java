package com.tissue_cell.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;


@Getter
@Component
public class PropertiesConfig {
	
	@Value("#{property['github.secret']}")
	private String githubSecret;
	
	@Value("#{property['github.client']}")
	private String githubClientId;
	
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
	
	@Value("#{property['google.access.uri']}")
	private String googleAccessUri;
	
	@Value("#{property['google.scope']}")
	private String ScopeUrl;
	
}
