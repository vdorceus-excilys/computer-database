package com.excilys.training.dto;
import com.excilys.training.model.Computer;
import com.excilys.training.validator.Constraint;

public class DefaultComputerSkin implements DataTransferObject<Computer>{
	
	String id="";
	@Constraint(nullable=false,blank=false)
	String name="";
	@Constraint(minSize=10,maxSize=10)
	String introduced="";
	@Constraint(minSize=10,maxSize=10)
	String discontinued="";
	@Constraint(nullable=false,blank=false)
	String company="";

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
	@Override
	public boolean equals(Object o) {
		boolean eq =
				(o!=null)&&
				(this.getClass()==o.getClass())&&
				(this.id.equals(((DefaultComputerSkin)o).getId()))&&
				(this.name.equals(((DefaultComputerSkin)o).getName()))
			;
		//introduced
				if(this.introduced!=null)
					eq = eq && (this.introduced.equals(((DefaultComputerSkin)o).getIntroduced()));
				else if(((DefaultComputerSkin)o).getIntroduced()!=null )
					eq=false;
				//discontinued
				if(this.discontinued!=null)
					eq = eq && (this.discontinued.equals(((DefaultComputerSkin)o).getDiscontinued()));
				else if(((DefaultComputerSkin)o).getDiscontinued()!=null )
					eq=false;
				//company
				if(this.company!=null)
					eq = eq && (this.company.equals(((DefaultComputerSkin)o).getCompany()));
				else if(((DefaultComputerSkin)o).getCompany()!=null )
					eq=false;
		return eq;
	}
	@Override
	public String toString() {
		return id+" "+name;
	}
	
}
