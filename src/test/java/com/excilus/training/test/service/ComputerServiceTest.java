package com.excilus.training.test.service;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.excilys.training.persistance.ComputerPersistor;
import com.excilys.training.persistance.db.Database;
import com.excilys.training.persistance.db.Mysql;
import com.excilys.training.util.ConfigurationProperties;

import org.mockito.Mock;

public class ComputerServiceTest {
	
	@Mock ComputerPersistor persistor;

	@Before
	public void setUp() throws Exception {
		ConfigurationProperties config = new ConfigurationProperties();
		config.load(ConfigurationProperties.DEFAULT_PERSISTANCE_PATH);
		Database db = new Mysql(config);
		persistor = new ComputerPersistor(db);
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
