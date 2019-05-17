/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excilus.test.training.mapper;

import com.excilys.training.dto.DefaultComputerSkin;
import com.excilys.training.mapper.DefaultComputerMapper;
import com.excilys.training.model.Company;
import com.excilys.training.model.Computer;
import com.excilys.training.service.CompanyService;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

/**
 *
 * @author excilys
 */
public class DefaultComputerMapperTest {
    
    Computer computerDummy;
	DefaultComputerSkin skinDummy;
	Company companyDummy;
	DefaultComputerMapper mapper;
	SimpleDateFormat sdf;
	
	@Mock CompanyService companyService;
	@Rule public MockitoRule mockitoRule = MockitoJUnit.rule();	

	@Before
	public void setUp() throws Exception {
		sdf = new SimpleDateFormat("dd-MM-yyyy");
		computerDummy=new Computer();
		computerDummy.setId(1L);
		computerDummy.setName("Ordinateur de travail");
		computerDummy.setIntroduced(sdf.parse("24-04-2019"));
		computerDummy.setDiscontinued(sdf.parse("24-04-2019"));
		companyDummy = new Company();
		companyDummy.setId(1L);
		companyDummy.setName("Apple");
		computerDummy.setCompany(companyDummy);
		skinDummy = new DefaultComputerSkin();
		skinDummy.setId("1");
		skinDummy.setName("Ordinateur de travail");
		skinDummy.setIntroduced("24-04-2019");
		skinDummy.setDiscontinued("24-04-2019");
		skinDummy.setCompany("Apple");
		
		mapper = new DefaultComputerMapper(companyService);
	}

	@Test
	public void forwardTest() {
		when(companyService.findByAttribut(anyString(),anyString())).thenReturn(companyDummy);
		assertEquals(computerDummy,mapper.forward(skinDummy));
	}
	
	@Test
	public void reverseTest() {
		assertEquals(skinDummy,mapper.reverse(computerDummy));
	}
	
	
    
}
