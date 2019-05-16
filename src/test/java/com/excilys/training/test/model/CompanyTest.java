package com.excilys.training.test.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.excilys.training.model.Company;

public class CompanyTest {



	Company companyA = new Company();
	Company companyB = new Company();
	Company companyC = new Company();
	Company companyEmpty = new Company();

	@Before
	public void setUp() throws Exception {
		companyA.setId(1L);companyA.setName("A");
		companyB.setId(2L);companyB.setName("B");
		companyC.setId(1L);companyC.setName("A");
	}
	
	@Test
	public void checkingToStringRepresentation() {
		String expectedA = "ID=1 NAME=A";
		String expectedB = "ID=2 NAME=B";
		String expectedC = "ID=1 NAME=A";
		assertEquals(expectedA,companyA.toString());
		assertEquals(expectedB,companyB.toString());
		assertEquals(expectedC,companyC.toString());
	}

	
	@Test
	public void EqualsWithValues() {
		assertEquals(companyA,companyA);
		assertEquals(companyA,companyC);
	}
	@Test
	public void EqualsWithDifferentValues() {
		assertNotEquals(companyA,companyB);
		assertNotEquals(companyC,companyB);
	}

}
