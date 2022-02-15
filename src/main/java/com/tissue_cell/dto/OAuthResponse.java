package com.tissue_cell.dto;

import lombok.Data;

@Data
public class OAuthResponse {
	private String accessToken;
	private String expiresIn;
	private String refreshToken;
	private String scope;
	private String tokenType;
	private String idToken;
	private String nothing;
}
