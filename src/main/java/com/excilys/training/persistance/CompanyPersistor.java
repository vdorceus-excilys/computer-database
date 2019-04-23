package com.excilys.training.persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;
import java.util.TreeSet;

import com.excilys.training.model.Company;
import com.excilys.training.persistance.db.Database;

public class CompanyPersistor implements Persistor<Company>{
	
	
	private static final String 
					FIND_ALL_QUERY_LAZY="SELECT id, name FROM company",
					FIND_ALL_QUERY_LIMIT="SELECT id, name FROM company LIMIT ?, ?",
					FIND_ALL_QUERY="SELECT id, name FROM company",
					FIND_ONE_QUERY="SELECT id, name FROM company WHERE id = ? LIMIT 1",
					FIND_ONE_QUERY_LAZY="SELECT id, name FROM company WHERE id = ? LIMIT 1",
					CREATE_QUERY ="INSERT INTO company(`id`,`name`) VALUES(?,?)",
					DELETE_QUERY="DELETE FROM company where id = ?",
					UPDATE_QUERY="UPDATE company SET name = ? WHERE id = ?",
					COUNT_QUERY="SELECT COUNT(*)  FROM company"
					;
	
	private final Database database;
	private Boolean lazyStrategy;
	public CompanyPersistor(Database database) {
		this.database = database;
		this.lazyStrategy =  false;
	}

	@Override
	public Set<Company> findAllQuery() {
		Set<Company> companies = new TreeSet<Company>();
		try(Connection connection = database.getConnection()){
			Statement stmt = connection.createStatement();
			ResultSet rset = (!lazyStrategy) ? stmt.executeQuery(FIND_ALL_QUERY) : stmt.executeQuery(FIND_ALL_QUERY_LAZY);
			while (rset.next()) {				
				companies.add(convertResultLine(rset));
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return companies;
	}
	
	@Override
	public Set<Company> findAllQuery(Long offset, Long limit) {
		Set<Company> companies = new TreeSet<Company>();
		try(Connection connection = database.getConnection()){
			String sqlQuery = FIND_ALL_QUERY_LIMIT;
			PreparedStatement stmt = connection.prepareStatement(sqlQuery);
			stmt.setLong(1, offset);
			stmt.setLong(2, limit);
			ResultSet rset = stmt.executeQuery();
			while (rset.next()) {				
				companies.add(convertResultLine(rset));
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return companies;
	}

	@Override
	public void createQuery(Company computer) throws Exception {
		try(Connection connection = database.getConnection()){
			String sqlQuery = CREATE_QUERY;
			PreparedStatement stmt = connection.prepareStatement(sqlQuery);
			stmt.setLong(1, computer.getId());
			stmt.setString(2, computer.getName());
			stmt.execute();						
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	@Override
	public void deleteQuery(Company company) {
		try(Connection connection = database.getConnection()){
			String sqlQuery = DELETE_QUERY;
			PreparedStatement stmt = connection.prepareStatement(sqlQuery);
			stmt.setLong(1,company.getId());
			stmt.execute();			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public  void updateQuery(Company company) {
		try(Connection connection = database.getConnection()){
			String sqlQuery = UPDATE_QUERY;
			PreparedStatement stmt = connection.prepareStatement(sqlQuery);
			stmt.setString(1, company.getName());
			stmt.setLong(2, company.getId());
			stmt.execute();						
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	@Override
	public Company findOneQuery(Long id) {
		Company company = null;
		try(Connection connection = database.getConnection()){
			String sqlQuery = (!lazyStrategy) ? FIND_ONE_QUERY : FIND_ONE_QUERY_LAZY;
			PreparedStatement stmt = connection.prepareStatement(sqlQuery);
			stmt.setLong(1,id);
			ResultSet rset = stmt.executeQuery();
			while (rset.next()) {				
				company = convertResultLine(rset);
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return company;
	}
	
	@Override
	public Long countAll() {
		Long count =0L;
		try(Connection connection = database.getConnection()){
			Statement stmt = connection.createStatement();
			ResultSet rset =  stmt.executeQuery(COUNT_QUERY);
			count = rset.next()  ? rset.getLong(1) : 0L;						
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return count;
	}

	@Override 
	public Company convertResultLine(ResultSet rset) throws SQLException {
		Company company = new Company();
		company.setId(rset.getLong(1));
		company.setName(rset.getString(2));
		return company;
	}

	@Override
	public void setLazyStrategy(Boolean b) {
		// TODO Auto-generated method stub
		this.lazyStrategy = b;
	}

}
