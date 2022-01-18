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
	public int InsertAccount(UserDTO user) {
		return sqlSession.insert("login.insert_account", user);
	}

	@Override
	public int SelectDuplication(String id) {
		return sqlSession.selectOne("login.select_duplication", id);
	}
	
}
