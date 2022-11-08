<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.iesvegademijas.model.Producto"%>
<%@page import="java.util.List"%>

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

</style>
</head>
<body>
<body>

		<div id="contenedora" style="float:none; margin: 0 auto;width: 900px;" >
		<div class="clearfix">
			<div style="float: left; width: 50%">
				<h1>Productos</h1>
			</div>
			<div style="float: none;width: auto;overflow: hidden;min-height: 80px;position: relative;">
				
				<div style="position: absolute; left: 39%; top : 39%;">
					
<!-- 						<form action="/tienda_informatica/productos/crear"> -->
<!-- 							<input type="submit" value="Crear"> -->
<!-- 						</form> -->
					</div>
				
			</div>
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
			<div style="float: left;width: 20%"><%= producto.getCodigo()%></div>
			<div style="float: left;width: 20%"><%= producto.getNombre()%></div>
			<div style="float: left;width: 20%"><%= producto.getPrecio()%></div>
			<div style="float: left;width: 20%"><%= producto.getCodigofabricante()%></div>
			<div style="float: none;width: auto;overflow: hidden;">
				<form action="/tienda_informatica/Productos/<%= producto.getCodigo()%>" style="display: inline;">
    				<input type="submit" value="Ver Detalle" />
				</form>
				<form action="/tienda_informatica/Productos/editar/<%= producto.getCodigo()%>" style="display: inline;">
    				<input type="submit" value="Editar" />
				</form>
				<form action="/tienda_informatica/Productos/borrar/" method="post" style="display: inline;">
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
</body>
</body>
</html>