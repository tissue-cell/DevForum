package com.tissue_cell.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Data
public class UserDTO {
	private String id;
	private String password;
	private String name;
	private String email;
	private String token;
}
