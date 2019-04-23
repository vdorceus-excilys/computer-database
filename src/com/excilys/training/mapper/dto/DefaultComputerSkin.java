package com.excilys.training.mapper.dto;
import com.excilys.training.model.Computer;

public class DefaultComputerSkin implements DataTransferObject<Computer>{
	String id, name, introduced, discontinued, company;

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

	public String getIntroduced() {
		return introduced;
	}

	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}

	public String getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@Override
	public int compareTo(DataTransferObject<Computer> o) {
		DefaultComputerSkin sk = (DefaultComputerSkin) o;
		
		Long self=Long.parseLong(this.getId()),  other=Long.parseLong(sk.getId());
		return self.compareTo(other);
	}
	
}
