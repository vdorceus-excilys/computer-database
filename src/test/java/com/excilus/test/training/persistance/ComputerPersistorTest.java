package com.excilus.test.training.persistance;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.training.config.TestConfig;
import com.excilys.training.model.Computer;
import com.excilys.training.persistance.ComputerPersistor;
import com.excilys.training.util.ConfigurationFile;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=TestConfig.class)
public class ComputerPersistorTest {
	
	@Resource(name="testDataSource")	
	private DataSource ds;
	@Resource(name="correctComputerA")	
	private Computer correctComputerA;
	@Resource(name="correctComputerB")
	private Computer correctComputerB;
	

	@Before
	public void setUp() throws SQLException, Exception {
		try(Connection connection = ds.getConnection()){
			Statement statement = connection.createStatement();			
			String sqls = new ConfigurationFile("test-db.sql").readAsString(); 
			String sqlsData = new ConfigurationFile("test-db.sql").readAsString(); 
			for(String sql : sqls.split(";")) {
				statement.executeUpdate(sql);
			}	
			/*
			 * for(String sql : sqlsData.split(";")) { statement.executeUpdate(sql); }
			 */
		}		
	}

	
	
	@Test
	public void findAllTest() throws Exception{
		ComputerPersistor persistor = new ComputerPersistor(ds);
		Set<Computer> computerSet = new TreeSet<>(Arrays.asList(correctComputerA,correctComputerB));	
		for(Computer computer : computerSet) persistor.createQuery(computer); 
		Set<Computer> computerFetched = persistor.findAllQuery();
		assertEquals(computerSet,computerFetched);
	}
	@Test
	public void findAllWithLimitTest() throws Exception{
		ComputerPersistor persistor = new ComputerPersistor(ds);
		Set<Computer> computerSet = new TreeSet<>(Arrays.asList(correctComputerB));
		persistor.createQuery(correctComputerA);			
		persistor.createQuery(correctComputerB);		
		Set<Computer> computerFetched = persistor.findAllQuery(1L,1L);
		assertEquals(computerSet,computerFetched);
	}
	@Test
	public void findAllWithOrderTest() throws Exception{
		ComputerPersistor persistor = new ComputerPersistor(ds);
		Set<Computer> computerSet = new TreeSet<>(Arrays.asList(correctComputerB));		
		persistor.createQuery(correctComputerA);
		persistor.createQuery(correctComputerB);		
		Set<Computer> computerFetched = persistor.findAllQueryOrdered(1L,1L,"name",true);
		assertEquals(computerSet,computerFetched);
	}
	@Test
	public void findAllWithSearchTest() throws Exception{
		ComputerPersistor persistor = new ComputerPersistor(ds);
		Set<Computer> computerSet = new TreeSet<>(Arrays.asList(correctComputerA,correctComputerB));		
		for(Computer computer : computerSet) persistor.createQuery(computer); 
		Set<Computer> computerFetched = persistor.searchQuery("Computer");
		assertEquals(computerSet,computerFetched);
	}

	@Test
	public void InitTest() {
		ComputerPersistor persistor = new ComputerPersistor(ds);
		Long count = persistor.countAll();
		assertEquals(new Long(0L),count);
	}
	@Test
	public void createTest() throws Exception {
		ComputerPersistor persistor = new ComputerPersistor(ds);
		persistor.createQuery(correctComputerA);
		Long count = persistor.countAll();
		assertEquals(new Long(1L),count);
	}
	@Test
	public void findOneTest() throws Exception{
		ComputerPersistor persistor = new ComputerPersistor(ds);		
		persistor.createQuery(correctComputerA);
		Computer computerFetched = persistor.findOneQuery(1000L);
		assertEquals(correctComputerA,computerFetched);
	}
	
	@Test 
	public void updateTest() throws Exception{
		ComputerPersistor persistor = new ComputerPersistor(ds);
		Computer computer = correctComputerA.clone();
		persistor.createQuery(computer);
		computer.setName("AlienWare");
		persistor.updateQuery(computer);
		Computer computerFetched = persistor.findOneQuery(1000L);
		assertEquals(computer,computerFetched);
	}
	@Test 
	public void deleteTest() throws Exception{
		ComputerPersistor persistor = new ComputerPersistor(ds);
		Computer computer = correctComputerA.clone();
		persistor.createQuery(computer);
		Long count = persistor.countAll();
		assertEquals(new Long(1L),count);
		persistor.deleteQuery(computer);
		count = persistor.countAll();
		assertEquals(new Long(0L),count);
	}

}
