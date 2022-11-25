<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.iesvegademijas.model.Producto"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Optional"%>
<%@page import="org.iesvegademijas.model.Usuario"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Productos</title>
<style>
.clearfix::after {
	content: "";
	display: block;
	clear: both;
}

<% 
	Optional<Usuario> optUs =(Optional<Usuario>)request.getSession().getAttribute("usuario-logado");
	
	if(optUs!=null){
		if(optUs.get().getRole().equals("administrador")){
			%>
			.adminbtn{
				display:inline;
			}
			<%
		}else{
			%>
			.adminbtn{
				display:none;
			}			
			<%
		}
	}else{
		%>
		.adminbtn{
			display:none;
		}
		<%
	}
%>

</style>
</head>

<body>

<%@ include file="../../header.jspf" %>

<%@ include file="../../nav.jspf" %>

<main>
            <section>
		            <div id="contenedora" style="float:none; margin: 0 auto;width: 900px;" >
						<div class="clearfix">
							<div style="float: left; width: 50%">
								<h1>Productos</h1>
							</div>
							<div style="float: none;width: auto;overflow: hidden;min-height: 80px;position: relative;">
								
								<div style="position: absolute; left: 39%; top : 39%;">
									
										<form action="/tienda_informatica/productos/crear" class="adminbtn">
											<input type="submit" value="Crear">
										</form>
								</div>
								
							</div>
						</div>
						
						<div class="clearfix">
							<form action=/tienda_informatica/productos>
				
								<input type="text" name="filtrar-por-nombre" value=""> 
								<input type="submit" value="Buscar">
							</form>
						</div>
						
						<div class="clearfix">
							<hr/>
						</div>
							
						<div class="clearfix">
							<div style="float: left;width: 20%">Código</div>
							<div style="float: left;width: 20%">Nombre</div>
							<div style="float: left;width: 20%">Precio</div>
							<div style="float: left;width: 20%">Código fabricante</div>
							<div style="float: none;width: auto;overflow: hidden;">Acción</div>
						</div>
						<div class="clearfix">
							<hr/>
						</div>
					<% 
				        if (request.getAttribute("listaProductos") != null) {
				            List<Producto> listaProducto = (List<Producto>)request.getAttribute("listaProductos");
				            
				            for (Producto producto : listaProducto) {
				    %>
				
						<div style="margin-top: 6px;" class="clearfix">
							<div style="float: left;width: 10%"><%= producto.getCodigo()%></div>
							<div style="float: left;width: 40%"><%= producto.getNombre()%></div>
							<div style="float: left;width: 10%"><%= producto.getPrecio()%></div>
							<div style="float: left;width: 20%"><a style="color:black; text-decoration:none" href="/tienda_informatica/fabricantes/<%= producto.getCodigofabricante()%>"><%= producto.getCodigofabricante()%></a></div>
							<div style="float: none;width: auto;overflow: hidden;">
								<form action="/tienda_informatica/productos/<%= producto.getCodigo()%>" style="display: inline;">
				    				<input type="submit" value="Ver Detalle" />
								</form>
								<form action="/tienda_informatica/productos/editar/<%= producto.getCodigo()%>" class="adminbtn"">
				    				<input type="submit" value="Editar" />
								</form>
								<form action="/tienda_informatica/productos/borrar/" method="post" class="adminbtn">
									<input type="hidden" name="__method__" value="delete"/>
									<input type="hidden" name="codigo" value="<%= producto.getCodigo()%>"/>
				    				<input type="submit" value="Eliminar" />
								</form>
							</div>
						</div>
				
					<% 
				            }
				        } else { 
				    %>
						No hay registros de Producto
					<% } %>
					</div>
           </section>
</main>
<%@ include file ="../../footer.jspf"%>
		
</body>
</html>