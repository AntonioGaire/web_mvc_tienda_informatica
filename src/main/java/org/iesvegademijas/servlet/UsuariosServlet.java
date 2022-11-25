package org.iesvegademijas.servlet;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.iesvegademijas.dao.FabricanteDAO;
import org.iesvegademijas.dao.FabricanteDAOImpl;
import org.iesvegademijas.dao.UsuarioDAO;
import org.iesvegademijas.dao.UsuarioDAOImpl;
import org.iesvegademijas.model.Fabricante;
import org.iesvegademijas.model.Usuario;

public class UsuariosServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		RequestDispatcher dispatcher = null;
				
		String pathInfo = request.getPathInfo(); 

					
		if (pathInfo == null || "/".equals(pathInfo)) {
			
			//GET 
			//	/usuarios/
			//	/usuarios
			
			UsuarioDAO usuDAO = new UsuarioDAOImpl();
			
			request.setAttribute("listaUsuarios", usuDAO.getAll());		
			dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuarios.jsp");
	        
			
						
		} else {
			// GET
			// 		/fabricantes/{id}
			// 		/fabricantes/{id}/
			// 		/fabricantes/edit/{id}
			// 		/fabricantes/edit/{id}/
			// 		/fabricantes/create
			// 		/fabricantes/create/
			
			pathInfo = pathInfo.replaceAll("/$", "");
			String[] pathParts = pathInfo.split("/");
			
			
			if (pathParts.length == 2 && "crear".equals(pathParts[1])) {
				
				// GET
				// /usuarios/create			
				UsuarioDAO usuDAO = new UsuarioDAOImpl();
				Map<Integer, String> mapa = usuDAO.getRoles();
				
				request.setAttribute("rolemap", mapa);
				dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/crear-usuario.jsp");

        												
			
			}else if (pathParts.length == 2 && "login".equals(pathParts[1])) {
				
				dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
				
			
			}else if (pathParts.length == 2 && "logout".equals(pathParts[1])) {
				
				HttpSession session=request.getSession();
				
				session.invalidate();
				
				dispatcher = request.getRequestDispatcher("/index.jsp");
				
			}else if (pathParts.length == 2 ) {
				UsuarioDAO usuDAO = new UsuarioDAOImpl();
				// GET
				// /usuarios/{id}
				try {
					request.setAttribute("usuario",usuDAO.find(Integer.parseInt(pathParts[1])));
					
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/detalle-usuario.jsp");
					        								
				} catch (NumberFormatException nfe) {
					nfe.printStackTrace();
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuarios.jsp");
				}
				
			}else if (pathParts.length == 3 && "editar".equals(pathParts[1]) ) {
				UsuarioDAO usuDAO = new UsuarioDAOImpl();
				
				// GET
				// /usuarios/edit/{id}
				try {
					Map<Integer, String> mapa = usuDAO.getRoles();
					
					request.setAttribute("rolemap", mapa);
					
					request.setAttribute("usuario",usuDAO.find(Integer.parseInt(pathParts[2])));
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/editar-usuario.jsp");
					        								
				} catch (NumberFormatException nfe) {
					nfe.printStackTrace();
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuarios.jsp");
				}
				
				
			} else {
				
				System.out.println("Opción POST no soportada.");
				dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuarios.jsp");
			
			}
			
		}
		
		dispatcher.forward(request, response);
			 
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		RequestDispatcher dispatcher;
		String __method__ = request.getParameter("__method__");
		
		if (__method__ == null) {
			// Crear uno nuevo
			UsuarioDAO usuDAO = new UsuarioDAOImpl();
			
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String role = request.getParameter("role");
			
			Usuario usu = new Usuario();
			
			usu.setUsername(username);
			usu.setpassword(password);
			usu.setRole(role);
			
			usuDAO.create(usu);			
			
		} else if (__method__ != null && "put".equalsIgnoreCase(__method__)) {			
			// Actualizar uno existente
			//Dado que los forms de html sólo soportan method GET y POST utilizo parámetro oculto para indicar la operación de actulización PUT.
			doPut(request, response);
			
		
		} else if (__method__ != null && "delete".equalsIgnoreCase(__method__)) {			
			// Actualizar uno existente
			//Dado que los forms de html sólo soportan method GET y POST utilizo parámetro oculto para indicar la operación de actulización DELETE.
			doDelete(request, response);
		
		} else if (__method__ != null && "check".equalsIgnoreCase(__method__)) {	
			System.out.println("CHK meth");
			doCheck(request, response);
			
		}else if(__method__ != null && "logout".equalsIgnoreCase(__method__)) {
			
			HttpSession session=request.getSession();
			
			session.invalidate();
			

		} else {
			
			System.out.println("Opción POST no soportada.");
			
		}
		
		response.sendRedirect("/tienda_informatica");
		
		
	}
	
	private void doCheck(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		UsuarioDAO usuDAO = new UsuarioDAOImpl();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		Usuario usu = new Usuario();
		
		try {
			
			usu.setUsername(username);
			usu.setpassword(password);
			
			Optional<Usuario> usuario = usuDAO.check(usu);
			
			if(usuario.isPresent()) {

				 HttpSession session=request.getSession(true);  
				 session.setAttribute("usuario-logado", usuario);  
			}
			
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
		
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		UsuarioDAO usuDAO = new UsuarioDAOImpl();
		String userid = request.getParameter("id");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String role = request.getParameter("role"); 
		
		Usuario usu = new Usuario();
		
		try {
			
			usu.setId(Integer.parseInt(userid));
			usu.setUsername(username);
			usu.setpassword(password);
			usu.setRole(role);
			
			usuDAO.update(usu);
			
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
		
	}
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		RequestDispatcher dispatcher;
		UsuarioDAO usuDAO = new UsuarioDAOImpl();
		String userid = request.getParameter("id");
		
		try {
			
			int id = Integer.parseInt(userid);
		
		usuDAO.delete(id);
			
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
		
	}
}
