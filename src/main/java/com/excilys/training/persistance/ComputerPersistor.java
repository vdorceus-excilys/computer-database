package com.excilys.training.persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;
import java.util.TreeSet;

import com.excilys.training.model.Company;
import com.excilys.training.model.Computer;
import com.excilys.training.persistance.db.Database;

public class ComputerPersistor implements Persistor<Computer> {
	
	private static final String 
					FIND_ALL_QUERY_LAZY="SELECT id, name, introduced, discontinued, company_id FROM computer",
					FIND_ALL_QUERY="SELECT computer.id, computer.name, introduced, discontinued, company_id,company.name FROM computer left join company on computer.company_id=company.id",
					FIND_ALL_QUERY_LIMIT="SELECT computer.id, computer.name, introduced, discontinued, company_id,company.name FROM computer left join company on computer.company_id=company.id LIMIT ?,?",
					FIND_ONE_QUERY="SELECT computer.id, computer.name, introduced, discontinued, company_id,company.name FROM computer left join company on computer.company_id=company.id WHERE computer.id = ? LIMIT 1",
					FIND_ONE_QUERY_LAZY="SELECT id, name, introduced, discontinued, company_id FROM computer WHERE id = ? LIMIT 1",
					CREATE_QUERY ="INSERT INTO computer(`id`,`name`,`introduced`,`discontinued`,`company_id`) VALUES(?,?,?,?,?)",
					DELETE_QUERY="DELETE FROM computer where computer.id = ?",
					UPDATE_QUERY="UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id  = ? WHERE id = ?",
					COUNT_QUERY="SELECT COUNT(*)  FROM computer"
					;
	
	private final Database database;
	private Boolean lazyStrategy;
	public ComputerPersistor(Database database) {
		this.database = database;
		this.lazyStrategy =  false;
	}

	@Override
	public Set<Computer> findAllQuery() {
		Set<Computer> computers = new TreeSet<Computer>();
		try(Connection connection = database.getConnection()){
			Statement stmt = connection.createStatement();
			ResultSet rset = (!lazyStrategy) ? stmt.executeQuery(FIND_ALL_QUERY) : stmt.executeQuery(FIND_ALL_QUERY_LAZY);
			while (rset.next()) {				
				computers.add(convertResultLine(rset));
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return computers;
	}
	@Override
	public Set<Computer> findAllQuery(Long offset,Long limit) {
		Set<Computer> computers = new TreeSet<Computer>();
		try(Connection connection = database.getConnection()){
			String sqlQuery = FIND_ALL_QUERY_LIMIT;
			PreparedStatement stmt = connection.prepareStatement(sqlQuery);
			stmt.setLong(1, offset);
			stmt.setLong(2, limit);
			ResultSet rset = stmt.executeQuery();
			while (rset.next()) {				
				computers.add(convertResultLine(rset));
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return computers;
	}

	@Override
	public void createQuery(Computer computer) throws Exception {
		try(Connection connection = database.getConnection()){
			String sqlQuery = CREATE_QUERY;
			PreparedStatement stmt = connection.prepareStatement(sqlQuery);
			stmt.setLong(1, computer.getId());
			stmt.setString(2, computer.getName());
			stmt.setDate(3, (computer.getIntroduced()==null)? null : new java.sql.Date(computer.getIntroduced().getTime()));
			stmt.setDate(4, (computer.getDiscontinued()==null)? null :new java.sql.Date(computer.getDiscontinued().getTime()));
			stmt.setLong(5, computer.getCompany().getId());
			stmt.execute();						
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	@Override
	public void deleteQuery(Computer computer) {
		try(Connection connection = database.getConnection()){
			String sqlQuery = DELETE_QUERY;
			PreparedStatement stmt = connection.prepareStatement(sqlQuery);
			stmt.setLong(1,computer.getId());
			stmt.execute();			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public  void updateQuery(Computer computer) {
		try(Connection connection = database.getConnection()){
			String sqlQuery = UPDATE_QUERY;
			PreparedStatement stmt = connection.prepareStatement(sqlQuery);
			stmt.setString(1, computer.getName());
			stmt.setDate(2, (computer.getIntroduced()==null)? null : new java.sql.Date(computer.getIntroduced().getTime()));
			stmt.setDate(3, (computer.getDiscontinued()==null)? null :new java.sql.Date(computer.getDiscontinued().getTime()));
			stmt.setLong(4, computer.getCompany().getId());
			stmt.setLong(5, computer.getId());
			stmt.execute();						
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	@Override
	public Computer findOneQuery(Long id) {
		Computer computer = null;
		try(Connection connection = database.getConnection()){
			String sqlQuery = (!lazyStrategy) ? FIND_ONE_QUERY : FIND_ONE_QUERY_LAZY;
			PreparedStatement stmt = connection.prepareStatement(sqlQuery);
			stmt.setLong(1,id);
			ResultSet rset = stmt.executeQuery();
			while (rset.next()) {				
				computer = convertResultLine(rset);
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return computer;
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
	public Computer convertResultLine(ResultSet rset) throws SQLException {
		Computer computer = new Computer();
		computer.setId(rset.getLong(1));
		computer.setName(rset.getString(2));
		computer.setIntroduced(rset.getDate(3));
		computer.setDiscontinued(rset.getDate(4));
		Company company = new Company();		
		company.setId(rset.getLong(5));
		if(!lazyStrategy)
			company.setName((company.getId()==0L) ? "PAS DE COMPANY" : rset.getString(6));
		computer.setCompany(company);
		return computer;
	}
	
	

	@Override
	public void setLazyStrategy(Boolean b) {
		// TODO Auto-generated method stub
		this.lazyStrategy = b;
	}
	
}


