package com.tissue_cell.controller;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class MyBaitsTest {
	
	 @Autowired
	 private SqlSessionFactory sqlFactory;
	 
	 @Test
	 public void testFactory(){
	     System.out.println("\n >>>>>>>>>> sqlFactory 출력 : "+sqlFactory);
	 }
	 
	 @Test
	 public void testSession() throws Exception{
	     
	     try(SqlSession session = sqlFactory.openSession()){
	         
	         System.out.println(" >>>>>>>>>> session 출력 : "+session+"\n");
	         
	     } catch (Exception e) {
	         e.printStackTrace();
	     }
	 }
}
