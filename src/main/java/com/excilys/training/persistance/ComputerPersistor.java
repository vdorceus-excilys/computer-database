package com.excilys.training.persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.training.model.Company;
import com.excilys.training.model.Computer;

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
	
	
	private final DataSource database;
	private Boolean lazyStrategy;
	private JdbcTemplate jdbcTemplate;
	public ComputerPersistor(DataSource database) {
		this.database = database;
		this.lazyStrategy =  false;
		this.jdbcTemplate = new JdbcTemplate(database);
	}

	@Override
	public Set<Computer> findAllQuery() {
		Set<Computer> computers = new TreeSet<>();
		
		try {
			computers.addAll(jdbcTemplate.query(FIND_ALL_QUERY, new ComputerRowMapper()));
		}catch(DataAccessException exp) {
			logger.error("SQL Exception while calling FIND_ALL_QUERY",exp);
		}				
		return computers;
	}
	
	@Override
	public Set<Computer> findAllQuery(Long offset,Long limit) {
		Set<Computer> computers = new TreeSet<>();
		try {
			computers.addAll(jdbcTemplate.query(FIND_ALL_QUERY_LIMIT,new Object[] {offset,limit}, new ComputerRowMapper()));
		}catch(DataAccessException exp) {
			logger.error("SQL Exception while calling FIND_ALL_QUERY_WITH_LIMIT",exp);
		}		
		return computers;
	}
	@Override
	public Set<Computer> findAllQueryOrdered(Long offset, Long limit, String att,Boolean asc) {
		if(!accepted.containsKey(att)) {
			return findAllQuery(offset,limit);
		}else {
			Set<Computer> computers = new TreeSet<>();
			try {
				String sqlQuery = FIND_ALL_QUERY_ORDERED;
				sqlQuery = sqlQuery.replace("#columnOrder",accepted.get(att));
				sqlQuery = sqlQuery.replace("#directionOrder",asc? "ASC" : "DESC");
				computers.addAll(jdbcTemplate.query(sqlQuery, new Object[] {offset,limit}, new ComputerRowMapper()));
			}catch(DataAccessException exp) {
				logger.error("SQL Exception while calling FIND_ALL_QUERY_ORDERED",exp);
			}	
			return computers;
		}		
	}
	@Override
	public Set<Computer> searchQuery(String search) {
		search = "%"+search+"%";
		Set<Computer> computers = new TreeSet<>();
		computers.addAll(jdbcTemplate.query(SEARCH_ALL_QUERY, new Object[] {search}, new ComputerRowMapper()));				
		return computers;
	}

	@Override
	public void createQuery(Computer computer) throws Exception {
		Long companyId = computer.getCompany()==null ? null : computer.getCompany().getId();
		jdbcTemplate.update(CREATE_QUERY,computer.getId(),computer.getName(),computer.getIntroduced(),computer.getDiscontinued(),companyId);		
	}
	
	@Override
	public void deleteQuery(Computer computer) {
		jdbcTemplate.update(DELETE_QUERY,computer.getId());
	}
	
	@Override
	public  void updateQuery(Computer computer) {
		Long companyId = computer.getCompany()==null ? null : computer.getCompany().getId();
		jdbcTemplate.update(UPDATE_QUERY,computer.getName(),computer.getIntroduced(),computer.getDiscontinued(),companyId,computer.getId());
	}
	
	@Override
	public Computer findOneQuery(Long id) {
		return jdbcTemplate.queryForObject(FIND_ONE_QUERY, new Object[] {id}, new ComputerRowMapper());		
	}
	
	@Override
	public Long countAll() {
		return jdbcTemplate.queryForObject(COUNT_QUERY,Long.class);
	}
	
}

class ComputerRowMapper implements RowMapper<Computer>{

	@Override
	public Computer mapRow(ResultSet rset, int rowNum) throws SQLException {
		Computer computer = new Computer();
		computer.setId(rset.getLong(1));
		computer.setName(rset.getString(2));
		computer.setIntroduced(rset.getDate(3));
		computer.setDiscontinued(rset.getDate(4));
		Company company = new Company();		
		company.setId(rset.getLong(5));
		company.setName(rset.getString(6));
		if(company.getId()>0)
			computer.setCompany(company);		
		return computer;
	}
	
}









