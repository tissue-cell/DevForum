package com.tissue_cell.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {
	private String id;
	private String password;
	private String name;
	private String email;
	private String token;
}
