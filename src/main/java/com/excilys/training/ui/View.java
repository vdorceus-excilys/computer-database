package com.excilys.training.ui;
import java.io.IOException;

public interface View<T>{
	public void menu() throws IOException;
	public void show();
	public void list();
	public void create();
	public void update();
	public void delete();
	
}
