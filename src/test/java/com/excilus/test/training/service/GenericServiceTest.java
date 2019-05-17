package com.excilus.test.training.service;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.training.config.AppConfig;
import com.excilys.training.persistance.Persistor;

public class GenericServiceTest {
	
	@Mock Persistor persistor;

	@Before
	public void setUp() throws Exception {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		DataSource db = context.getBean("testDataSource",DataSource.class);
		//persistor = new Persistor(db) {};
		context.close();
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
