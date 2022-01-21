package com.tissue_cell.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tissue_cell.dto.UserDTO;

@Repository
public class LoginDAOImpl implements LoginDAO{
	
	@Autowired
	SqlSession sqlSession;
	
	@Override
	public int insertAccount(UserDTO user) {
		return sqlSession.insert("login.insert_account", user);
	}

	@Override
	public int selectDuplication(String id) {
		return sqlSession.selectOne("login.select_duplication", id);
	}

	@Override
	public UserDTO selectUser(String id) {
		
		return sqlSession.selectOne("login.select_user",id);
	}
	@Override
	public void updateToken(UserDTO user) {
		 sqlSession.update("login.update_user_token",user);
	}
}
