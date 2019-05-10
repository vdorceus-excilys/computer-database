package com.excilys.training.model;

import com.excilys.training.validator.Constraint;

public class Company implements Comparable<Company> {
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
	public boolean equals(Object c) {
		boolean eq = 
				(c!=null) &&
				(this.getClass().equals(c.getClass())) &&
				(this.id.equals(((Company)c).getId())) &&
				(this.name.equals(((Company)c).getName()))
		;
		return eq;
	}
	@Override
	public String toString() {
		return " ID="+getId()+" NAME="+getName();
	}
}
