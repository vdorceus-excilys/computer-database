package com.excilys.training.webcontroller.spring;

import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.training.controller.CompanyController;
import com.excilys.training.controller.ComputerController;
import com.excilys.training.dto.DefaultComputerSkin;
import com.excilys.training.validator.ConstraintValidator;
import com.excilys.training.validator.exception.FailedValidationException;
import com.excilys.training.webcontroller.WebController;

@Controller

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
	
		
	@GetMapping({"/computer","/","/computer/index"})
	public String list(HttpSession session, 
			@RequestParam(value="page",required=false) String page,
			@RequestParam(value="OrderBy",required=false) String orderBy,
			@RequestParam(value="search",required=false) String search,
			@RequestParam(value="pagination", required=false) String  paginationString,
			ModelMap  model) {
		Long pagination = (paginationString==null) ? 10 : Long.valueOf(paginationString);
		pagination = (pagination>100 || pagination<10) ? 10 : pagination;
		webController.setPagination(pagination);
		
		Long limit = webController.getPagination();
		Long nbPages = webController.calculatePages(computerController.count());
		
		Long currentPage = (page==null) ? 1 : Long.valueOf(page); 
		currentPage = (currentPage>=nbPages || currentPage<1)? 1 : currentPage;
		
		model.put("nbPages",nbPages);
		
		Set<DefaultComputerSkin> computers = null;
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
	public String editGet(@RequestParam("idComputer") String id, ModelMap model) {
		DefaultComputerSkin computer = (DefaultComputerSkin) computerController.show(id);
		model.put("computer",computer);
		model.put("companyList",companyController.list());
		return COMPUTER_EDIT_VIEW;
	}
	
	@PostMapping(value="/computer/edit")
	public String editPost(
			@RequestParam("idComputer") String id ,
			@RequestParam("newComputerName") String name,
			@RequestParam("newIntroducedDate") String introduced,
			@RequestParam("newDiscontinuedDate") String discontinued,
			@RequestParam("newComputerCompanyName") String company,
			ModelMap model) {
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
		
		return COMPUTER_LIST_VIEW;
	}
	
	
	@GetMapping("/computer/add")
	public ModelAndView addGet( ModelMap model) {		
		
		String companies = companyController.list()
				.stream().map(c -> c.getName())
				.collect(Collectors.joining(";")); 
		DefaultComputerSkin computerDTO = new DefaultComputerSkin();
		computerDTO.setCompany(companies);		
		return new ModelAndView(COMPUTER_ADD_VIEW,"computer",computerDTO);
	}
	@PostMapping("/computer/add")
	public String addPost(@Valid @ModelAttribute("computer") DefaultComputerSkin computerDTO, ModelMap model) {				
		try {
			new ConstraintValidator().validate(computerDTO);
			computerController.create(computerDTO);
		}
		catch(FailedValidationException exp) {
			logger.error("DTO Validation Error",exp);
			model.put("error","Validation Error");
		}
		return COMPUTER_LIST_VIEW;
	}
	@PostMapping("/computer/delete")
	public String delete(@RequestParam("idComputer") String idComputer ,ModelMap model) {
		DefaultComputerSkin computer = new DefaultComputerSkin();
		computer.setId(idComputer);
		computerController.delete(computer);
		return COMPUTER_LIST_VIEW;
	}
	
	
}
