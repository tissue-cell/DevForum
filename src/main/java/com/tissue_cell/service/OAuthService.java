package com.tissue_cell.service;

public interface OAuthService {
	public String getAccessToken(String code) throws Exception;
	
	public String getUserEmail(String accessToken) throws Exception;
}
