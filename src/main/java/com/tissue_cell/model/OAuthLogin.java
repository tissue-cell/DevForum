package com.tissue_cell.model;

public interface OAuthLogin {
	public String getAccessToken(String code) throws Exception;
	
	public String getUserEmail(String accessToken) throws Exception;
}
