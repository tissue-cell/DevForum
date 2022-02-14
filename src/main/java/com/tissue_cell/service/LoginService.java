package com.tissue_cell.service;

import javax.servlet.http.HttpServletResponse;

import com.tissue_cell.dto.UserDTO;

public interface LoginService {
	
	
	public boolean isLogin(UserDTO user);
	
	public boolean isUserExist(String id);
	
	public int signUp(UserDTO user);
	
	public HttpServletResponse responseToken(UserDTO user,HttpServletResponse response);
	
}
