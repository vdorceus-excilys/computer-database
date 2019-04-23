package com.excilys.training.controller;

import com.excilys.training.mapper.dto.DataTransferObject;
import java.util.Set;

public interface Controller<T> {
	
	public DataTransferObject<T> show(String id);	
	public void delete(DataTransferObject<T> skin);
	public void update(DataTransferObject<T> skin);
	public void create(DataTransferObject<T> skin);
	public Set<DataTransferObject<T>> list();
	public Set<DataTransferObject<T>> list(Long offset, Long limit);
	public Long count();

}
