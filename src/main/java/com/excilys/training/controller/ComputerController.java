package com.excilys.training.controller;
import java.util.Set;
import java.util.TreeSet;

import com.excilys.training.dto.DataTransferObject;
import com.excilys.training.dto.DefaultComputerSkin;
import com.excilys.training.mapper.Mapper;
import com.excilys.training.model.Computer;
import com.excilys.training.service.Service;

public class ComputerController implements Controller<Computer>{
	
	
	private final Service<Computer> service;
	private final Mapper<Computer,DataTransferObject<Computer>> mapper;
	private static ComputerController controller=null;
	
	public ComputerController(Service<Computer> service,Mapper<Computer,DataTransferObject<Computer>>  mapper) {
		this.service =  service;
		this.mapper = mapper;
	}
	
	
	static public ComputerController getInstance(Service<Computer> service,Mapper<Computer,DataTransferObject<Computer>> mapper){
		return (controller!=null) ? controller : (controller = new ComputerController(service,mapper));
	}

	@Override
	public DataTransferObject<Computer> show(String id) {
		System.out.println(id);
		return  mapper.reverse(service.findOne(Long.parseLong(id)));
	}

	@Override
	public void delete(DataTransferObject<Computer> skin) {
		service.delete(mapper.forward(skin));
		
	}

	@Override
	public void update(DataTransferObject<Computer> skin) {
		service.update(mapper.forward(skin));
	}

	@Override
	public void create(DataTransferObject<Computer> skin) {
		service.create(mapper.forward(skin));
	}

	@Override
	public Set<DefaultComputerSkin> list() {
		// TODO Auto-generated method stub
		Set<DefaultComputerSkin> cs = new TreeSet<> ();
		for(Computer c : service.listAll()) {
			cs.add((DefaultComputerSkin) mapper.reverse(c));
		}
		return cs;
	}
	@Override
	public Set<DefaultComputerSkin> list(Long offset,Long limit) {
		// TODO Auto-generated method stub
		Set<DefaultComputerSkin> cs = new TreeSet<> ();
		for(Computer c : service.listAll(offset,limit)) {
			cs.add((DefaultComputerSkin) mapper.reverse(c));
		}
		return cs;
	}
	@Override
	public Set<DefaultComputerSkin> list(Long offset, Long limit, String orderBy, Boolean order) {
		Set<DefaultComputerSkin> cs = new TreeSet<> ();
		for(Computer c : service.orderedListAll(offset,limit,orderBy, order)) {
			cs.add((DefaultComputerSkin) mapper.reverse(c));
		}
		return cs;
	}
	@Override
	public Set<DefaultComputerSkin> list(String search) {
		Set<DefaultComputerSkin> cs = new TreeSet<> ();
		for(Computer c : service.listSearch(search)) {
			cs.add((DefaultComputerSkin) mapper.reverse(c));
		}
		return cs;
	}
	
	@Override
	public Long count() {		
		return service.count();
	}


	


	

	

}
