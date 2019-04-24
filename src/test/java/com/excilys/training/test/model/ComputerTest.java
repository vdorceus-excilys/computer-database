package com.excilys.training.test.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.excilys.training.model.Company;
import com.excilys.training.model.Computer;

public class ComputerTest {

	Computer computerA = new Computer();
	Computer computerB = new Computer();
	Computer computerC = new Computer();
	Computer computerEmpty = new Computer();
	Company companyA = new Company();
	Company companyB = new Company();

	@Before
	public void setUp() throws Exception {
		companyA.setId(1L);companyA.setName("A");
		companyB.setId(2L);companyB.setName("B");
		computerA.setId(1L);computerA.setName("A");computerA.setCompany(companyA);
		computerB.setId(2L);computerB.setName("B");computerB.setCompany(companyB);
		computerC.setId(2L);computerC.setName("B");computerC.setCompany(companyB);	
	}

	@Test
	public void EqualsWithValues() {
		assertEquals(computerA,computerA);
		assertEquals(computerB,computerC);
		assertEquals(computerC,computerB);
	}
	@Test
	public void EqualsWithDifferentValues() {
		assertNotEquals(computerA,computerB);
		assertNotEquals(computerA,computerEmpty);
	}

}
