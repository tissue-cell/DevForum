package com.tissue_cell.service;

import com.tissue_cell.dto.UserDTO;

public interface LoginService {
	
	public int insertAccount(UserDTO user);
	
	public int selectDuplication(String id);
	
	public UserDTO selectUser(String id);
	
	public void updateToken(UserDTO user);

}
