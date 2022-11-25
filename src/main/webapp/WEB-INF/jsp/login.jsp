<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="org.iesvegademijas.model.Usuario"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Usuario Login</title>
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
	<form action="/tienda_informatica/usuarios/login/" method="post">
	<input type="hidden" name="__method__" value="check" />
		<div class="clearfix">
			<div style="float: left; width: 50%">
				<h1>Login Usuario</h1>
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
				<br>
			</div>
			<div style="float: none;width: auto;overflow: hidden;">
				<input type="submit" value="Log in" />		
			</div> 
		</div>
		
	</form>
</div>

</body>
</html>