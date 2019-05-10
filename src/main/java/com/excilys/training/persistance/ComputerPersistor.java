package com.excilys.training.persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.training.model.Company;
import com.excilys.training.model.Computer;
import com.excilys.training.persistance.db.Database;

public class ComputerPersistor implements Persistor<Computer> {
	
	private static Logger logger = LogManager.getLogger(ComputerPersistor.class);
	
	private static final String 
					FIND_ALL_QUERY_LAZY="SELECT `id`, `name`, `introduced`, `discontinued`, company_id FROM `computer-database-db`.computer",
					FIND_ALL_QUERY="SELECT `computer`.`id`, `computer`.`name`, `introduced`, `discontinued`, `company_id`,`company`.`name` FROM `computer-database-db`.`computer` left join `computer-database-db`.`company` on `computer`.`company_id`=`company`.`id`",
					SEARCH_ALL_QUERY="SELECT `computer`.`id`, `computer`.`name`, `introduced`, `discontinued`, `company_id`,`company`.`name` FROM `computer-database-db`.`computer` left join `computer-database-db`.`company` on `computer`.`company_id`=`company`.`id` WHERE `computer`.`name` LIKE ?",
					FIND_ALL_QUERY_LIMIT="SELECT `computer`.`id`, `computer`.`name`, `introduced`, `discontinued`, `company_id`,`company`.`name` FROM `computer-database-db`.`computer` left join `computer-database-db`.`company` on `computer`.`company_id`=`company`.`id` LIMIT ?,?",
					FIND_ALL_QUERY_ORDERED="SELECT `computer`.`id`, `computer`.`name`, `introduced`, `discontinued`, `company_id`,`company`.`name` FROM `computer-database-db`.`computer` left join `computer-database-db`.`company` on `computer`.`company_id`=`company`.`id` ORDER BY #columnOrder #directionOrder LIMIT ?,?",							
					FIND_ONE_QUERY="SELECT `computer`.`id`, `computer`.`name`, `introduced`, `discontinued`, `company_id`,`company`.`name` FROM `computer-database-db`.`computer` left join `computer-database-db`.`company` on `computer`.`company_id`=`company`.`id` WHERE `computer`.`id` = ? LIMIT 1",
					FIND_ONE_QUERY_LAZY="SELECT `id`, `name`, `introduced`, `discontinued`, `company_id` FROM `computer-database-db`.`computer` WHERE `id` = ? LIMIT 1",
					CREATE_QUERY ="INSERT INTO `computer-database-db`.`computer`(`id`,`name`,`introduced`,`discontinued`,`company_id`) VALUES(?,?,?,?,?)",
					DELETE_QUERY="DELETE FROM `computer-database-db`.`computer` where `computer`.`id` = ?",
					UPDATE_QUERY="UPDATE `computer-database-db`.`computer` SET `name` = ?, `introduced` = ?, `discontinued` = ?, `company_id`  = ? WHERE `id` = ?",
					COUNT_QUERY="SELECT COUNT(*)  FROM `computer-database-db`.`computer`"
					;
	static Map<String,String> accepted = new HashMap<>();
	static {
		accepted.put("id","`computer`.`id`");
		accepted.put("name","`computer`.`name`");
		accepted.put("introduced","`computer`.`introduced`");
		accepted.put("discontinued","`computer`.`discontinued`");
		accepted.put("companyId","`company`.`id`");
		accepted.put("companyName","`company`.`name`");
	}
	
	
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
			logger.error("SQL Exception while calling FIND_ALL_QUERY",e);
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
			logger.error("SQL Exception while calling FIND_ALL_QUERY_WITH_LIMIT",e);
		}		
		return computers;
	}
	@Override
	public Set<Computer> findAllQueryOrdered(Long offset, Long limit, String att,Boolean asc) {
		if(!accepted.containsKey(att)) {
			return findAllQuery(offset,limit);
		}else {
			Set<Computer> computers = new TreeSet<Computer>();
			try(Connection connection = database.getConnection()){
				String sqlQuery = FIND_ALL_QUERY_ORDERED;
				sqlQuery = sqlQuery.replace("#columnOrder",accepted.get(att));
				sqlQuery = sqlQuery.replace("#directionOrder",asc? "ASC" : "DESC");
				PreparedStatement stmt = connection.prepareStatement(sqlQuery);
				//stmt.setString(1,accepted.get(att));
				//stmt.setString(2, asc? "ASC" : "DESC");
				stmt.setLong(1, offset);
				stmt.setLong(2, limit);
				ResultSet rset = stmt.executeQuery();
				while (rset.next()) {				
					computers.add(convertResultLine(rset));
				}			
			} catch (SQLException e) {
				logger.error("SQL Exception while calling FIND_ALL_QUERY_ORDERED",e);
			}		
			return computers;
		}		
	}
	@Override
	public Set<Computer> searchQuery(String search) {
		Set<Computer> computers = new TreeSet<Computer>();
		try(Connection connection = database.getConnection()){
			String sqlQuery = SEARCH_ALL_QUERY;
			PreparedStatement stmt = connection.prepareStatement(sqlQuery);
			stmt.setString(1, search);
			ResultSet rset = stmt.executeQuery();
			while (rset.next()) {				
				computers.add(convertResultLine(rset));
			}			
		} catch (SQLException e) {
			logger.error("SQL Exception while calling FIND_ALL_QUERY_WITH_LIMIT",e);
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
			if(computer.getCompany()!=null && computer.getCompany().getId()!=null) {
				stmt.setLong(5,computer.getCompany().getId());
			}else {
				stmt.setNull(5,java.sql.Types.DOUBLE);
			}			
			stmt.execute();						
		} catch (SQLException e) {
			logger.error("SQL Exception while calling CREATE_QUERY",e);
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
			logger.error("SQL Exception while calling DELETE_QUERY",e);
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
			if(computer.getCompany()!=null && computer.getCompany().getId()!=null) {
				stmt.setLong(4,computer.getCompany().getId());
			}else {
				stmt.setNull(4, java.sql.Types.DOUBLE);
			}
			stmt.setLong(5, computer.getId());
			stmt.execute();						
		} catch (SQLException e) {
			logger.error("SQL Exception while calling UPDATE_QUERY",e);
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
			logger.error("SQL Exception while calling FIND_ONE_QUERY",e);
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
			logger.error("SQL Exception while calling COUNT_QUERY",e);
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
			company.setName(rset.getString(6));
		if(company.getId()!=0)
			computer.setCompany(company);
		
		return computer;
	}
	
	

	@Override
	public void setLazyStrategy(Boolean b) {
		this.lazyStrategy = b;
	}

	

	
	
}


