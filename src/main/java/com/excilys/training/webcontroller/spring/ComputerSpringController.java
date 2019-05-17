package com.excilys.training.webcontroller.spring;

import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.training.controller.ComputerController;
import com.excilys.training.dto.DataTransferObject;
import com.excilys.training.model.Computer;
import com.excilys.training.webcontroller.WebController;

@Controller
@RequestMapping("/")
public class ComputerSpringController {
	
	@Resource(name="computerWebController")
	ComputerController controller;
	@Autowired
	WebController webController;
	
	@RequestMapping(value="/computer",method = RequestMethod.GET)
	public String index(HttpSession session, ModelMap  model) {
		String paginationString = (String) model.get("pagination");
		Long pagination = (paginationString==null) ? 10 : Long.valueOf(paginationString);
		pagination = (pagination>100 || pagination<10) ? 10 : pagination;
		webController.setPagination(pagination);
		
		Long limit = webController.getPagination();
		Long nbPages = webController.calculatePages(controller.count());
		String page = (String) model.get("page");
		
		Long currentPage = (page==null) ? 1 : Long.valueOf(page); 
		currentPage = (currentPage>=nbPages || currentPage<1)? 1 : currentPage;
		model.put("lang",webController.language().get("fr"));
		model.put("nbPages",nbPages);
		
		Set<DataTransferObject<Computer>> computers = null;
		String orderBy = (String) model.get("orderBy");
		String search = (String) model.get("search");
		if(search!=null) {
			computers = controller.list(search);			
			
		}else if(orderBy!=null && session.getAttribute("orderBy")!=null) {
			if(!orderBy.equals(session.getAttribute("orderBy"))) {
				session.setAttribute("orderBy",orderBy);
				session.setAttribute("order","ASC");
				computers = controller.list(--currentPage*limit,limit,orderBy,true);
			}else {
				String order = (String) session.getAttribute("order");
				order = order==null ? "ASC" : (order.equals("ASC")) ? "DESC" : "ASC";
				session.setAttribute("order",order);				
				computers = controller.list(--currentPage*limit,limit,orderBy,order.equals("ASC"));				
			}
		}else if(session.getAttribute("orderBy")!=null) {
			String order = (String) session.getAttribute("order");
			order = order==null ? "ASC" : (order.equals("ASC")) ? "DESC" : "ASC";
			computers = controller.list(--currentPage*limit,limit,orderBy,order.equals("ASC"));
			
		}else if(orderBy != null) {
			session.setAttribute("orderBy",orderBy);
			session.setAttribute("order","ASC");
			computers = controller.list(--currentPage*limit,limit,orderBy,true);
			
		}else {
			computers =controller.list(--currentPage*limit,limit);
		}
		
		//request.setAttribute("orderBy", orderBy);
		model.put("computers", computers);
		model.put("count", controller.count());
		
		
		return "list-computer";
	}
	
	
}
