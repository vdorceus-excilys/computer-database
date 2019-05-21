package com.excilus.test.training.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.excilys.training.controller.ComputerController;
import com.excilys.training.dto.DefaultComputerSkin;
import com.excilys.training.mapper.DefaultComputerMapper;
import com.excilys.training.model.Company;
import com.excilys.training.model.Computer;
import com.excilys.training.service.ComputerService;
import com.excilys.training.validator.ConstraintValidator;

public class ComputerControllerTest {
	
	 @Mock  ComputerService service;
	 @Mock  DefaultComputerMapper mapper;
	 ConstraintValidator validator;
	 Computer computerDummy= new Computer();
	 DefaultComputerSkin skinDummy ;
	 ComputerController controller;
	 @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();	
	
	
	@BeforeClass
	static public void setUpClass() throws Exception{
		
	}
	
	
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
	 public void init() {
		 dummy();
			mapper = mock(DefaultComputerMapper.class);
			validator = new ConstraintValidator();			
			controller = new ComputerController(service,mapper);
	 }


	@Before
	public void setUp() throws Exception {
		init();
	}


	@Test
	public void showMethod() {
		when(service.findOne(anyLong())).thenReturn(computerDummy);
		when(mapper.reverse(computerDummy)).thenReturn(skinDummy);
		DefaultComputerSkin computerDTO = (DefaultComputerSkin) controller.show("1");
		assertEquals(computerDTO,skinDummy);
	}
	
	@Test
	public void deleteMethod() {
		when(service.delete(any(Computer.class))).thenReturn(true);
		when(mapper.forward(skinDummy)).thenReturn(computerDummy);
		controller.delete(skinDummy);
		return;		
	}
	
	@Test
	public void createMethod() {
		when(service.create(any(Computer.class))).thenReturn(true);
		when(mapper.forward(skinDummy)).thenReturn(computerDummy);
		controller.create(skinDummy);
		return;
	}
	
	@Test
	public void updateMethod() {
		when(service.update(any(Computer.class))).thenReturn(true);
		when(mapper.forward(skinDummy)).thenReturn(computerDummy);
		controller.update(skinDummy);
		return;
	}
	@Test
	public void simpleListMethod() {
		when(service.listAll()).thenReturn(new TreeSet<Computer>(Arrays.asList(new Computer[] {computerDummy})));
		when(mapper.reverse(computerDummy)).thenReturn(skinDummy);
		Set<DefaultComputerSkin> listComputerDTO = controller.list();
		assertEquals(1,listComputerDTO.size());
		assertTrue(listComputerDTO.contains(skinDummy));		
	}
	@Test
	public void pagedListMethod() {
		when(service.listAll(anyLong(),anyLong())).thenReturn(new TreeSet<Computer>(Arrays.asList(new Computer[] {computerDummy})));
		when(mapper.reverse(computerDummy)).thenReturn(skinDummy);
		Set<DefaultComputerSkin> listComputerDTO = controller.list(1L,2L);
		assertEquals(1,listComputerDTO.size());
		assertTrue(listComputerDTO.contains(skinDummy));		
	}
	@Test
	public void countMethod() {
		when(service.count()).thenReturn(1L);
		Long count = controller.count();
		assertEquals(new Long(1L),count);
	}

}
