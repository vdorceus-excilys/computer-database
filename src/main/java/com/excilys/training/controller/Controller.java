package com.excilys.training.controller;

import java.util.Set;

import com.excilys.training.dto.DataTransferObject;

public interface Controller<T> {
	
	public DataTransferObject<T> show(String id);	
	public void delete(DataTransferObject<T> skin);
	public void update(DataTransferObject<T> skin);
	public void create(DataTransferObject<T> skin);
	public Set<DataTransferObject<T>> list();
	public Set<DataTransferObject<T>> list(Long offset, Long limit);
	public Set<DataTransferObject<T>> list(Long offset, Long limit,String orderBy, Boolean order);
	public Set<DataTransferObject<T>> list(String search);
	public Long count();
}
