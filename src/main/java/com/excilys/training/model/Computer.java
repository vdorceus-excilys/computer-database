package com.excilys.training.model;

import java.util.Date;

import com.excilys.training.validator.annotation.Constraint;

public class Computer implements Comparable<Computer> {
	
	@Constraint(clazz="java.lang.Long", minValue=1L,nullable=false)
	private Long id;
	@Constraint(clazz="java.lang.String",minSize=3,maxSize=25,nullable=false,blank=false)
	private String name;
	@Constraint(clazz="java.util.Date",ref="introduced",minDate="1-01-1970",maxDate="now")
	private Date introduced;
	@Constraint(clazz="java.util.Date",greaterThan="introduced",maxDate="now")
	private Date discontinued;
	private Company company;
	
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
				(c.getClass().equals(this.getClass())) &&
				(this.id.equals(((Computer)c).getId())) &&
				(this.name.equals(((Computer)c).getName()))			
		;
		//introduced
		if(this.introduced!=null)
			eq = eq && (this.introduced.equals(((Computer)c).getIntroduced()));
		else if(((Computer)c).getIntroduced()!=null )
			eq=false;
		//discontinued
		if(this.discontinued!=null)
			eq = eq && (this.discontinued.equals(((Computer)c).getDiscontinued()));
		else if(((Computer)c).getDiscontinued()!=null )
			eq=false;
		//company
		if(this.company!=null)
			eq = eq && (this.company.equals(((Computer)c).getCompany()));
		else if(((Computer)c).getCompany()!=null )
			eq=false;
		return eq;
	}
	@Override
	public String toString() {
		
		return "ID="+getId()+" NAME="+getName()+" INTRO="+getIntroduced()+" DISC="+getDiscontinued()+" COMPANY="+getCompany();
	}
	
	
	
	
}
