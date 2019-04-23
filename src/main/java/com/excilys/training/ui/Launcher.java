package com.excilys.training.ui;

import static java.lang.System.in;
import static java.lang.System.out;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.excilys.training.model.Company;
import com.excilys.training.model.Computer;
import com.excilys.training.util.log.SimpleLog;

public class Launcher {
	
	public static Integer pagination =10;
	
	private static View<Company> companyView;
	private static View<Computer> computerView;
	
	static  {
		companyView = new CompanyView();
		computerView = new ComputerView();
	}

	public static void main(String[] args) throws IOException, Exception{		
		SimpleLog.getInstance().debug(true);
		Launcher.menu();
	}
	
	public static String read() throws IOException{
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		return reader.readLine();
	}
	public static void menu() throws Exception{

		out.println("Bienvenue sur le gestionnaire de parc informatique");
		StringBuffer q = new StringBuffer();
		q.append("Tapez  [O] pour entrer dans le contexte Ordinateur\n");
		q.append("Tapez  [F] pour entrer dans le contexte fabriquant\n");
		q.append("Tapez  [P] pour fixer la pagination  | 10 par défault");
		q.append("Tapez  [q] pour quitter le programme");
		String input = "";
		out.println(q);
		input = read();
		while(!input.equals("q")) {
			if(input.equals("O")) {
				//Computer context
				out.println("Vous êtes dans le contexte Ordinateur");
				computerView.menu();				
			}else if(input.equals("F")) {
				//Company context
				out.println("Vous êtes dans le contexte Fabriquant");
				companyView.menu();
			}else if(input.equals("P")) {
				//Company context
				out.println("Vous êtes dans le contexte de configuration de la pagination");
				pageSetting();
			}
			else {
				out.println("Commande non reconnue...");
				out.println("Saisissez une commande reconnue...");				
			}
			out.println(q);
			input=read();
		}
	}
	
	static public void pageSetting() throws Exception{
		out.println("Saisissez une valeur pour la pagination...");	
		String  s  = read();
		Integer i = Integer.parseInt(s);
		Launcher.pagination  = (i>5)  ? i :10;
		out.println("Valeur de la pagination :  "+Launcher.pagination);	
	}
	
	static public int howManyPages(Long count) {
		int nbre  =  (int) (count/Launcher.pagination);
		nbre += (count%Launcher.pagination==0) ? 0 : 1;
		return  nbre;		 
	}
	
	
	
	

}
