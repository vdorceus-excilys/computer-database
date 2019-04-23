package com.excilys.training.ui;

import java.io.IOException;
import java.util.Set;

import com.excilys.training.controller.ComputerController;
import com.excilys.training.controller.Controller;
import com.excilys.training.mapper.DefaultComputerMapper;
import com.excilys.training.mapper.dto.DataTransferObject;
import com.excilys.training.mapper.dto.DefaultComputerSkin;
import com.excilys.training.model.Computer;
import com.excilys.training.model.validator.ComputerDefaultValidator;

public class ComputerView implements View<Computer>{
	
	private final Controller<Computer> controller;
	
	public ComputerView() {
		controller = new ComputerController(new DefaultComputerMapper(), new ComputerDefaultValidator());
	}
	
	@Override 
	public void menu() throws IOException{
		StringBuffer q = new StringBuffer();
		System.out.println("Ordinateurs: ");
		q.append("Tapez  [1] pour lister\n");
		q.append("Tapez  [2] pour ajouter un nouveau\n");
		q.append("Tapez  [3] pour supprimer un ordinateur\n");
		q.append("Tapez  [4] pour voir les détails d'un ordinateur\n");
		q.append("Tapez  [q] pour quitter le contexte actuel\n");
		System.out.println(q);
		String command = Launcher.read();
		while(!command.equals("q")) {
			switch(command) {
			case "1" : 
				this.list(); break;
			case "2" : 
				this.create(); break;
			case "3" : 
				this.delete(); break;
			case "4" : 
				this.show(); break;
			default:
					System.out.println("Commande non reconnue");
			}
			System.out.println(q);
			command = Launcher.read();
		}
		System.out.println("Vous avez quittez le contexte ordinateur \n");
	}
	
	@Override
	public void list() {
		System.out.println("Voici la liste des Ordinateurs");
		Long count = controller.count();
		int nbrePage  = Launcher.howManyPages(count);
		for(Long i=0L; i<nbrePage; i++) {
			System.out.println("Page "+(i+1)+" sur "+nbrePage);
			Long offset =  i*Launcher.pagination;
			Long limit = 1L * Launcher.pagination;
			Set<DataTransferObject<Computer>> cs = controller.list(offset,limit);
			for(DataTransferObject<Computer> comp : cs) {
				DefaultComputerSkin computer = (DefaultComputerSkin) comp;
				System.out.println("<"+computer.getId()+"> "+computer.getName()+", par : "+computer.getCompany() +" ["+computer.getIntroduced()+"//"+computer.getDiscontinued()+"]"  );
			}
			
			try{
				Launcher.read();
			}catch(Exception exp) {
				
			}
		}
		System.out.println("====================================");
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		System.out.println("Saisissez un identifiant d'Ordinateur");
		try {
			String id = Launcher.read();
			DefaultComputerSkin computer = (DefaultComputerSkin) controller.show(id);
			System.out.println("<"+computer.getId()+"> "+computer.getName()+", par : "+computer.getCompany() +" ["+computer.getIntroduced()+"//"+computer.getDiscontinued()+"]"  );
		}catch(Exception exp) {
			System.err.println("Une erreur s'est produite");
		}
		
		System.out.println("=======================================");
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub
		System.out.println("CONTEXTE: ENREGISTRER UN NOUVEL ORDINATEUR");
		System.out.println("Saisissez en une ligne les informations suivantes séparées par une virgule:");
		System.out.println("ID, NAME, DATE-INTRO, DATE-RETRAIT, COMPANY-NAME");
		System.out.println("FORMAT DE DATE ACCEPTÉ : JJ-MM-AAAA");
		try {
			String computerString = Launcher.read();
			String[] computerParams = computerString.split(",");
			DefaultComputerSkin computer = new DefaultComputerSkin();
			computer.setId(computerParams[0]);
			computer.setName(computerParams[1]);
			computer.setIntroduced(computerParams[2]);
			computer.setDiscontinued(computerParams[3]);
			computer.setCompany(computerParams[4]);
			controller.create(computer);
		}catch(Exception exp) {
			System.err.println("Une erreur s'est produite");
		}		
		System.out.println("=======================================");		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		System.out.println("CONTEXTE: MODIFIER UN ORDINATEUR EXISTANT");
		System.out.println("Saisissez en une ligne les informations suivantes séparées par une virgule:");
		System.out.println("ID, NAME, DATE-INTRO, DATE-RETRAIT, COMPANY-NAME");
		System.out.println("L'ID DOIT ÊTRE EXISTANT");
		System.out.println("FORMAT DE DATE ACCEPTÉ : JJ-MM-AAAA");
		try {
			String computerString = Launcher.read();
			String[] computerParams = computerString.split(",");
			DefaultComputerSkin computer = new DefaultComputerSkin();
			computer.setId(computerParams[0]);
			computer.setName(computerParams[1]);
			computer.setIntroduced(computerParams[2]);
			computer.setDiscontinued(computerParams[3]);
			computer.setCompany(computerParams[4]);
			controller.update(computer);
		}catch(Exception exp) {
			System.err.println("Une erreur s'est produite");
		}		
		System.out.println("=======================================");	
		
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		System.out.println("CONTEXTE: SUPPRIMER UN ORDINATEUR");
		System.out.println("Saisissez L'IDENTIFIANT");
		System.out.println("L'ID DOIT DÉJÀ EXISTER");
		try {
			String computerString = Launcher.read();
			DefaultComputerSkin computer = new DefaultComputerSkin();
			computer.setId(computerString);
			controller.delete(computer);
		}catch(Exception exp) {
			System.err.println("Une erreur s'est produite");
		}		
		System.out.println("=======================================");			
	}


}
