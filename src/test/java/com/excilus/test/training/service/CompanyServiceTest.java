package com.excilus.test.training.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.excilys.training.model.Company;
import com.excilys.training.model.Computer;
import com.excilys.training.persistance.CompanyPersistor;
import com.excilys.training.service.CompanyService;
import com.excilys.training.service.ComputerService;
import com.excilys.training.validator.ConstraintValidator;

public class CompanyServiceTest {
	
	@Mock CompanyPersistor persistor;
	Company companyA, companyB;
	Set<Company> companies;
	@Rule public MockitoRule mockitoRule = MockitoJUnit.rule();
	CompanyService service;

	@Before
	public void setUp() throws Exception {
		companyA = new Company();
		companyB = new Company();
		
		companyA.setId(1L); companyA.setName("A");
		companyB.setId(2L); companyB.setName("B");
		
		companies = new TreeSet<>();
		companies.add(companyA);
		companies.add(companyB);
	}

	@Test
	public void listAllCompanySucceded() {
		when(persistor.findAllQuery()).thenReturn(companies);
		service = new CompanyService(persistor,new ConstraintValidator());
		Set<Company> companyResult = service.listAll();
		assertEquals(companies,companyResult);	
	}
	
	@Test 
	public void listAllCompanyWithLimitSucceded() {
		when(persistor.findAllQuery(anyLong(),anyLong())).thenReturn(companies);
		service = new CompanyService(persistor,new ConstraintValidator());
		Set<Company> companyResult = service.listAll(1L,10L);
		assertEquals(companies,companyResult);
	}
	@Test
	public void listAllCompanyOrderedSucceded() {
		when(persistor.findAllQueryOrdered(anyLong(),anyLong(),anyString(),anyBoolean())).thenReturn(companies);
		service = new CompanyService(persistor,new ConstraintValidator());
		Set<Company> companyResult = service.orderedListAll(1L,10L,"name",true);
		assertEquals(companies,companyResult);	
	}
	@Test
	public void listCompanyBySearchSucceded() {
		when(persistor.searchQuery(anyString())).thenReturn(companies);
		service = new CompanyService(persistor,new ConstraintValidator());
		Set<Company> companyResult = service.listSearch("Bo");
		assertEquals(companies,companyResult);
	}
	@Test
	public void findOneCompanySucceded() {
		when(persistor.findOneQuery(anyLong())).thenReturn(companyA);
		service = new CompanyService(persistor,new ConstraintValidator());
		Company company = service.findOne(1L);
		assertEquals(companyA,company);
	}
	@Test
	public void findByAttributSucceded() {
		when(persistor.findAllQuery()).thenReturn(companies);
		service = new CompanyService(persistor,new ConstraintValidator());
		Company company = service.findByAttribut("NAME","A");
		assertEquals(companyA,company);
	}
	@Test 
	public void createCompanySucceded() throws Exception {
		doNothing().when(persistor).createQuery(companyA);
		service = new CompanyService(persistor,new ConstraintValidator());
		service.create(companyA);
	}
	@Test
	public void updateCompanySucceded() {
		doNothing().when(persistor).updateQuery(companyA);
		service = new CompanyService(persistor,new ConstraintValidator());
		service.update(companyA);
	}
	@Test
	public void deleteCompanySucceded() {
		doNothing().when(persistor).deleteQuery(companyA);
		service = new CompanyService(persistor,new ConstraintValidator());
		service.delete(companyA);
	}
	@Test
	public void countCompanySucceded() throws Exception {
		when(persistor.countAll()).thenReturn(2L);
		doNothing().when(persistor).createQuery(any());
		service = new CompanyService(persistor,new ConstraintValidator());
		service.create(companyA);
		service.create(companyB);
		assertEquals(new Long(2L),service.count());
	}

}
