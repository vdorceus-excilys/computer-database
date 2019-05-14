package com.excilys.training.webcontroller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.excilys.training.controller.CompanyController;
import com.excilys.training.controller.ComputerController;
import com.excilys.training.util.ConfigurationProperties;

public class WebController {
	
	
	private ComputerController computerController;
	
	private CompanyController companyController;
	private final Map<String,Map<String,String>> language=new HashMap<>();
	//private static Logger logger = LogManager.getLogger(WebController.class);
	
	
	public WebController(ComputerController computerController, CompanyController companyController,
			ConfigurationProperties english, ConfigurationProperties french
			) {
		this.computerController = computerController;
		this.companyController = companyController;
		language.put("en",english.getAllProperties());
		language.put("fr",french.getAllProperties());
	}
	static public WebApplicationContext context(ServletContext context) {
		WebApplicationContext webContext = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
		return webContext;
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
