package com.excilys.training.service;


import java.util.Set;

import com.excilys.training.model.Company;
import com.excilys.training.model.validator.Validator;
import com.excilys.training.persistance.CompanyPersistor;
import com.excilys.training.persistance.db.Database;
import com.excilys.training.persistance.db.Mysql;
import com.excilys.training.util.ConfigurationProperties;


public class CompanyService extends GenericService<Company>{
	
	static private CompanyService self=null;
	
	private CompanyService(CompanyPersistor persistor,Validator<Company> validator) {
		super();		
	}
	
	public static CompanyService getInstance(CompanyPersistor persistor, Validator<Company> validator) {
		self = (self!=null)? self : (self=new CompanyService(persistor,validator));
		self.validator = validator;
		self.persistor = persistor;
		return self;
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
