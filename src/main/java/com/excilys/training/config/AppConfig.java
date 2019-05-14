package com.excilys.training.config;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.excilys.training.controller.CompanyController;
import com.excilys.training.controller.ComputerController;
import com.excilys.training.mapper.DefaultCompanyMapper;
import com.excilys.training.mapper.DefaultComputerMapper;
import com.excilys.training.mapper.WebCompanyMapper;
import com.excilys.training.mapper.WebComputerMapper;
import com.excilys.training.persistance.CompanyPersistor;
import com.excilys.training.persistance.ComputerPersistor;
import com.excilys.training.persistance.db.Database;
import com.excilys.training.service.CompanyService;
import com.excilys.training.service.ComputerService;
import com.excilys.training.util.ConfigurationProperties;
import com.excilys.training.validator.ConstraintValidator;
import com.excilys.training.webcontroller.WebController;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class AppConfig {
	private static Logger logger = LogManager.getLogger(AppConfig.class);
	
	
	
	@Bean(name="defaultConfig")
	public ConfigurationProperties defaultConfig() {
		ConfigurationProperties  config = new ConfigurationProperties();
		try {
			config.load(ConfigurationProperties.DEFAULT_PERSISTANCE_PATH);
		}catch(IOException exp) {
			logger.error("Error importing configuration",exp);
		}		
		return config;
	}
	@Bean(name="testConfig")
	public ConfigurationProperties testConfig() {
		ConfigurationProperties  config = new ConfigurationProperties();
		try {
			config.load(ConfigurationProperties.DEFAULT_PERSISTANCE_PATH);
		}catch(IOException exp) {
			logger.error("Error importing configuration",exp);
		}		
		return config;
	}
	
	
	
	@Bean
	public ConfigurationProperties englishConfig() {
		ConfigurationProperties  config = new ConfigurationProperties();
		try {
			config.load("i19n/en.properties");
		}catch(IOException exp) {
			logger.error("Error importing configuration",exp);
		}		
		return config;
	}
	@Bean
	public ConfigurationProperties frenchConfig() {
		ConfigurationProperties  config = new ConfigurationProperties();
		try {
			config.load("i19n/fr.properties");
		}catch(IOException exp) {
			logger.error("Error importing configuration",exp);
		}		
		return config;
	}
	
	@Bean(name="defaultDatabase")
	public Database defaultDatabase() {
		ConfigurationProperties config = defaultConfig();
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setDriverClassName(config.readProperty("driver"));
		hikariConfig.setJdbcUrl(config.readProperty("url"));
		hikariConfig.setUsername(config.readProperty("username"));
		hikariConfig.setPassword(config.readProperty("password"));
		hikariConfig.setMaximumPoolSize(20);
		hikariConfig.addDataSourceProperty("cachePrepStmts","true");
		hikariConfig.addDataSourceProperty("prepStmtCacheSize","250");
		hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit",2048);
		HikariDataSource hikariDataSource = new  HikariDataSource(hikariConfig);
		return new Database(hikariDataSource);
	}
	@Bean(name="testDatabase")
	public Database testDatabase() {
		ConfigurationProperties config = defaultConfig();
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setDriverClassName(config.readProperty("driver"));
		hikariConfig.setJdbcUrl(config.readProperty("url"));
		hikariConfig.setUsername(config.readProperty("username"));
		hikariConfig.setPassword(config.readProperty("password"));
		hikariConfig.setMaximumPoolSize(20);
		hikariConfig.addDataSourceProperty("cachePrepStmts","true");
		hikariConfig.addDataSourceProperty("prepStmtCacheSize","250");
		hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit",2048);
		HikariDataSource hikariDataSource = new  HikariDataSource(hikariConfig);
		return new Database(hikariDataSource);
	}
	
	@Bean
	public ComputerPersistor computerPersistor() {		
		return new ComputerPersistor(defaultDatabase());
	}
	@Bean
	public CompanyPersistor companyPersistor() {
		return new CompanyPersistor(defaultDatabase());
	}
	
	@Bean
	public ConstraintValidator validator() {
		return new ConstraintValidator();
	}
	@Bean
	public ComputerService computerService() {
		return new ComputerService(computerPersistor(),validator());
	}
	@Bean
	public CompanyService companyService() {
		return new CompanyService(companyPersistor(),validator());
	}
	@Bean
	public WebComputerMapper webComputerMapper() {
		return new WebComputerMapper(companyService());
	}
	@Bean
	public WebCompanyMapper webCompanyMapper() {
		return new WebCompanyMapper();
	}
	@Bean
	public DefaultComputerMapper defaultComputerMapper() {
		return new DefaultComputerMapper(companyService());
	}
	@Bean
	public DefaultCompanyMapper  defaultCompanyMapper() {
		return new DefaultCompanyMapper();
	}
	
	@Bean(name="computerWebController")
	ComputerController computerWebController() {
		return new ComputerController(computerService(),webComputerMapper());
	}
	@Bean(name="companyWebController")
	CompanyController companyWebController() {
		return new CompanyController(companyService(),webCompanyMapper());
	}
	@Bean(name="computerCliController")
	ComputerController computerCliController() {
		return new ComputerController(computerService(),defaultComputerMapper());
	}
	@Bean(name="companyCliController")
	CompanyController companyCliController() {
		return new CompanyController(companyService(),defaultCompanyMapper());
	}
	@Bean
	WebController webController() {
		return new WebController(computerWebController(),companyWebController(),englishConfig(),frenchConfig());
	}

}
