package org.iesvegademijas.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.iesvegademijas.dao.FabricanteDAO;
import org.iesvegademijas.dao.FabricanteDAOImpl;
import org.iesvegademijas.dao.ProductoDAO;
import org.iesvegademijas.dao.ProductoDAOImpl;
import org.iesvegademijas.model.Fabricante;
import org.iesvegademijas.model.Producto;

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
			/*
			//Lista simple de productos  
			
			request.setAttribute("listaProductos", proDAO.getAll());	

			dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos.jsp");
			*/
			/*
			String partialName = request.getParameter("filtrar-por-nombre");
			
			List<Producto> listProd = proDAO.getAll();
			
			listProd = listProd.stream()
					.filter(p -> p.getNombre().toLowerCase().contains(partialName.toLowerCase()))
					.collect(Collectors.toList());
			
			request.setAttribute("listaProductos", listProd);	

			dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos.jsp");
			*/
			/*
			String partialName = request.getParameter("filtrar-por-nombre");
			
			if(partialName != null) {
				partialName = '%'+partialName+'%';
			}else {
				partialName = "%%";
			}
			
			request.setAttribute("listaProductos", proDAO.getByName(partialName));	

			dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos.jsp");
			*/
			
			String partialName = request.getParameter("filtrar-por-nombre");
			System.out.println(partialName + '0');
			
			if(partialName != null && !partialName.equals("")) {
				partialName += '*';
				request.setAttribute("listaProductos", proDAO.getByFulltextName(partialName));	
			}else {
				request.setAttribute("listaProductos", proDAO.getAll());	
			}

			dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos.jsp");
			
		} else {
			// GET
			// 		/productos/{id}
			// 		/productos/{id}/
			// 		/productos/editar/{id}
			// 		/productos/editar/{id}/
			// 		/productos/crear
			// 		/productos/crear/
			
			pathInfo = pathInfo.replaceAll("/$", "");
			String[] pathParts = pathInfo.split("/");
			
			if (pathParts.length == 2 && "crear".equals(pathParts[1])) {
				
				// GET
				// /producto/crear
				
				FabricanteDAO fabDAO = new FabricanteDAOImpl();
				
				request.setAttribute("listaFabricantes", fabDAO.getAll());
				dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/crear-producto.jsp");
        												
			
			} else if (pathParts.length == 2) {
				ProductoDAO proDAO = new ProductoDAOImpl();
				// GET
				// /productos/{id}
				try {
					request.setAttribute("producto",proDAO.find(Integer.parseInt(pathParts[1])));
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/detalle-producto.jsp");
					        								
				} catch (NumberFormatException nfe) {
					nfe.printStackTrace();
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos.jsp");
				}
				
			} else if (pathParts.length == 3 && "editar".equals(pathParts[1]) ) {
				ProductoDAO proDAO = new ProductoDAOImpl();
				
				// GET
				// /productos/editar/{id}
				try {
					request.setAttribute("producto",proDAO.find(Integer.parseInt(pathParts[2])));
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/editar-producto.jsp");
					        								
				} catch (NumberFormatException nfe) {
					nfe.printStackTrace();
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos.jsp");
				}
				
				
			} else {
				
				System.out.println("Opción POST no soportada.");
				dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos.jsp");
			
			}
			
		}
		
			dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher;
		String __method__ = request.getParameter("__method__");
		
		if (__method__ == null) {
			// Crear uno nuevo
			ProductoDAO proDAO = new ProductoDAOImpl();
			
			String nombre = request.getParameter("nombre");
			Double precio = Double.valueOf(request.getParameter("precio"));
			int codigofabricante = Integer.parseInt(request.getParameter("codigofabricante"));
			
			Producto nuevoPro = new Producto();
			nuevoPro.setNombre(nombre);
			nuevoPro.setPrecio(precio);
			nuevoPro.setCodigofabricante(codigofabricante);
			proDAO.create(nuevoPro);			
			
		} else if (__method__ != null && "put".equalsIgnoreCase(__method__)) {			
			// Actualizar uno existente
			//Dado que los forms de html sólo soportan method GET y POST utilizo parámetro oculto para indicar la operación de actulización PUT.
			doPut(request, response);
			
		
		} else if (__method__ != null && "delete".equalsIgnoreCase(__method__)) {			
			// Actualizar uno existente
			//Dado que los forms de html sólo soportan method GET y POST utilizo parámetro oculto para indicar la operación de actulización DELETE.
			doDelete(request, response);
			
			
			
		} else {
			
			System.out.println("Opción POST no soportada.");
			
		}
		
		response.sendRedirect("/tienda_informatica/productos");
		//response.sendRedirect("/tienda_informatica/fabricantes");
		
		
	}
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		ProductoDAO proDAO = new ProductoDAOImpl();
		
		String codigo = request.getParameter("codigo");
		String nombre = request.getParameter("nombre");
		String precio = request.getParameter("precio");
		String codigofabricante = request.getParameter("codigofabricante");
		
		Producto pro = new Producto();
		
		try {
			
			int id = Integer.parseInt(codigo);
			pro.setCodigo(id);
			pro.setNombre(nombre);
			pro.setPrecio(Double.parseDouble(precio));
			pro.setCodigofabricante(Integer.parseInt(codigofabricante));
			proDAO.update(pro);
			
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
		
	}
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		RequestDispatcher dispatcher;
		ProductoDAO proDAO = new ProductoDAOImpl();
		String codigo = request.getParameter("codigo");
		
		try {
			
			int id = Integer.parseInt(codigo);
		
		proDAO.delete(id);
			
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
		
	}

}
