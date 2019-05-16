package com.excilys.training.model;

import java.util.Date;
import java.util.Objects;

import com.excilys.training.validator.Constraint;

public class Computer implements Comparable<Computer>, Cloneable {
	
	@Constraint(clazz="java.lang.Long", minValue=1L,nullable=false)
	private Long id;
	@Constraint(clazz="java.lang.String",minSize=3,maxSize=25,nullable=false,blank=false)
	private String name;
	@Constraint(clazz="java.util.Date",ref="introduced",minDate="1-01-1970",maxDate="now")
	private Date introduced;
	@Constraint(clazz="java.util.Date",greaterThan="introduced",maxDate="now")
	private Date discontinued;
	@Constraint(clazz = "com.excilys.training.model.Company")
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
	public String toString() {
		
		return "ID="+getId()+" NAME="+getName()+" INTRO="+getIntroduced()+" DISC="+getDiscontinued()+" COMPANY="+getCompany();
	}
	
	public Computer clone() {
		Computer computer = new Computer();
		computer.setId(this.id);
		computer.setName(this.name);
		computer.setIntroduced(this.introduced);
		computer.setDiscontinued(this.discontinued);
		computer.setCompany(this.company==null ? null : this.company.clone());
		return computer;
	}
	@Override
	public int hashCode() {
		return Objects.hash(company, discontinued, id, introduced, name);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Computer)) {
			return false;
		}
		Computer other = (Computer) obj;
		return Objects.equals(company, other.company) && Objects.equals(discontinued, other.discontinued)
				&& Objects.equals(id, other.id) && Objects.equals(introduced, other.introduced)
				&& Objects.equals(name, other.name);
	}
	
	
}
