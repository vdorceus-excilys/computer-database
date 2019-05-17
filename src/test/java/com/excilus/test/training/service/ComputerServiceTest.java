package com.excilus.test.training.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

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
import com.excilys.training.persistance.ComputerPersistor;
import com.excilys.training.service.ComputerService;
import com.excilys.training.validator.ConstraintValidator;

public class ComputerServiceTest {
	
	@Mock ComputerPersistor persistor;
	Computer computerA, computerB;
	Set<Computer> computers;
	Company company;
	@Rule public MockitoRule mockitoRule = MockitoJUnit.rule();
	ComputerService service;

	@Before
	public void setUp() throws Exception {
		company = new Company();
		company.setId(1L);
		company.setName("ASUS");
		
		computerA = new Computer();
		computerA.setId(1L);
		computerA.setName("Bogus");
		computerA.setIntroduced(null);
		computerA.setDiscontinued(null);
		computerA.setCompany(company);
		
		computerB = new Computer();
		computerB.setId(2L);
		computerB.setName("Bongo");
		computerB.setIntroduced(null);
		computerB.setDiscontinued(null);
		computerB.setCompany(company);
		
		computers = new TreeSet<>();
		computers.add(computerA);
		computers.add(computerB);
	}
	
	@Test
	public void listAllComputerSucceded() {
		when(persistor.findAllQuery()).thenReturn(computers);
		service = new ComputerService(persistor,new ConstraintValidator());
		Set<Computer> computerResult = service.listAll();
		assertEquals(computers,computerResult);		
	}
	
	@Test 
	public void listAllComputerWithLimitSucceded() {
		when(persistor.findAllQuery(anyLong(),anyLong())).thenReturn(computers);
		service = new ComputerService(persistor,new ConstraintValidator());
		Set<Computer> computerResult = service.listAll(1L,10L);
		assertEquals(computers,computerResult);	
	}
	@Test
	public void listAllComputerOrderedSucceded() {
		when(persistor.findAllQueryOrdered(anyLong(),anyLong(),anyString(),anyBoolean())).thenReturn(computers);
		service = new ComputerService(persistor,new ConstraintValidator());
		Set<Computer> computerResult = service.orderedListAll(1L,10L,"name",true);
		assertEquals(computers,computerResult);	
	}
	@Test
	public void listComputerBySearchSucceded() {
		when(persistor.searchQuery(anyString())).thenReturn(computers);
		service = new ComputerService(persistor,new ConstraintValidator());
		Set<Computer> computerResult = service.listSearch("Bo");
		assertEquals(computers,computerResult);	
	}
	@Test
	public void findOneComputerSucceded() {
		when(persistor.findOneQuery(anyLong())).thenReturn(computerA);
		service = new ComputerService(persistor,new ConstraintValidator());
		Computer computer = service.findOne(1L);
		assertEquals(computerA,computer);	
	}
	
	@Test 
	public void createComputerSucceded() throws Exception  {
		doNothing().when(persistor).createQuery(computerA);
		service = new ComputerService(persistor,new ConstraintValidator());
		service.create(computerA);
	}
	@Test
	public void updateComputerSucceded() {
		doNothing().when(persistor).updateQuery(computerA);
		service = new ComputerService(persistor,new ConstraintValidator());
		service.update(computerA);
	}
	@Test
	public void deleteComputerSucceded() {
		doNothing().when(persistor).deleteQuery(computerA);
		service = new ComputerService(persistor,new ConstraintValidator());
		service.delete(computerA);
	}
	@Test
	public void countComputerSucceded() throws Exception {
		when(persistor.countAll()).thenReturn(2L);
		doNothing().when(persistor).createQuery(computerA);
		service = new ComputerService(persistor,new ConstraintValidator());
		service.create(computerA);
		service.create(computerB);
		assertEquals(new Long(2L),service.count());
	}

}
