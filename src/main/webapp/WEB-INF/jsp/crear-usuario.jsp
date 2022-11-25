<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="org.iesvegademijas.model.Usuario"%>
<%@page import="java.util.Optional"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Detalle Usuario</title>
<style>
.clearfix::after {
	content: "";
	display: block;
	clear: both;
}

</style>
</head>
<body>

<div id="contenedora" style="float:none; margin: 0 auto;width: 900px;" >
	<form action="/tienda_informatica/usuarios/crear/" method="post">
		<div class="clearfix">
			<div style="float: left; width: 50%">
				<h1>Crear Usuario</h1>
			</div>
			<div style="float: none;width: auto;overflow: hidden;min-height: 80px;position: relative;">
				
				<div style="position: absolute; left: 39%; top : 39%;">								
					<input type="submit" value="Crear"/>					
				</div>
				
			</div>
		</div>
		
		<div class="clearfix">
			<hr/>
		</div>
		
		<div style="margin-top: 6px;" class="clearfix">
			<div style="float: left;width: 50%">
				Username
			</div>
			<div style="float: none;width: auto;overflow: hidden;">
				<input name="username" />
			</div> 
		</div>
				<div style="margin-top: 6px;" class="clearfix">
			<div style="float: left;width: 50%">
				Password
			</div>
			<div style="float: none;width: auto;overflow: hidden;">
				<input name="password" />
			</div> 
		</div>
				<div style="margin-top: 6px;" class="clearfix">
			<div style="float: left;width: 50%">
				Role
			</div>
			<div style="float: none;width: auto;overflow: hidden;">
				<select name="role" style="width:100px;">
				
				<%
			        if (request.getAttribute("rolemap") != null) {
			    		Map<Integer, String> rolemap = (HashMap<Integer, String>)request.getAttribute("rolemap");
			            
			            for (Map.Entry<Integer, String> set : rolemap.entrySet()) {
			            	
			    %>
			    
					<option value="<%= set.getKey() %>" > <%= set.getValue() %></option> 
					
				<% 
			            }
			        } else { 
			    %>
					No hay registros de fabricante
				<% } %>
				
				</select>
		</div>

	</form>
</div>

</body>
</html>