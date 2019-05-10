package com.excilys.training.webcontroller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.training.controller.CompanyController;
import com.excilys.training.controller.ComputerController;
import com.excilys.training.mapper.WebCompanyMapper;
import com.excilys.training.mapper.WebComputerMapper;
import com.excilys.training.persistance.CompanyPersistor;
import com.excilys.training.persistance.ComputerPersistor;
import com.excilys.training.persistance.db.Database;
import com.excilys.training.persistance.db.Mysql;
import com.excilys.training.service.CompanyService;
import com.excilys.training.service.ComputerService;
import com.excilys.training.util.ConfigurationProperties;
import com.excilys.training.validator.ConstraintValidator;

public class WebController {
	
	private static WebController self=null;
	private ComputerController computerController;
	private CompanyController companyController;
	private final Map<String,Map<String,String>> language=new HashMap<>();
	private static Logger logger = LogManager.getLogger(WebController.class);
	
	
	static public WebController getInstance() {
		return (self!=null) ? self : (self= new WebController());
	}
	
	
	
	private WebController() {
		ComputerPersistor computerPersistor = null;
    	CompanyPersistor companyPersistor =null;
    	try {
			ConfigurationProperties config = new ConfigurationProperties();
			ConfigurationProperties english=new ConfigurationProperties();
			ConfigurationProperties french=new ConfigurationProperties();
			config.load(ConfigurationProperties.DEFAULT_PERSISTANCE_PATH);
			english.load("i19n/en.properties");
			french.load("i19n/fr.properties");
			language.put("en",english.getAllProperties());
			language.put("fr",french.getAllProperties());
			Database db = new Mysql(config);
			computerPersistor = new ComputerPersistor(db);
			companyPersistor = new CompanyPersistor(db);
		}
		catch(IOException exp) {
			logger.error("Error importing configuration",exp);
		}
		 ComputerService computerService = ComputerService.getInstance(computerPersistor,new ConstraintValidator());
		 CompanyService companyService = CompanyService.getInstance(companyPersistor, new ConstraintValidator());
		 computerController = ComputerController.getInstance(computerService,new WebComputerMapper(companyService));
		 companyController = CompanyController.getInstance(companyService, new WebCompanyMapper());
		
	}
	
	public ComputerController getComputerController() {
		return computerController;
	}
	public CompanyController getCompanyController() {
		return companyController;
	}
	public Map<String,Map<String,String>> language(){
		return language;
	}
	
	
	private Long pagination=10L;
	
	public Long calculatePages(Long count) {
		Long nbre  =   (count/pagination);
		nbre += (count%pagination==0) ? 0 : 1;
		return  nbre;		 
	}
	
	public void setPagination(Long nbre) {
		pagination  = (nbre>5)  ? nbre :10;
	}
	public Long getPagination() {
		return pagination;
	}

}
