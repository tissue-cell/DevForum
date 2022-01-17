package com.tissue_cell.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tissue_cell.dao.LoginDAO;
import com.tissue_cell.dto.UserDTO;

@Service
public class LoginServiceImpl implements LoginService{

	@Autowired
	private LoginDAO login;
	
	@Override
	public int InsertAccount(UserDTO user) {
		
		return login.InsertAccount(user);
	}

}
