package com.excilys.training.model;

public class Company implements Comparable<Company> {
	Long id;
	String name;
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
}
