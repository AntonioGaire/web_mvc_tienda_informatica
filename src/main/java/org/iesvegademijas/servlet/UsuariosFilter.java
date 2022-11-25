package org.iesvegademijas.servlet;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.iesvegademijas.model.Usuario;

/**
 * Servlet Filter implementation class UsuariosFilter
 */

@WebFilter(
        urlPatterns = {"/usuarios/*"},
        initParams = @WebInitParam(name = "acceso-concedido-a-rol", value = "administrador")       
)

public class UsuariosFilter extends HttpFilter implements Filter {
	
	private String rolAcceso;
       
    /**
     * @see HttpFilter#HttpFilter()
     */
    public UsuariosFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
				
		//Cast de ServletRequest a HttpServletRequest, el único tipo implementado 
		//en el contenedor de Servlet: HttpServletRequest & HttpServletReponse
		HttpServletRequest httpRequest =(HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse)response;
				
				
		//Accediendo al objeto de sesión
		HttpSession session = httpRequest.getSession();
				
		//Obteniendo la url
		String url = httpRequest.getRequestURL().toString();
				
		Usuario usuario = null;
		
		System.out.println("usu log: " + session.getAttribute("usuario-logado")+httpRequest.getParameter("__method__"));
		
		
		Optional<Usuario> us = (Optional<Usuario>)session.getAttribute("usuario-logado");
		
		if(us!=null) {
			usuario = us.get();
		}
		
		if (session != null 
							&& (usuario != null
							&& "administrador".equals(usuario.getRole()))) {
						
			chain.doFilter(request, response);
			return;
	
		} else if (url.endsWith("/login")){
		
			chain.doFilter(request, response);
			return;
				
		}else if(httpRequest.getParameter("__method__")!=null){
			chain.doFilter(request, response);
			return;
		}else if(url.endsWith("/logout")){
			chain.doFilter(request, response);
			return;
		}else {
			httpResponse.sendRedirect("/tienda_informatica/");
		}
				

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		this.rolAcceso = fConfig.getInitParameter("acceso-concedido-a-rol");
	}

}
