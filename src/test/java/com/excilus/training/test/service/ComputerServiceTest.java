package com.excilus.training.test.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.training.config.AppConfig;
import com.excilys.training.persistance.ComputerPersistor;
import com.excilys.training.persistance.db.Database;

public class ComputerServiceTest {
	
	@Mock ComputerPersistor persistor;

	@Before
	public void setUp() throws Exception {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		Database db = context.getBean("testDatabase",Database.class);
		persistor = new ComputerPersistor(db);
		context.close();
	}

	@Test
	public void test() {
		//fail("Not yet implemented");
	}

}
