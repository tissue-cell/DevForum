package com.tissue_cell.dto;

import lombok.Data;


@Data
public class UserDTO {
	private String id;
	private String password;
	private String name;
	private String email;
	private String token;
}
