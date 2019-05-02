package com.excilys.training.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.training.dto.DataTransferObject;
import com.excilys.training.dto.DefaultComputerSkin;
import com.excilys.training.model.Company;
import com.excilys.training.model.Computer;
import com.excilys.training.service.CompanyService;
import com.excilys.training.service.Service;

public class WebComputerMapper implements Mapper<Computer,DataTransferObject<Computer>> {

	private static Logger logger = LogManager.getLogger(DefaultComputerMapper.class);
	private SimpleDateFormat americanDateFormat;
	private Service<Company> companyService;
	
	public WebComputerMapper(CompanyService service) {		
		
		americanDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		this.companyService  = service;
	}

	@Override
	public Computer forward(DataTransferObject<Computer> po) {
		// TODO Auto-generated method stub
		DefaultComputerSkin pojo = (DefaultComputerSkin) po;
		Computer computer = new Computer();
		computer.setId((pojo.getId()!=null && !pojo.getId().isEmpty()) ? Long.parseLong(pojo.getId()) :-1L);
		computer.setName(pojo.getName());	
		try {
			String a=pojo.getIntroduced().trim().replace("/","-");
			String b =pojo.getDiscontinued().trim().replace("/","-");
			
			computer.setIntroduced((a==null || a.isEmpty())? null : americanDateFormat.parse(a));
			computer.setDiscontinued((b==null ||a.isEmpty())? null : americanDateFormat.parse(b));
		}catch(ParseException exp) {
			logger.error("exception parsing date",exp);			
		}
		computer.setCompany(companyService.findByAttribut("NAME",pojo.getCompany()));
		return computer;
	}

	@Override
	public DataTransferObject<Computer> reverse(Computer pojo) {
		// TODO Auto-generated method stub
		DefaultComputerSkin skin =  new DefaultComputerSkin();
		skin.setId(pojo.getId().toString());
		skin.setName(pojo.getName());
		
		skin.setIntroduced( (pojo.getIntroduced()!=null) ? americanDateFormat.format(pojo.getIntroduced()) : null );		
		skin.setDiscontinued( (pojo.getDiscontinued()!=null) ? americanDateFormat.format(pojo.getDiscontinued()) : null  );
		if(pojo.getCompany()!=null)
			skin.setCompany(pojo.getCompany().getName());
		return skin;
	}
}
