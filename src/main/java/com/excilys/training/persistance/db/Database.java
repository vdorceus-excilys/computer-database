package com.excilys.training.persistance.db;

import java.sql.Connection;
import java.sql.DriverManager;
import com.excilys.training.util.ConfigurationProperties;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public abstract class Database {
	
	protected static Logger logger = LogManager.getLogger(Database.class);
	
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
			logger.error("Unable to load Mysql Driver",exp);
			//log the bloody exception
			System.err.println();			
		}
	}
	
	public Connection getConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(config.readProperty("url"),
					config.readProperty("username"),
					config.readProperty("password"));
		}catch(Exception exp) {
			logger.error("Database encountered a problem",exp);
		}
		return connection;
	}	

}
