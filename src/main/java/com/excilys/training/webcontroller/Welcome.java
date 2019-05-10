package com.excilys.training.webcontroller;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		
		HttpSession session =request.getSession();
		Set computers = null;
		String orderBy = request.getParameter("orderBy");
		String search = request.getParameter("search");
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
		request.setAttribute("computers", computers);
		request.setAttribute("count", controller.count());
		request.getRequestDispatcher("WEB-INF/list-computer.jsp").forward(request,response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
	

}
