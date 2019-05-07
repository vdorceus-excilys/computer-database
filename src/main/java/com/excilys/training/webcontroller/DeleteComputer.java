package com.excilys.training.webcontroller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.training.controller.ComputerController;
import com.excilys.training.dto.DefaultComputerSkin;

/**
 * Servlet implementation class DeleteComputer
 */
@WebServlet("/delete-computer")
public class DeleteComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ComputerController computerController;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteComputer() {
        super();
        computerController = WebController.getInstance().getComputerController(); 
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		String idComputer = request.getParameter("idComputer");
		DefaultComputerSkin computer = new DefaultComputerSkin();
		computer.setId(idComputer);
		computerController.delete(computer);
		request.setAttribute("lang",WebController.getInstance().language().get("fr"));
		request.getRequestDispatcher("list-computer").forward(request,response);
	}

}
