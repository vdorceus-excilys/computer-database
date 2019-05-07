package com.excilys.training.persistance.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.training.util.ConfigurationProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public abstract class Database {	
	protected static Logger logger = LogManager.getLogger(Database.class);
	
	//Hikari
	private  HikariConfig hikariConfig = new  HikariConfig();
	private  HikariDataSource hikariDataSource;
	
	
	
	ConfigurationProperties config;
	protected String jdbcUrl ;
	protected String dbUsername, dbPassword;
	
	
	public Database(ConfigurationProperties config) {
		this.config = config;
		this.loadDriver();
		this.configHikariCP();
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
	
	private void configHikariCP() {
		hikariConfig.setJdbcUrl(this.config.readProperty("url"));
		hikariConfig.setUsername(this.config.readProperty("username"));
		hikariConfig.setPassword(this.config.readProperty("password"));
		hikariConfig.addDataSourceProperty("cachePrepStmts","true");
		hikariConfig.addDataSourceProperty("prepStmtCacheSize","250");
		hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit",2048);
		hikariDataSource = new  HikariDataSource(hikariConfig);
	}
	
	public Connection getConnection() throws SQLException {
		return  hikariDataSource.getConnection();
	}
	
	public Connection getOldConnection() {
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
