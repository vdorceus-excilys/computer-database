package com.excilys.training.dto;

import com.excilys.training.model.Company;
import com.excilys.training.validator.Constraint;

public class DefaultCompanySkin implements DataTransferObject<Company> {
	private String id ="";
	@Constraint(nullable=false,blank=false)
	private String name="";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(DataTransferObject<Company> o) {
		// TODO Auto-generated method stub
		DefaultCompanySkin sk = (DefaultCompanySkin) o;
		
		Long self=Long.parseLong(this.getId()),  other=Long.parseLong(sk.getId());
		return self.compareTo(other);
	}
	
	@Override
	public boolean equals(Object o) {
		boolean eq =
				(o!=null)&&
				(this.getClass()==o.getClass())&&
				(this.id == ((DefaultCompanySkin)o).getId())&&
				(this.name == ((DefaultCompanySkin)o).getName())
			;
		return eq;
	}

	

	
	
}
