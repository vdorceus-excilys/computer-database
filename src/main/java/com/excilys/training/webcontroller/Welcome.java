package com.excilys.training.webcontroller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.training.controller.ComputerController;

/**
 * Servlet implementation class Welcome
 */
@WebServlet("/list-computer")
public class Welcome extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ComputerController controller;
	
    /**
     * Default constructor. 
     */
    public Welcome() {
    	super();
    	controller = WebController.getInstance().getComputerController();    		
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// pagination
		String paginationString = request.getParameter("pagination");
		Long pagination = (paginationString==null) ? 10 : Long.valueOf(paginationString);
		pagination = (pagination>100 || pagination<10) ? 10 : pagination;
		WebController.getInstance().setPagination(pagination);
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
		Long limit = WebController.getInstance().getPagination();
		Long nbPages = WebController.getInstance().calculatePages(controller.count());
		String page = request.getParameter("page");
		
		Long currentPage = (page==null) ? 1 : Long.valueOf(page); 
		currentPage = (currentPage>=nbPages || currentPage<1)? 1 : currentPage;
		request.setAttribute("lang",WebController.getInstance().language().get("fr"));
		request.setAttribute("nbPages",nbPages);
		request.setAttribute("computers", controller.list(--currentPage*limit,limit));
		request.getRequestDispatcher("WEB-INF/list-computer.jsp").forward(request,response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
	

}
