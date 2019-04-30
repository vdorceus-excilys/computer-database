package com.excilys.training.controller;

import java.util.Set;
import java.util.TreeSet;

import com.excilys.training.dto.DataTransferObject;
import com.excilys.training.dto.DefaultCompanySkin;
import com.excilys.training.mapper.Mapper;
import com.excilys.training.model.Company;
import com.excilys.training.service.Service;
import com.excilys.training.validator.Validator;

public class CompanyController implements Controller<Company> {
	
	private final Service<Company> service;
	private final Mapper<Company,DataTransferObject<Company>> mapper;
	private static CompanyController controller =null;
	
	private  CompanyController(Service<Company> service,Mapper<Company,DataTransferObject<Company>> mapper) {
		this.service =  service;
		this.mapper = mapper;
	}
	
	
	static public CompanyController getInstance(Service<Company> service, Mapper<Company,DataTransferObject<Company>> mapper){
		return (controller!=null) ? controller : (controller = new CompanyController(service,mapper));
	}
	
	@Override
	public DataTransferObject<Company> show(String id) {
		DefaultCompanySkin skin = (DefaultCompanySkin) mapper.reverse(service.findOne(Long.parseLong(id)));
		return skin;
	}

	@Override
	public void delete(DataTransferObject<Company> skin) {
		// TODO Auto-generated method stub
		service.delete(mapper.forward(skin));
		
	}

	@Override
	public void update(DataTransferObject<Company> skin) {
		// TODO Auto-generated method stub
		service.update(mapper.forward(skin));
	}

	@Override
	public void create(DataTransferObject<Company> skin) {
		// TODO Auto-generated method stub
		service.create(mapper.forward(skin));
	}

	@Override
	public Set<DataTransferObject<Company>> list() {
		// TODO Auto-generated method stub
		Set<DataTransferObject<Company>> cs = new TreeSet<DataTransferObject<Company>>();
		for(Company c : service.listAll()) {
			cs.add((DefaultCompanySkin) mapper.reverse(c));
		}
		return cs;
	}
	
	@Override
	public Set<DataTransferObject<Company>> list(Long offset, Long limit) {
		// TODO Auto-generated method stub
		Set<DataTransferObject<Company>> cs = new TreeSet<DataTransferObject<Company>>();
		for(Company c : service.listAll(offset,limit)) {
			cs.add((DefaultCompanySkin) mapper.reverse(c));
		}
		return cs;
	}

	@Override
	public Long count() {		
		return service.count();
	}
	
	

}
