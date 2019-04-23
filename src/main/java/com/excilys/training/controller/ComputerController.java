package com.excilys.training.controller;
import java.util.Set;
import java.util.TreeSet;

import com.excilys.training.mapper.Mapper;
import com.excilys.training.mapper.dto.DataTransferObject;
import com.excilys.training.mapper.dto.DefaultComputerSkin;
import com.excilys.training.model.Computer;
import com.excilys.training.model.validator.Validator;
import com.excilys.training.service.ComputerService;
import com.excilys.training.service.Service;

public class ComputerController implements Controller<Computer>{
	
	private final Service<Computer> service;
	private final Mapper<Computer,DataTransferObject<Computer>> mapper;
	
	public ComputerController(Mapper<Computer,DataTransferObject<Computer>>  mapper, Validator<Computer> validator) {
		this.service =  ComputerService.getInstance();
		this.service.setValidator(validator);
		this.mapper = mapper;
	}

	@Override
	public DataTransferObject<Computer> show(String id) {
		// TODO Auto-generated method stub
		DefaultComputerSkin skin = (DefaultComputerSkin) mapper.reverse(service.findOne(Long.parseLong(id)));
		return skin;
	}

	@Override
	public void delete(DataTransferObject<Computer> skin) {
		// TODO Auto-generated method stub
		service.delete(mapper.forward(skin));
		
	}

	@Override
	public void update(DataTransferObject<Computer> skin) {
		// TODO Auto-generated method stub
		service.update(mapper.forward(skin));
	}

	@Override
	public void create(DataTransferObject<Computer> skin) {
		// TODO Auto-generated method stub
		service.create(mapper.forward(skin));
	}

	@Override
	public Set<DataTransferObject<Computer>> list() {
		// TODO Auto-generated method stub
		Set<DataTransferObject<Computer>> cs = new TreeSet<DataTransferObject<Computer>> ();
		for(Computer c : service.listAll()) {
			cs.add((DefaultComputerSkin) mapper.reverse(c));
		}
		return cs;
	}
	@Override
	public Set<DataTransferObject<Computer>> list(Long offset,Long limit) {
		// TODO Auto-generated method stub
		Set<DataTransferObject<Computer>> cs = new TreeSet<DataTransferObject<Computer>> ();
		for(Computer c : service.listAll(offset,limit)) {
			cs.add((DefaultComputerSkin) mapper.reverse(c));
		}
		return cs;
	}
	@Override
	public Long count() {		
		return service.count();
	}

}
