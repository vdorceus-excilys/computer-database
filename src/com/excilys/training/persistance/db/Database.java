package com.excilys.training.persistance.db;

import java.sql.Connection;
import java.sql.DriverManager;
import com.excilys.training.util.ConfigurationProperties;

public abstract class Database {
	
	ConfigurationProperties config;
	protected String jdbcUrl ;
	protected String dbUsername, dbPassword;
	
	public Database(ConfigurationProperties config) {
		this.config = config;
		this.loadDriver();
	}
	
	protected void loadDriver() {
		try {
			Class.forName(config.readProperty("driver"));
		}
		catch(ClassNotFoundException exp) {
			//log the bloody exception
			System.err.println("Unable to load Mysql Driver");			
		}
	}
	
	public Connection getConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(config.readProperty("url"),
					config.readProperty("username"),
					config.readProperty("password"));
		}catch(Exception exp) {
			System.err.println("Database encountered a problem");
			System.err.println("Exception" + exp.getMessage());
		}
		return connection;
	}	

}
