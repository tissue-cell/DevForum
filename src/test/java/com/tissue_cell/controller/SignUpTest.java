package com.tissue_cell.controller;

//import org.junit.jupiter.api.Test;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.tissue_cell.dao.LoginDAO;
import com.tissue_cell.dto.UserDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class SignUpTest {
	
	
	@Autowired
	private LoginDAO loginDao;
	
	@Test
	public void 회원가입() {
		UserDTO user = new UserDTO();
		user.setName("test");
		user.setEmail("test@gmail.com");
		user.setId("user");
		user.setPassword("1234");
		System.out.println("회원가입");
		if(loginDao.selectDuplication(user.getId()) > 0) {
			System.out.println("중복아이디 있음 저장 실패");
		}else {
			System.out.println("중복아이디 없음 저장 성공");
			loginDao.insertAccount(user);
		}
	}
	
}
