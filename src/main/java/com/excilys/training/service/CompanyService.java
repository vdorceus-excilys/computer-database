package com.excilys.training.service;


import java.util.Set;

import com.excilys.training.model.Company;
import com.excilys.training.persistance.CompanyPersistor;
import com.excilys.training.persistance.db.Database;
import com.excilys.training.persistance.db.Mysql;
import com.excilys.training.util.ConfigurationProperties;


public class CompanyService extends GenericService<Company>{
	
	static private CompanyService self=null;
	
	private CompanyService() {
		super();
		try {
			ConfigurationProperties config = new ConfigurationProperties();
		config.load(ConfigurationProperties.DEFAULT_PERSISTANCE_PATH);		
		Database db = new Mysql(config);
		this.persistor = new CompanyPersistor(db);
		}
		catch(Exception exp) {
			//log service exception
			logger.error("ERROR WHILE TRYING TO SET COMPANY CONFIGURATION WITH PROPERTIES",exp);
		}
	}
	
	public static CompanyService getInstance() {
		return (self!=null)? self : (self=new CompanyService());
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
