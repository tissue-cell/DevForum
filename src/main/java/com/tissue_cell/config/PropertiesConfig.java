package com.tissue_cell.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


import lombok.Getter;


@Getter
@Component
public class PropertiesConfig {
	
	@Value("#{property['github.secretkey']}")
	private String githubSecretKey;
	
	@Value("#{property['jwt.secretkey']}")
	private String jwtSecretKey;
}
