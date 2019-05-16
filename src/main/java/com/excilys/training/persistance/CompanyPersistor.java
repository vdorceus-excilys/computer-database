package com.excilys.training.persistance;

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

public class CompanyPersistor implements Persistor<Company>{
	
	

	private static final String FIND_ALL_QUERY_LIMIT="SELECT `id`, `name` FROM `computer-database-db`.`company` LIMIT ?, ?";
	private static final String FIND_ALL_QUERY_ORDERED="SELECT `id`, `name` FROM `computer-database-db`.`company` ORDER BY #columnOrder #directionOrder LIMIT ?, ?";
	private static final String FIND_ALL_QUERY="SELECT `id`, `name` FROM `computer-database-db`.`company`";
	private static final String SEARCH_ALL_QUERY="SELECT `id`, `name` FROM `computer-database-db`.`company` WHERE `company`.`name` LIKE ?";
	private static final String FIND_ONE_QUERY="SELECT `id`, `name` FROM `computer-database-db`.`company` WHERE id = ? LIMIT 1";
	
	private static final String CREATE_QUERY ="INSERT INTO `computer-database-db`.`company`(`id`,`name`) VALUES(?,?)";
	private static final String DELETE_QUERY="DELETE FROM `computer-database-db`.`company` where `id` = ?";
	private static final String DELETE_COMPUTER_WHERE_QUERY="DELETE FROM `computer-database-db`.`computer` where `company_id` = ?";
	private static final String UPDATE_QUERY="UPDATE `computer-database-db`.`company` SET `name` = ? WHERE `id` = ?";
	private static final String COUNT_QUERY="SELECT COUNT(*)  FROM `computer-database-db`.`company`";
	
	private static Logger logger = LogManager.getLogger(CompanyPersistor.class);
	
	static Map<String,String> accepted = new HashMap<>();
	static {
		accepted.put("id","`company`.`id`");
		accepted.put("name","`company`.`name`");
	}
	
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	
	public CompanyPersistor(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(this.dataSource);
	}

	@Override
	public Set<Company> findAllQuery() {
		Set<Company> companies = new TreeSet<Company>();
		try {
			companies.addAll( jdbcTemplate.query(FIND_ALL_QUERY, new CompanyRowMapper()	));
		}catch(DataAccessException exp) {
			logger.error("COMPANY : FIND ALL QUERY / DataAccessException",exp);
		}
		return companies;
	}
	
	@Override
	public Set<Company> findAllQuery(Long offset, Long limit) {
		Set<Company> companies = new TreeSet<Company>();
		try {
			companies.addAll(jdbcTemplate.query(FIND_ALL_QUERY_LIMIT,new Object[] {offset,limit}, new CompanyRowMapper()));
		}catch(DataAccessException exp) {
			logger.error("COMPANY : FIND ALL QUERY WITH OFFSET AND LIMIT / DataAccessException",exp);
		}				
		return companies;
	}
	@Override
	public Set<Company> findAllQueryOrdered(Long offset, Long limit, String att, Boolean asc) {
		if(!accepted.containsKey(att)) {
			return findAllQuery(offset, limit);
		}else {
			Set<Company> companies = new TreeSet<>();
			try {
				String sqlQuery = FIND_ALL_QUERY_ORDERED;
				sqlQuery = sqlQuery.replace("#columnOrder",accepted.get(att));
				sqlQuery = sqlQuery.replace("#directionOrder",asc? "ASC" : "DESC");
				companies.addAll(jdbcTemplate.query(sqlQuery,new Object[] {offset,limit}, new CompanyRowMapper()));
			}catch(DataAccessException exp) {
				logger.error("COMPANY : FIND ALL QUERY WITH OFFSET AND LIMIT + ORDER/ DataAccessException",exp);
			}				
			return companies;
		}
	}
	@Override
	public Set<Company> searchQuery(String search) {
		search = "%"+search+"%";
		Set<Company> companies = new TreeSet<Company>();
		companies.addAll(jdbcTemplate.query(SEARCH_ALL_QUERY,new Object[] {search}, new CompanyRowMapper()));		
		return companies;
	}
	@Override
	public void createQuery(Company computer) throws Exception {		
		jdbcTemplate.update(CREATE_QUERY,computer.getId(),computer.getName());		
	}
	
	@Transactional
	@Override
	public void deleteQuery(Company company) {
		jdbcTemplate.update(DELETE_COMPUTER_WHERE_QUERY,company.getId());
		jdbcTemplate.update(DELETE_QUERY,company.getId());		
	}
	
	@Override
	public  void updateQuery(Company company) {
		jdbcTemplate.update(UPDATE_QUERY,company.getName(),company.getId());
	}
	
	@Override
	public Company findOneQuery(Long id) {
		return jdbcTemplate.queryForObject(FIND_ONE_QUERY, new Object[] {id}, new CompanyRowMapper());
	}
	
	@Override
	public Long countAll() {
		return jdbcTemplate.queryForObject(COUNT_QUERY,Long.class);		
	}

}

class CompanyRowMapper implements RowMapper<Company>{
	@Override
	public Company mapRow(ResultSet rset, int rowNum) throws SQLException {
		Company company = new Company();
		company.setId(rset.getLong("id"));
		company.setName(rset.getString("name"));
		return company;
	}		
}







