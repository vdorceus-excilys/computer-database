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
 * Servlet implementation class AddComputer
 */
@WebServlet("/add-computer")
public class AddComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ComputerController computerController;
	private WebController webController;
	private CompanyController companyController;
	private static Logger logger = LogManager.getLogger(AddComputer.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddComputer() {
        super();
        WebApplicationContext context = WebController.context(getServletContext());
    	computerController  = context.getBean("computerWebController",ComputerController.class);
    	companyController = context.getBean("companyWebController",CompanyController.class);
    	webController = context.getBean(WebController.class);	
    }
    
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		request.setAttribute("lang",webController.language().get("fr"));
		request.setAttribute("companyList",companyController.list());
		request.getRequestDispatcher("WEB-INF/add-computer.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String name = request.getParameter("newComputerName");
		String introduced = request.getParameter("newIntroducedDate");
		String discontinued = request.getParameter("newDiscontinuedDate");
		String company = request.getParameter("newComputerCompanyName");
		System.out.println(name +" // "+ introduced +"//"+discontinued+"//"+company);
		
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
			request.setAttribute("error","Validation Error");
		}
				
		request.setAttribute("lang",webController.language().get("fr"));
		request.getRequestDispatcher("list-computer").forward(request,response);
	}

}
