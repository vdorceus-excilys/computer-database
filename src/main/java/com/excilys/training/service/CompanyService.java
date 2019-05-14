package com.excilys.training.service;


import java.util.Set;

import com.excilys.training.model.Company;
import com.excilys.training.persistance.CompanyPersistor;
import com.excilys.training.validator.Validator;


public class CompanyService extends GenericService<Company>{
	
	
	public CompanyService(CompanyPersistor persistor,Validator<Object> validator) {
		super();	
		this.validator = validator;
		this.persistor =persistor;
	}
	
	
	@Override
	public Company findByAttribut(String att, String value){
		Company company =  null;
		if(att.equals("NAME")) {
			try {
				Set<Company> companyList =  this.persistor.findAllQuery();
				for(Company  c : companyList) {
					if(c.getName().equals(value))
						company =c;
				}
			} catch (Exception exp) {
				// TODO Auto-generated catch block
				logger.error("FAILED TO  FIND MODEL BY ATTRIBUTE NAME",exp);
			}
			
		}
		return company;
	}
	
	
}
