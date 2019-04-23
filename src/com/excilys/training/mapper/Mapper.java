package com.excilys.training.mapper;

public interface Mapper<M,S> {
	
	public M forward(S pojo);
	public S reverse(M pojo);	
	
}
