package com.excilys.training.test.controller;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.excilys.training.mapper.DefaultComputerMapper;
import com.excilys.training.mapper.dto.DefaultComputerSkin;
import com.excilys.training.model.Company;
import com.excilys.training.model.Computer;
import com.excilys.training.model.validator.ComputerDefaultValidator;

public class ComputerControllerTest {
	
		
	@Mock DefaultComputerMapper mapper;
	@Mock ComputerDefaultValidator validator;
	Computer computerDummy= new Computer();
	DefaultComputerSkin skinDummy = new DefaultComputerSkin();
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
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
