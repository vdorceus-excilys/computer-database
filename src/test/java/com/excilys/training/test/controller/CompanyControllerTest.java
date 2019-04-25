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

import com.excilys.training.controller.CompanyController;
import com.excilys.training.mapper.DefaultCompanyMapper;
import com.excilys.training.mapper.DefaultComputerMapper;
import com.excilys.training.mapper.dto.DataTransferObject;
import com.excilys.training.mapper.dto.DefaultCompanySkin;
import com.excilys.training.mapper.dto.DefaultComputerSkin;
import com.excilys.training.model.Company;
import com.excilys.training.model.Computer;
import com.excilys.training.model.validator.CompanyDefaultValidator;
import com.excilys.training.model.validator.ComputerDefaultValidator;
import com.excilys.training.service.CompanyService;

public class CompanyControllerTest {

	@Mock CompanyService service;
	@Mock DefaultCompanyMapper mapper;
	@Mock CompanyDefaultValidator validator;
	Company companyDummy= new Company();
	DefaultCompanySkin skinDummy ;
	CompanyController controller;
	@Rule public MockitoRule mockitoRule = MockitoJUnit.rule();	
	
	
	public void dummy() {
		companyDummy.setId(1L);
		companyDummy.setName("Ordinateur de travail");
		skinDummy = new DefaultCompanySkin();
		skinDummy.setId("1");
		skinDummy.setName("Ordinateur de travail");
	}


	@Before
	public void setUp() throws Exception {
		this.dummy();
		mapper = mock(DefaultCompanyMapper.class);
		validator = mock(CompanyDefaultValidator.class);
		when(mapper.forward(skinDummy)).thenReturn(companyDummy);
		when(mapper.reverse(companyDummy)).thenReturn(skinDummy);
		doNothing().when(validator).validate(companyDummy);
		when(service.update(any(Company.class))).thenReturn(true);
		when(service.create(any(Company.class))).thenReturn(true);
		when(service.delete(any(Company.class))).thenReturn(true);
		when(service.count()).thenReturn(1L);
		when(service.listAll()).thenReturn(new TreeSet<Company>(Arrays.asList(new Company[] {companyDummy})));
		when(service.listAll(anyLong(),anyLong())).thenReturn(new TreeSet<Company>(Arrays.asList(new Company[] {companyDummy})));
		when(service.findOne(anyLong())).thenReturn(companyDummy);
		when(service.findByAttribut(anyString(),anyString())).thenReturn(companyDummy);
		controller = CompanyController.getInstance(service,mapper);
	}


	@Test
	public void showMethod() {
		DefaultCompanySkin computerDTO = (DefaultCompanySkin) controller.show("1");
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
		Set<DataTransferObject<Company>> listComputerDTO = controller.list();
		assertEquals(1,listComputerDTO.size());
		assertTrue(listComputerDTO.contains(skinDummy));		
	}
	@Test
	public void pagedListMethod() {
		Set<DataTransferObject<Company>> listComputerDTO = controller.list(1L,2L);
		assertEquals(1,listComputerDTO.size());
		assertTrue(listComputerDTO.contains(skinDummy));		
	}
	@Test
	public void countMethod() {
		Long count = controller.count();
		
	}
	
}
