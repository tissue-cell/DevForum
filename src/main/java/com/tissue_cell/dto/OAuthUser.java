package com.tissue_cell.dto;

import lombok.Data;

@Data
public class OAuthUser {
	private String id;
	private String email;
	private String verified_email;
	private String verified;
	private String primary;
	private String visibility;
	private String picture;
}
