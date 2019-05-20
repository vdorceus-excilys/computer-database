package com.excilus.test.training.config;

import java.io.IOException;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.excilys.training.model.Company;
import com.excilys.training.model.Computer;
import com.excilys.training.util.ConfigurationProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration	
public class TestConfig {
	
	private static Logger logger = LogManager.getLogger(TestConfig.class);
	private static final  String FAILED_TO_IMPORT_CONFIG= "Error importing configuration";
	
	@Bean(name="testConfigurationProperties")
	public ConfigurationProperties testConfigurationProperties() {
		ConfigurationProperties  config = new ConfigurationProperties();
		try {
			config.load(ConfigurationProperties.H2_TEST_PERSISTANCE_PATH);
		}catch(IOException exp) {
			logger.error(FAILED_TO_IMPORT_CONFIG,exp);
		}		
		return config;
	}

	@Bean(name="testDataSource")
	public DataSource testDataSource() {
		ConfigurationProperties config = testConfigurationProperties();
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setDriverClassName(config.readProperty("driver"));
		hikariConfig.setJdbcUrl(config.readProperty("url"));
		hikariConfig.setUsername(config.readProperty("username"));
		hikariConfig.setPassword(config.readProperty("password"));
		hikariConfig.setMaximumPoolSize(20);
		hikariConfig.addDataSourceProperty("cachePrepStmts","true");
		hikariConfig.addDataSourceProperty("prepStmtCacheSize","250");
		hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit",2048);
		return new  HikariDataSource(hikariConfig);
	}
	
	
	@Bean(name="correctComputerA")
	public Computer correctComputerA(){
		Computer computer = new Computer();
		computer.setId(1000L);
		computer.setName("ComputerA");
		Company company = new Company();
		company.setId(1L);
		company.setName("APPLE");
		//computer.setCompany(company);
		return computer;
	}
	@Bean(name="correctComputerB")
	public Computer correctComputerB(){
		Computer computer = new Computer();
		computer.setId(2000L);
		computer.setName("ComputerB");
		Company company = new Company();
		company.setId(2L);
		company.setName("DELL");
		//computer.setCompany(company);
		return computer;
	}
	@Bean(name="correctCompanyA")
	public Company correctCompanyA() {
		Company company = new Company();
		company.setId(1L);
		company.setName("A");
		return company;
	}
	@Bean(name="correctCompanyB")
	public Company correctCompanyB() {
		Company company = new Company();
		company.setId(2L);
		company.setName("B");
		return company;
	}
	
}
