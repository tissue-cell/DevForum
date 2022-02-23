package com.tissue_cell.dto;

import lombok.Data;


@Data
public class UserDTO {
	private String id;
	private String password;
	private String datetime;
	private String name;
	private String token;
}
