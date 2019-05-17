package com.excilys.training.webcontroller;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;

import com.excilys.training.controller.CompanyController;
import com.excilys.training.controller.ComputerController;
import com.excilys.training.dto.DefaultComputerSkin;
import com.excilys.training.validator.ConstraintValidator;
import com.excilys.training.validator.exception.FailedValidationException;

/**
 * Servlet implementation class EditComputer
 */
//@WebServlet("/edit-computer")
public class EditComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ComputerController computerController;
	private CompanyController companyController;
	private WebController webController;
	private static Logger logger = LogManager.getLogger(EditComputer.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditComputer() {
    	super();
    	WebApplicationContext context = WebController.context(getServletContext());
     	computerController  = context.getBean("computerWebController", ComputerController.class);
     	companyController =  context.getBean("companyWebController", CompanyController.class);
     	webController = context.getBean(WebController.class);	
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String idComputer = request.getParameter("idComputer");
		response.getWriter().append("Served at: ").append(request.getContextPath());
		DefaultComputerSkin computer = (DefaultComputerSkin) computerController.show(idComputer);
		request.setAttribute("lang",webController.language().get("fr"));
		request.setAttribute("computer",computer);
		request.setAttribute("companyList",companyController.list());
		request.getRequestDispatcher("WEB-INF/edit-computer.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("newComputerId");
		String name = request.getParameter("newComputerName");
		String introduced = request.getParameter("newIntroducedDate");
		String discontinued = request.getParameter("newDiscontinuedDate");
		String company = request.getParameter("newComputerCompanyName");
		//System.out.println(name +" // "+ introduced +"//"+discontinued+"//"+company);
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
			request.setAttribute("error","Validation Error");
		}
		
		request.setAttribute("lang",webController.language().get("fr"));
		request.getRequestDispatcher("list-computer").forward(request,response);
	}

}
