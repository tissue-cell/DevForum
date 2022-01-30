package com.tissue_cell.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

public interface SocialLoginService {
	
	public ResponseEntity<String> requestAccessToken(String code);
	
	public Map<String, String> requestEmail(ResponseEntity<String> responseEntity);
}
