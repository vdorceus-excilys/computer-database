package com.excilus.training.test.persistance;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.training.config.AppConfig;
import com.excilys.training.model.Computer;
import com.excilys.training.persistance.ComputerPersistor;
import com.excilys.training.persistance.db.Database;
import com.excilys.training.util.ConfigurationFile;

public class ComputerPersistorTest {
	
	static private Database db;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {	
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		db = context.getBean("testDatabase",Database.class);
		context.close();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	

	@Before
	public void setUp() throws SQLException, Exception {
		try(Connection connection = db.getConnection()){
			Statement statement = connection.createStatement();			
			String sqls = new ConfigurationFile("test-db.sql").readAsString(); 
			for(String sql : sqls.split(";")) {
				statement.executeUpdate(sql);
			}	
		}		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void InitTest() {
		ComputerPersistor persistor = new ComputerPersistor(db);
		Long count = persistor.countAll();
		assertEquals(new Long(0L),count);
	}
	@Test
	public void createTest() throws Exception {
		ComputerPersistor persistor = new ComputerPersistor(db);
		Computer computer = new Computer();
		computer.setId(10L);computer.setName("BOGUS");
		persistor.createQuery(computer);
	}
	@Test
	public void findOneTest() throws Exception{
		ComputerPersistor persistor = new ComputerPersistor(db);
		Computer computer = new Computer();
		computer.setId(10L);computer.setName("BOGUS");
		persistor.createQuery(computer);
		Computer computerFetched = persistor.findOneQuery(10L);
		assertEquals(computer,computerFetched);
	}
	
	@Test 
	public void updateTest() throws Exception{
		ComputerPersistor persistor = new ComputerPersistor(db);
		Computer computer = new Computer();
		computer.setId(10L);computer.setName("BOGUS");
		persistor.createQuery(computer);
		computer.setName("AlienWare");
		persistor.updateQuery(computer);
		Computer computerFetched = persistor.findOneQuery(10L);
		assertEquals(computer,computerFetched);
	}
	@Test 
	public void deleteTest() throws Exception{
		ComputerPersistor persistor = new ComputerPersistor(db);
		Computer computer = new Computer();
		computer.setId(10L);computer.setName("BOGUS");
		persistor.createQuery(computer);
		Long count = persistor.countAll();
		assertEquals(new Long(1L),count);
		persistor.deleteQuery(computer);
		count = persistor.countAll();
		assertEquals(new Long(0L),count);
	}

}
