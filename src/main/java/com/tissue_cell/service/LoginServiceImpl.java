package com.tissue_cell.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.tissue_cell.dao.LoginDAO;
import com.tissue_cell.dto.UserDTO;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private LoginDAO login;

	@Override
	public int insertAccount(UserDTO user) {

		return login.insertAccount(user);
	}

	@Override
	public int selectDuplication(String id) {
		return login.selectDuplication(id);
	}

	@Override
	public UserDTO selectUser(String id) {

		return login.selectUser(id);
	}

	@Override
	public void updateToken(UserDTO user) {
		 login.updateToken(user);
	}
}
