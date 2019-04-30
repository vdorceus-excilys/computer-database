package com.excilys.training.test.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.excilys.training.controller.ComputerController;
import com.excilys.training.dto.DataTransferObject;
import com.excilys.training.dto.DefaultComputerSkin;
import com.excilys.training.mapper.DefaultComputerMapper;
import com.excilys.training.model.Company;
import com.excilys.training.model.Computer;
import com.excilys.training.service.ComputerService;
import com.excilys.training.validator.model.ComputerDefaultValidator;

public class ComputerControllerTest {
	
	@Mock ComputerService service;
	@Mock DefaultComputerMapper mapper;
	@Mock ComputerDefaultValidator validator;
	Computer computerDummy= new Computer();
	DefaultComputerSkin skinDummy ;
	ComputerController controller;
	@Rule public MockitoRule mockitoRule = MockitoJUnit.rule();	
	
	
	public void dummy() {
		computerDummy.setId(1L);
		computerDummy.setName("Ordinateur de travail");
		computerDummy.setIntroduced(new Date());
		Company companyDummy = new Company();
		companyDummy.setId(1L);
		companyDummy.setName("Apple");
		computerDummy.setCompany(companyDummy);
		skinDummy = new DefaultComputerSkin();
		skinDummy.setId("1");
		skinDummy.setName("Ordinateur de travail");
		skinDummy.setIntroduced("24-04-2019");
		skinDummy.setCompany("Apple");
	}


	@Before
	public void setUp() throws Exception {
		this.dummy();
		mapper = mock(DefaultComputerMapper.class);
		validator = mock(ComputerDefaultValidator.class);
		when(mapper.forward(skinDummy)).thenReturn(computerDummy);
		when(mapper.reverse(computerDummy)).thenReturn(skinDummy);
		doNothing().when(validator).validate(computerDummy);
		when(service.update(any(Computer.class))).thenReturn(true);
		when(service.create(any(Computer.class))).thenReturn(true);
		when(service.delete(any(Computer.class))).thenReturn(true);
		when(service.count()).thenReturn(1L);
		when(service.listAll()).thenReturn(new TreeSet<Computer>(Arrays.asList(new Computer[] {computerDummy})));
		when(service.listAll(anyLong(),anyLong())).thenReturn(new TreeSet<Computer>(Arrays.asList(new Computer[] {computerDummy})));
		when(service.findOne(anyLong())).thenReturn(computerDummy);
		when(service.findByAttribut(anyString(),anyString())).thenReturn(computerDummy);
		controller = ComputerController.getInstance(service,mapper);
	}


	@Test
	public void showMethod() {
		DefaultComputerSkin computerDTO = (DefaultComputerSkin) controller.show("1");
		assertEquals(computerDTO,skinDummy);
	}
	
	@Test
	public void deleteMethod() {
		controller.delete(skinDummy);
		return;		
	}
	
	@Test
	public void createMethod() {
		controller.create(skinDummy);
		return;
	}
	
	@Test
	public void updateMethod() {
		controller.update(skinDummy);
		return;
	}
	@Test
	public void simpleListMethod() {
		Set<DataTransferObject<Computer>> listComputerDTO = controller.list();
		assertEquals(1,listComputerDTO.size());
		assertTrue(listComputerDTO.contains(skinDummy));		
	}
	@Test
	public void pagedListMethod() {
		Set<DataTransferObject<Computer>> listComputerDTO = controller.list(1L,2L);
		assertEquals(1,listComputerDTO.size());
		assertTrue(listComputerDTO.contains(skinDummy));		
	}
	@Test
	public void countMethod() {
		Long count = controller.count();
		
	}

}
