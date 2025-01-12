<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.iesvegademijas.model.FabricanteDTO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Optional"%>
<%@page import="org.iesvegademijas.model.Usuario"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Fabricantes</title>
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

<main class ="body_sec">
            <section id="Content">
			            <div id="contenedora" style="float:none; margin: 0 auto;width: 900px;" >
							<div class="clearfix">
								<div style="float: left; width: 50%">
									<h1>Fabricantes</h1>
								</div>
								<div style="float: none;width: auto;overflow: hidden;min-height: 80px;position: relative;">
									
									<div style="position: absolute; left: 39%; top : 39%;">
										
											<form action="/tienda_informatica/fabricantes/crear" class="adminbtn" >
												<input type="submit" value="Crear">
											</form>
									</div>
									
								</div>
										
							<div class="clearfix">
								<form action=/tienda_informatica/fabricantes>
									<select name="ordenar-por">
										<option value="codigo"> Por codigo </option>
										<option value="nombre"> Por nombre </option>
									</select>
									
									<select name="modo-ordenar">
										<option value="ascendente"> ascendente </option>
										<option value="descendente"> descendente </option>
									</select>
									
									<input type="submit" value="Ordenar">
								</form>
							</div>
							
							</div>
							<div class="clearfix">
								<hr/>
							</div>
							<div class="clearfix">
								<div style="float: left;width: 25%">Código</div>
								<div style="float: left;width: 25%">Nombre</div>
								<div style="float: left;width: 25%">Productos</div>
								<div style="float: none;width: auto;overflow: hidden;">Acción</div>
							</div>
							<div class="clearfix">
								<hr/>
							</div>
						<% 
					        if (request.getAttribute("listaFabricantesDTO") != null) {
					            List<FabricanteDTO> listaFabricanteDTO = (List<FabricanteDTO>)request.getAttribute("listaFabricantesDTO");
					            
					            for (FabricanteDTO fabricante : listaFabricanteDTO) {
					    %>
					
							<div style="margin-top: 6px;" class="clearfix">
								<div style="float: left;width: 25%"><%= fabricante.getCodigo()%></div>
								<div style="float: left;width: 25%"><%= fabricante.getNombre()%></div>
								<div style="float: left;width: 25%"><%= fabricante.getNumProductos()%></div>
								<div style="float: none;width: auto;overflow: hidden;">
									<form action="/tienda_informatica/fabricantes/<%= fabricante.getCodigo()%>" style="display: inline;">
					    				<input type="submit" value="Ver Detalle" />
									</form>
									<form action="/tienda_informatica/fabricantes/editar/<%= fabricante.getCodigo()%>" class="adminbtn">
					    				<input type="submit" value="Editar" />
									</form>
									<form action="/tienda_informatica/fabricantes/borrar/" method="post" class="adminbtn"">
										<input type="hidden" name="__method__" value="delete"/>
										<input type="hidden" name="codigo" value="<%= fabricante.getCodigo()%>"/>
					    				<input type="submit" value="Eliminar" />
									</form>
								</div>
							</div>
					
						<% 
					            }
					        } else { 
					    %>
							No hay registros de fabricante
						<% } %>
						</div>
            </section>
</main>
<%@ include file ="../../footer.jspf"%>

	
</body>
</body>
</html>