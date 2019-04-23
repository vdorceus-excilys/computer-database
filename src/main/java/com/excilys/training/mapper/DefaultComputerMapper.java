package com.excilys.training.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.excilys.training.mapper.dto.DataTransferObject;
import com.excilys.training.mapper.dto.DefaultComputerSkin;
import com.excilys.training.model.Company;
import com.excilys.training.model.Computer;
import com.excilys.training.service.CompanyService;
import com.excilys.training.service.Service;

public class DefaultComputerMapper implements Mapper<Computer,DataTransferObject<Computer>> {
	
	private SimpleDateFormat sdf;
	private Service<Company> companyService;
	
	public DefaultComputerMapper() {		
		sdf = new SimpleDateFormat("dd-MM-yyyy");
		this.companyService  = CompanyService.getInstance();
	}

	@Override
	public Computer forward(DataTransferObject<Computer> po) {
		// TODO Auto-generated method stub
		DefaultComputerSkin pojo = (DefaultComputerSkin) po;
		Computer computer = new Computer();
		computer.setId(Long.parseLong(pojo.getId()));
		computer.setName(pojo.getName());	
		try {
			String a=pojo.getIntroduced().trim(),b =pojo.getDiscontinued().trim();
			computer.setIntroduced((a==null || a.isEmpty())? null : sdf.parse(a));
			computer.setDiscontinued((b==null ||a.isEmpty())? null : sdf.parse(b));
		}catch(ParseException exp) {
			System.err.println(exp.getMessage());
		}
		computer.setCompany(companyService.findByAttribut("NAME",pojo.getCompany()));
		System.out.println("printing id of company linked with new computer"+computer.getCompany().getId());
		return computer;
	}

	@Override
	public DataTransferObject<Computer> reverse(Computer pojo) {
		// TODO Auto-generated method stub
		DefaultComputerSkin skin =  new DefaultComputerSkin();
		skin.setId(pojo.getId().toString());
		skin.setName(pojo.getName());
		
		skin.setIntroduced( (pojo.getIntroduced()!=null) ? sdf.format(pojo.getIntroduced()) : null );		
		skin.setDiscontinued( (pojo.getDiscontinued()!=null) ? sdf.format(pojo.getDiscontinued()) : null  );
		
		skin.setCompany(pojo.getCompany().getName());
		return skin;
	}

}
