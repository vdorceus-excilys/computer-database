package com.excilys.training.ui;

import java.io.IOException;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.training.config.AppConfig;
import com.excilys.training.controller.ComputerController;
import com.excilys.training.controller.Controller;
import com.excilys.training.dto.DataTransferObject;
import com.excilys.training.dto.DefaultComputerSkin;
import com.excilys.training.model.Computer;

public class ComputerView implements View<Computer>{
	
	private static Logger logger = LogManager.getLogger(ComputerView.class);
	
	private final Controller<Computer> controller;
	
	public ComputerView() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
    	controller  = (ComputerController) context.getBean("computerCliController");
    	context.close();
		/*
		 * ComputerPersistor computerPersistor = null; CompanyPersistor companyPersistor
		 * =null; try { ConfigurationProperties config = new ConfigurationProperties();
		 * config.load(ConfigurationProperties.DEFAULT_PERSISTANCE_PATH); Database db =
		 * new Mysql(config); computerPersistor = new ComputerPersistor(db);
		 * companyPersistor = new CompanyPersistor(db); } catch(Exception exp) { //log
		 * service exception logger.
		 * error("ERROR WHILE TRYING TO SET COMPUTER CONFIGURATION WITH PROPERTIES",exp)
		 * ; } ComputerService serviceComputer =
		 * ComputerService.getInstance(computerPersistor,new ConstraintValidator());
		 * CompanyService serviceCompany = CompanyService.getInstance(companyPersistor,
		 * new ConstraintValidator()); controller =
		 * ComputerController.getInstance(serviceComputer,new
		 * DefaultComputerMapper(serviceCompany));
		 */
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
			Set<DefaultComputerSkin> cs = (Set<DefaultComputerSkin>) controller.list(offset,limit);
			for(DefaultComputerSkin computer : cs) {
				System.out.println("<"+computer.getId()+"> "+computer.getName()+", par : "+computer.getCompany() +" ["+computer.getIntroduced()+"//"+computer.getDiscontinued()+"]"  );
			}
			
			try{
				Launcher.read();
			}catch(IOException exp) {
				logger.error("IOException occured while trying to read user input from list action in Computer View",exp);
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
		}catch(IOException exp) {
			logger.error("IOException occured while trying to read  user input from show action in Computer view",exp);
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
		}catch(IOException exp) {
			logger.error("IOException while trying to read user input from create in Computer View",exp);
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
		}catch(IOException exp) {
			logger.error("IOException occured while reading user input from update action in Computer View",exp);
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
			logger.error("IOException occured while reading user input from delete action in Computer View",exp);
		}		
		System.out.println("=======================================");			
	}


}
