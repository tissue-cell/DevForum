package com.tissue_cell.controller;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


import org.junit.Ignore;
//import org.junit.jupiter.api.Test;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.tissue_cell.dao.LoginDAO;
import com.tissue_cell.dao.LoginDAOImpl;
import com.tissue_cell.dto.UserDTO;
import com.tissue_cell.service.LoginService;
import com.tissue_cell.service.LoginServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class SignUpTest {
	
	
	@Autowired
	private LoginService login;
	
	@Test
//	@Ignore
	public void 회원가입() {
		UserDTO user = new UserDTO();
		user.setName("test");
		user.setEmail("test@gmail.com");
		user.setId("user");
		user.setPassword("1234");
		System.out.println("회원가입");
		if(login.SelectDuplication(user.getId()) > 0) {
			System.out.println("중복아이디 있음 저장 실패");
		}else {
			System.out.println("중복아이디 없음 저장 성공");
			login.InsertAccount(user);
		}
		
		
		
	}
	@Ignore
	@Test
	public void 중복회원_체크() {
		UserDTO user1 = new UserDTO();
		user1.setName("test");
		user1.setEmail("test@gmail.com");
		user1.setId("user");
		user1.setPassword("1234");
		
		UserDTO user2 = new UserDTO();
		user2.setName("test2");
		user2.setEmail("test2@gmail.com");
		user2.setId("user");
		user2.setPassword("12345");
		login.InsertAccount(user1);
		IllegalStateException e = assertThrows(IllegalStateException.class, () -> login.InsertAccount(user2));
		System.out.println(e.getMessage());
	}
	
}
