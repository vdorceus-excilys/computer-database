package com.excilus.test.training.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.excilys.training.controller.CompanyController;
import com.excilys.training.dto.DataTransferObject;
import com.excilys.training.dto.DefaultCompanySkin;
import com.excilys.training.mapper.DefaultCompanyMapper;
import com.excilys.training.model.Company;
import com.excilys.training.service.CompanyService;
import com.excilys.training.validator.ConstraintValidator;

public class CompanyControllerTest {

	@Mock CompanyService service;
	@Mock DefaultCompanyMapper mapper;
	ConstraintValidator validator;
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
		validator = new ConstraintValidator();
		//when(mapper.forward(skinDummy)).thenReturn(companyDummy);
		//when(mapper.reverse(companyDummy)).thenReturn(skinDummy);
		//when(service.findByAttribut(anyString(),anyString())).thenReturn(companyDummy);
		controller = new CompanyController(service,mapper);
	}


	@Test
	public void showMethod() {
		when(service.findOne(anyLong())).thenReturn(companyDummy);
		when(mapper.reverse(companyDummy)).thenReturn(skinDummy);
		DefaultCompanySkin computerDTO = (DefaultCompanySkin) controller.show("1");
		assertEquals(computerDTO,skinDummy);
	}
	
	@Test
	public void deleteMethod() {
		when(service.delete(any(Company.class))).thenReturn(true);
		when(mapper.forward(skinDummy)).thenReturn(companyDummy);
		controller.delete(skinDummy);
		return;		
	}
	
	@Test
	public void createMethod() {
		when(service.create(any(Company.class))).thenReturn(true);
		when(mapper.forward(skinDummy)).thenReturn(companyDummy);
		controller.create(skinDummy);
		return;
	}
	
	@Test
	public void updateMethod() {
		when(service.update(any(Company.class))).thenReturn(true);
		when(mapper.forward(skinDummy)).thenReturn(companyDummy);
		controller.update(skinDummy);
		return;
	}
	@Test
	public void simpleListMethod() {
		when(service.listAll()).thenReturn(new TreeSet<Company>(Arrays.asList(new Company[] {companyDummy})));
		when(mapper.reverse(companyDummy)).thenReturn(skinDummy);
		Set<DataTransferObject<Company>> listComputerDTO = controller.list();
		assertEquals(1,listComputerDTO.size());
		assertTrue(listComputerDTO.contains(skinDummy));		
	}
	@Test
	public void pagedListMethod() {
		when(service.listAll(anyLong(),anyLong())).thenReturn(new TreeSet<Company>(Arrays.asList(new Company[] {companyDummy})));
		when(mapper.reverse(companyDummy)).thenReturn(skinDummy);
		Set<DataTransferObject<Company>> listComputerDTO = controller.list(1L,2L);
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
