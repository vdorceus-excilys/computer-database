package com.excilys.training.mapper.dto;

import com.excilys.training.model.Company;

public class DefaultCompanySkin implements DataTransferObject<Company> {
	private String id, name;

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

	

	
	
}
