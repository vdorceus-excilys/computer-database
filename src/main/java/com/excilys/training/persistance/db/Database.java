package com.excilys.training.persistance.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class Database extends JdbcTemplate {	
	
	private DataSource hikariDataSource;
	public Database(DataSource dataSource) {
		super(dataSource);
		hikariDataSource = dataSource;
	}
	public Connection getConnection() throws SQLException {
		return  hikariDataSource.getConnection();
	}
}
