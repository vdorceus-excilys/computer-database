package com.excilys.training.webcontroller.spring;

import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.excilys.training.controller.CompanyController;
import com.excilys.training.controller.ComputerController;
import com.excilys.training.dto.DataTransferObject;
import com.excilys.training.dto.DefaultComputerSkin;
import com.excilys.training.model.Computer;
import com.excilys.training.validator.ConstraintValidator;
import com.excilys.training.validator.exception.FailedValidationException;
import com.excilys.training.webcontroller.WebController;

@Controller
@RequestMapping("/")
public class ComputerSpringController {
	
	@Resource(name="computerWebController")
	ComputerController computerController;
	@Resource(name="companyWebController")
	CompanyController companyController;
	@Autowired
	WebController webController;
	private static Logger logger = LogManager.getLogger(ComputerSpringController.class);
	private final String COMPUTER_LIST_VIEW="list-computer";
	private final String COMPUTER_ADD_VIEW = "add-computer";
	private final String COMPUTER_EDIT_VIEW = "edit-computer";
	
	@GetMapping("/computer")
	public String index(HttpSession session, ModelMap model) {
		return list(session,model);
	}
	
	@GetMapping(value="/computer/list")
	public String list(HttpSession session, ModelMap  model) {
		String paginationString = (String) model.get("pagination");
		Long pagination = (paginationString==null) ? 10 : Long.valueOf(paginationString);
		pagination = (pagination>100 || pagination<10) ? 10 : pagination;
		webController.setPagination(pagination);
		
		Long limit = webController.getPagination();
		Long nbPages = webController.calculatePages(computerController.count());
		String page = (String) model.get("page");
		
		Long currentPage = (page==null) ? 1 : Long.valueOf(page); 
		currentPage = (currentPage>=nbPages || currentPage<1)? 1 : currentPage;
		model.put("lang",webController.language().get("fr"));
		model.put("nbPages",nbPages);
		
		Set<DataTransferObject<Computer>> computers = null;
		String orderBy = (String) model.get("orderBy");
		String search = (String) model.get("search");
		if(search!=null) {
			computers = computerController.list(search);			
			
		}else if(orderBy!=null && session.getAttribute("orderBy")!=null) {
			if(!orderBy.equals(session.getAttribute("orderBy"))) {
				session.setAttribute("orderBy",orderBy);
				session.setAttribute("order","ASC");
				computers = computerController.list(--currentPage*limit,limit,orderBy,true);
			}else {
				String order = (String) session.getAttribute("order");
				order = order==null ? "ASC" : (order.equals("ASC")) ? "DESC" : "ASC";
				session.setAttribute("order",order);				
				computers = computerController.list(--currentPage*limit,limit,orderBy,order.equals("ASC"));				
			}
		}else if(session.getAttribute("orderBy")!=null) {
			String order = (String) session.getAttribute("order");
			order = order==null ? "ASC" : (order.equals("ASC")) ? "DESC" : "ASC";
			computers = computerController.list(--currentPage*limit,limit,orderBy,order.equals("ASC"));
			
		}else if(orderBy != null) {
			session.setAttribute("orderBy",orderBy);
			session.setAttribute("order","ASC");
			computers = computerController.list(--currentPage*limit,limit,orderBy,true);
			
		}else {
			computers =computerController.list(--currentPage*limit,limit);
		}		
		model.put("computers", computers);
		model.put("count", computerController.count());
		return COMPUTER_LIST_VIEW;
	}
	
	
	@GetMapping(value="/computer/edit")
	public String editGet(HttpSession session, ModelMap model) {
		String idComputer =(String) model.get("idComputer");
		DefaultComputerSkin computer = (DefaultComputerSkin) computerController.show(idComputer);
		model.put("lang",webController.language().get("fr"));
		model.put("computer",computer);
		model.put("companyList",companyController.list());
		return COMPUTER_EDIT_VIEW;
	}
	
	@PostMapping(value="/computer/edit")
	public String editPost(HttpSession session, ModelMap model) {
		String id = (String) model.get("newComputerId");
		String name = (String) model.get("newComputerName");
		String introduced = (String)  model.get("newIntroducedDate");
		String discontinued = (String) model.get("newDiscontinuedDate");
		String company = (String)("newComputerCompanyName");
		DefaultComputerSkin computer = new DefaultComputerSkin();
		computer.setId(id);
		computer.setName(name);
		computer.setIntroduced(introduced);
		computer.setDiscontinued(discontinued);
		computer.setCompany(company);
		try {
			new ConstraintValidator().validate(computer);
			computerController.update(computer);
		}
		catch(FailedValidationException exp) {
			logger.error("DTO Validation Error",exp);
			model.put("error","Validation Error");
		}		
		model.put("lang",webController.language().get("fr"));
		return COMPUTER_LIST_VIEW;
	}
	
	
	@GetMapping("/computer/add")
	public String addGet(HttpSession session, ModelMap model) {
		model.put("lang",webController.language().get("fr"));
		model.put("companyList",companyController.list());
		return COMPUTER_ADD_VIEW;
	}
	@PostMapping("/computer/add")
	public String addPost(HttpSession session, ModelMap model) {
		String name = (String) model.get("newComputerName");
		String introduced = (String) model.get("newIntroducedDate");
		String discontinued = (String) model.get("newDiscontinuedDate");
		String company = (String) model.get("newComputerCompanyName");
		
		DefaultComputerSkin computer = new DefaultComputerSkin();
		computer.setId("-100000");
		computer.setName(name);
		computer.setIntroduced(introduced);
		computer.setDiscontinued(discontinued);
		computer.setCompany(company);
		try {
			new ConstraintValidator().validate(computer);
			computerController.create(computer);
		}
		catch(FailedValidationException exp) {
			logger.error("DTO Validation Error",exp);
			model.put("error","Validation Error");
		}				
		model.put("lang",webController.language().get("fr"));
		return COMPUTER_LIST_VIEW;
	}
	@PostMapping("/computer/delete")
	public String delete(ModelMap model) {
		String idComputer = (String) model.get("idComputer");
		DefaultComputerSkin computer = new DefaultComputerSkin();
		computer.setId(idComputer);
		computerController.delete(computer);
		model.put("lang",webController.language().get("fr"));
		return COMPUTER_LIST_VIEW;
	}
	
	
}
