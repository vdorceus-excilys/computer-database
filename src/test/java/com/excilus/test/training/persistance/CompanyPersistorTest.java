package com.excilus.test.training.persistance;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilus.test.training.config.TestConfig;
import com.excilys.training.model.Company;
import com.excilys.training.persistance.CompanyPersistor;
import com.excilys.training.util.ConfigurationFile;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=TestConfig.class)
public class CompanyPersistorTest {
	
	@Resource(name="testDataSource")
	private DataSource ds;

	@Before
	public void setUp() throws SQLException, Exception {
		try(Connection connection = ds.getConnection()){
			Statement statement = connection.createStatement();			
			String sqls = new ConfigurationFile("test-db.sql").readAsString(); 
			for(String sql : sqls.split(";")) {
				statement.executeUpdate(sql);
			}	
		}		
	}

	
	
	@Test
	public void findAllTest() throws Exception{
		CompanyPersistor persistor = new CompanyPersistor(ds);
		Set<Company> companySet = new TreeSet<>();
		
		Company company = new Company();
		company.setId(10L);company.setName("BOGUS");
		persistor.createQuery(company);
		companySet.add(company);
		
		company = new Company();
		company.setId(11L);company.setName("BLACKBOX");
		companySet.add(company);
		persistor.createQuery(company);
		
		Set<Company> companyFetched = persistor.findAllQuery();
		assertEquals(companySet,companyFetched);
	}
	@Test
	public void findAllWithLimitTest() throws Exception{
		CompanyPersistor persistor = new CompanyPersistor(ds);
		Set<Company> companySet = new TreeSet<>();
		
		Company company = new Company();
		company.setId(10L);company.setName("BOGUS");
		persistor.createQuery(company);
		//companySet.add(company);
		
		company = new Company();
		company.setId(11L);company.setName("BLACKBOX");
		companySet.add(company);
		persistor.createQuery(company);
		
		Set<Company> companyFetched = persistor.findAllQuery(1L,1L);
		assertEquals(companySet,companyFetched);
	}
	@Test
	public void findAllWithOrderTest() throws Exception{
		CompanyPersistor persistor = new CompanyPersistor(ds);
		Set<Company> companySet = new TreeSet<>();
		
		Company company = new Company();
		company.setId(10L);company.setName("BOGUS");
		persistor.createQuery(company);
		//companySet.add(company);
		
		company = new Company();
		company.setId(11L);company.setName("BLACKBOX");
		companySet.add(company);
		persistor.createQuery(company);
		
		Set<Company> companyFetched = persistor.findAllQueryOrdered(1L,1L,"name",false);
		assertEquals(companySet,companyFetched);
	}
	@Test
	public void findAllWithSearchTest() throws Exception{
		CompanyPersistor persistor = new CompanyPersistor(ds);
		Set<Company> companySet = new TreeSet<>();
		
		Company company = new Company();
		company.setId(10L);company.setName("BOGUS");
		persistor.createQuery(company);
		companySet.add(company);
		
		company = new Company();
		company.setId(11L);company.setName("BONGO");
		companySet.add(company);
		persistor.createQuery(company);
		
		Set<Company> companyFetched = persistor.searchQuery("BO");
		assertEquals(companySet,companyFetched);
	}
	
	

	@Test
	public void InitTest() {
		CompanyPersistor persistor = new CompanyPersistor(ds);
		Long count = persistor.countAll();
		assertEquals(new Long(0L),count);
	}
	@Test
	public void createTest() throws Exception {
		CompanyPersistor persistor = new CompanyPersistor(ds);
		Company company = new Company();
		company.setId(10L);company.setName("BOGUS");
		persistor.createQuery(company);
	}
	@Test
	public void findOneTest() throws Exception{
		CompanyPersistor persistor = new CompanyPersistor(ds);
		Company company = new Company();
		company.setId(10L);company.setName("BOGUS");
		persistor.createQuery(company);
		Company companyFetched = persistor.findOneQuery(10L);
		assertEquals(company,companyFetched);
	}
	
	@Test 
	public void updateTest() throws Exception{
		CompanyPersistor persistor = new CompanyPersistor(ds);
		Company company = new Company();
		company.setId(10L);company.setName("BOGUS");
		persistor.createQuery(company);
		company.setName("AlienWare");
		persistor.updateQuery(company);
		Company companyFetched = persistor.findOneQuery(10L);
		assertEquals(company,companyFetched);
	}
	@Test 
	public void deleteTest() throws Exception{
		CompanyPersistor persistor = new CompanyPersistor(ds);
		Company company = new Company();
		company.setId(10L);company.setName("BOGUS");
		persistor.createQuery(company);
		Long count = persistor.countAll();
		assertEquals(new Long(1L),count);
		persistor.deleteQuery(company);
		count = persistor.countAll();
		assertEquals(new Long(0L),count);
	}

}
