<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.iesvegademijas.model.FabricanteDTO"%>
<%@page import="java.util.List"%>

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

</style>
</head>
<body>
<body>

	<div id="contenedora" style="float:none; margin: 0 auto;width: 900px;" >
		<div class="clearfix">
			<div style="float: left; width: 50%">
				<h1>Fabricantes</h1>
			</div>

		</div>
		<div class="clearfix">
			<hr/>
		</div>
		
		<div class="clearfix">
			<form action=/tienda_informatica/fabricantes&ordenar-por=&modo-ordenar=>
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
		
		
		
</body>
</body>
</html>