package com.excilys.training.ui;

import java.io.IOException;
import java.util.Set;

import com.excilys.training.controller.CompanyController;
import com.excilys.training.controller.Controller;
import com.excilys.training.mapper.DefaultCompanyMapper;
import com.excilys.training.mapper.dto.DataTransferObject;
import com.excilys.training.mapper.dto.DefaultCompanySkin;
import com.excilys.training.model.Company;
import com.excilys.training.model.validator.CompanyDefaultValidator;

public class CompanyView implements View<Company> {
	private final Controller<Company> controller;
	
	public CompanyView() {
		controller = new CompanyController(new DefaultCompanyMapper(), new CompanyDefaultValidator());
	}
	
	@Override
	public void menu() throws IOException{
		StringBuffer q = new StringBuffer();
		System.out.println("FABRICANT: ");
		q.append("Tapez  [1] pour lister\n");
		q.append("Tapez  [2] pour ajouter un nouveau\n");
		q.append("Tapez  [3] pour supprimer un ordinateur\n");
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
			System.err.println("Une erreur s'est produite");
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
			System.err.println("Une erreur s'est produite");
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
			System.err.println("Une erreur s'est produite");
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
			System.err.println("Une erreur s'est produite");
		}		
		System.out.println("=======================================");	
	}


}
