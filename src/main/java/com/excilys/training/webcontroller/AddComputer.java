package com.excilys.training.webcontroller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.training.controller.CompanyController;
import com.excilys.training.controller.ComputerController;
import com.excilys.training.dto.DefaultComputerSkin;

/**
 * Servlet implementation class AddComputer
 */
@WebServlet("/add-computer")
public class AddComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ComputerController computerController;
	private CompanyController companyController;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddComputer() {
        super();
        computerController = WebController.getInstance().getComputerController(); 
        companyController = WebController.getInstance().getCompanyController();
    }
    
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		request.setAttribute("lang",WebController.getInstance().language().get("fr"));
		request.setAttribute("companyList",companyController.list());
		request.getRequestDispatcher("WEB-INF/add-computer.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String name = request.getParameter("newComputerName");
		String introduced = request.getParameter("newIntroducedDate");
		String discontinued = request.getParameter("newDiscontinuedDate");
		String company = request.getParameter("newComputerCompanyName");
		System.out.println(name +" // "+ introduced +"//"+discontinued+"//"+company);
		DefaultComputerSkin computer = new DefaultComputerSkin();
		computer.setName(name);
		computer.setIntroduced(introduced);
		computer.setDiscontinued(discontinued);
		computer.setCompany(company);
		request.setAttribute("lang",WebController.getInstance().language().get("fr"));
		request.getRequestDispatcher("list-computer").forward(request,response);
	}

}
