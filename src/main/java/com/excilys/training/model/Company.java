package com.excilys.training.model;

import java.util.Objects;

import com.excilys.training.validator.Constraint;

public class Company implements Comparable<Company> , Cloneable{
	@Constraint(clazz="java.lang.Long", minValue=1L,nullable=false)
	private Long id;
	@Constraint(clazz="java.lang.String",minSize=3,maxSize=25,nullable=false,blank=false)
	private String name;
	
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
	
	@Override
	public int compareTo(Company c) {
		
		return this.getId().compareTo(c.getId());
	}

	
	@Override
	public String toString() {
		return "ID="+getId()+" NAME="+getName();
	}
	
	public Company clone() {
		Company company = new Company();
		company.setId(this.id);
		company.setName(this.name);
		return company;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Company)) {
			return false;
		}
		Company other = (Company) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name);
	}
	
	
}
