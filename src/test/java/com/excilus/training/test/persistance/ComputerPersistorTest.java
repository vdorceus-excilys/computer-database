package com.excilus.training.test.persistance;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.Statement;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.excilys.training.persistance.db.Database;
import com.excilys.training.persistance.db.Mysql;
import com.excilys.training.util.ConfigurationProperties;

public class ComputerPersistorTest {
	
	static private ConfigurationProperties config;
	static private Database db;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {	
		config = new ConfigurationProperties();
		config.load(ConfigurationProperties.H2_TEST_PERSISTANCE_PATH);
		db = new Mysql(config);
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		try(Connection connection = db.getConnection()){
			Statement statement = connection.createStatement();
			String sql =  "CREATE TABLE   REGISTRATION " + 
		            "(id INTEGER not NULL, " + 
		            " first VARCHAR(255), " +  
		            " last VARCHAR(255), " +  
		            " age INTEGER, " +  
		            " PRIMARY KEY ( id ))";  
		         statement.executeUpdate(sql);
		}
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
