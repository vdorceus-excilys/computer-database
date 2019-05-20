package com.excilus.test.training.service;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilus.test.training.config.TestConfig;
import com.excilys.training.persistance.Persistor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=TestConfig.class)
public class GenericServiceTest {
	
	@Mock Persistor persistor;
	DataSource db;
	

	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void listAllSucceded() {
		
	}
	
	@Test 
	public void listAllWithLimitSucceded() {
		
	}
	@Test
	public void listAllOrderedSucceded() {
		
	}
	@Test
	public void listBySearchSucceded() {
		
	}
	@Test
	public void findOneSucceded() {
		
	}
	@Test
	public void findBySucceded() {
		
	}
	@Test 
	public void createSucceded() {
		
	}
	@Test
	public void updateSucceded() {
		
	}
	@Test
	public void deleteSucceded() {
		
	}
	@Test
	public void countSucceded() {
		
	}

}
