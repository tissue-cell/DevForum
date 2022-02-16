package com.tissue_cell.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OAuthRequest {
	private String redirectUri;
	private String clientId;
	private String client_id;
	private String clientSecret;
	private String client_secret;
	private String code;
	private String responseType;
	private String scope;
	private String accessType;
	private String grantType;
	private String state;
	private String includeGrantedScopes;
	private String loginHint;
	private String prompt;
}
