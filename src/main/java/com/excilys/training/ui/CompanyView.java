package com.excilys.training.ui;

import java.io.IOException;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.training.config.AppConfig;
import com.excilys.training.controller.CompanyController;
import com.excilys.training.controller.Controller;
import com.excilys.training.dto.DataTransferObject;
import com.excilys.training.dto.DefaultCompanySkin;
import com.excilys.training.model.Company;

public class CompanyView implements View<Company> {
	
	private static Logger logger = LogManager.getLogger(CompanyView.class);
	private final Controller<Company> controller;
	
	public CompanyView() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
    	controller  = (CompanyController) context.getBean("computerCliController");
    	context.close();
		/*
		 * CompanyPersistor persistor=null; try { ConfigurationProperties config = new
		 * ConfigurationProperties();
		 * config.load(ConfigurationProperties.DEFAULT_PERSISTANCE_PATH); Database db =
		 * new Mysql(config); persistor = new CompanyPersistor(db); } catch(Exception
		 * exp) { //log service exception logger.
		 * error("ERROR WHILE TRYING TO SET COMPANY CONFIGURATION WITH PROPERTIES",exp);
		 * } CompanyService service = CompanyService.getInstance(persistor,new
		 * ConstraintValidator()); controller =
		 * CompanyController.getInstance(service,new DefaultCompanyMapper());
		 */
	}
	
	@Override
	public void menu() throws IOException{
		StringBuffer q = new StringBuffer();
		System.out.println("FABRICANT: ");
		q.append("Tapez  [1] pour lister\n");
		q.append("Tapez  [2] pour ajouter un nouveau fabricant\n");
		q.append("Tapez  [3] pour supprimer un fabricant\n");
		q.append("Tapez  [4] pour voir les détails d'un fabricant\n");
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
		System.out.println("Vous avez quittez le contexte fabriquant \n");
	}
	
	@Override
	public void show() {
		System.out.println("Saisissez un identifiant d'Ordinateur");
		try {
			String id = Launcher.read();
			DefaultCompanySkin company = (DefaultCompanySkin) controller.show(id);
			System.out.println("<"+company.getId()+"> "+company.getName()  );
		}catch(Exception exp) {
			logger.error("Une erreur s'est produite dans la vue Company",exp);
		}
		
		System.out.println("=======================================");
	}
	
	@Override
	public void list() {
		System.out.println("Voici la liste des fabricant d'Ordinateurs");
		Long count = controller.count();
		int nbrePage  = Launcher.howManyPages(count);
		for(Long i=0L; i<nbrePage; i++) {
			System.out.println("Page "+(i+1)+" sur "+nbrePage);
			Long offset =  i*Launcher.pagination;
			Long limit = 1L * Launcher.pagination;
			Set<DataTransferObject<Company>> cs = controller.list(offset,limit);
			for(DataTransferObject<Company> comp : cs) {
				DefaultCompanySkin company = (DefaultCompanySkin) comp;
				System.out.println("<"+company.getId()+"> "+company.getName() );
			}
			
			try{
				Launcher.read();
			}catch(Exception exp) {
				logger.error("Failed to read user input",exp);
			}
		}
		System.out.println("====================================");
	}


	@Override
	public void create() {
		System.out.println("CONTEXTE: ENREGISTRER UN NOUVEAU FABRICANT");
		System.out.println("Saisissez en une ligne les informations suivantes séparées par une virgule:");
		System.out.println("ID, NAME");
		try {
			String companyString = Launcher.read();
			String[] companyParams = companyString.split(",");
			DefaultCompanySkin company = new DefaultCompanySkin();
			company.setId(companyParams[0]);
			company.setName(companyParams[1]);
			controller.create(company);
		}catch(Exception exp) {
			logger.error("Failed to create DTO object in company view",exp);
		}		
		System.out.println("=======================================");		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		System.out.println("CONTEXTE: MODIFIER UN FABRICANT EXISTANT");
		System.out.println("Saisissez en une ligne les informations suivantes séparées par une virgule:");
		System.out.println("ID, NAME");
		System.out.println("L'ID DOIT ÊTRE EXISTANT");
		try {
			String companyString = Launcher.read();
			String[] companyParams = companyString.split(",");
			DefaultCompanySkin company = new DefaultCompanySkin();
			company.setId(companyParams[0]);
			company.setName(companyParams[1]);
			controller.update(company);
		}catch(Exception exp) {
			logger.error("Une erreur s'est produite",exp);
		}		
		System.out.println("=======================================");			
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		System.out.println("CONTEXTE: SUPPRIMER UN FABRICANT");
		System.out.println("Saisissez L'IDENTIFIANT");
		System.out.println("L'ID DOIT DÉJÀ EXISTER");
		try {
			String companyString = Launcher.read();
			DefaultCompanySkin company = new DefaultCompanySkin();
			company.setId(companyString);
			controller.delete(company);
		}catch(Exception exp) {
			logger.error("Une erreur s'est produite",exp);
		}		
		System.out.println("=======================================");	
	}


}
