package com.excilys.training.model;

import java.util.Date;

public class Computer implements Comparable<Computer> {
	Long id;
	String name;
	Date introduced;
	Date discontinued;
	Company company;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getIntroduced() {
		return introduced;
	}
	public void setIntroduced(Date introduced) {
		this.introduced = introduced;
	}
	public Date getDiscontinued() {
		return discontinued;
	}
	public void setDiscontinued(Date discontinued) {
		this.discontinued = discontinued;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}	
	
	@Override
	public int compareTo(Computer c) {
		
			
		return this.getId().compareTo(c.getId());
	}
	@Override
	public boolean equals(Object c) {
		boolean eq = 
				(c != null) &&
				(c.getClass()==this.getClass()) &&
				(this.id == ((Computer)c).getId()) &&
				(this.name == ((Computer)c).getName()) &&
				(this.introduced == ((Computer)c).getIntroduced()) &&
				(this.discontinued == ((Computer)c).getDiscontinued()) &&
				(this.company == ((Computer)c).getCompany())				
		;
		return eq;
	}
	
	
}
