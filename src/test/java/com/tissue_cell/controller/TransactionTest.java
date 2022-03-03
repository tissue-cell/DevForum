package com.tissue_cell.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class TransactionTest {

	/**
	 * REQUIRED: 현재 진행중인 트랜잭션이 있으면 그것을 사용하고, 없으면 생성한다. [DEFAULT 값]<br>
	 * MANDATORY: 현재 진행중인 트랜잭션이 없으면 Exception 발생. 없으면 생성한다.<br>
	 * REQUIRES_NEW: 항상 새로운 트랜잭션을 만듦 (트랜잭션을 분리)<br>
	 * SUPPORTS: 현재 진행중인 트랜잭션이 있으면 그것을 사용. 없으면 그냥 진행.<br>
	 * NOT_SUPPORTED: 현재 진행중인 트랜잭션이 있으면 그것을 미사용. 없으면 그냥 진행.<br>
	 * NEVER: 현재 진행중인 트랜잭션이 있으면 Exception. 없으면 그냥 진행.<br>
	 */
	@Test
	@Transactional(readOnly = true, propagation = Propagation.NEVER, rollbackFor = {Exception.class})
	public void transaction() throws Exception{
		
	}
}
