package org.iesvegademijas.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.iesvegademijas.dao.ProductoDAO;
import org.iesvegademijas.dao.ProductoDAOImpl;

/**
 * Servlet implementation class ProductosServletF0
 */
//@WebServlet("/productos/*")
public class ProductosServletF0 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductosServletF0() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		RequestDispatcher dispatcher;
				
		String pathInfo = request.getPathInfo(); //
			
		if (pathInfo == null || "/".equals(pathInfo)) {
			ProductoDAO proDAO = new ProductoDAOImpl();
			
			//GET 
			//	/productos/
			//	/productos
			
			request.setAttribute("listaProductos", proDAO.getAll());	

			dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos.jsp");
			
			dispatcher.forward(request, response);
		}
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
