<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Register</title>
</head>
<body>
<h1>Register</h1>
		<form action="/web/create" method="post">
			<input name="login" placeholder="Login" pattern="[a-zA-Z]+">
			<input name = "password" placeholder="Password" pattern="[a-zA-Z]+">
			<button>Register</button>
		</form>
		<% String asd = (String)request.getAttribute("exists");
		if (asd != null){
			response.getWriter().append("<h1>User with your username is already exists, or you doesn't fill all fields!</h1>");
		}
		%>
		<form action="/web/login" method="get">
			<button>login</button>
		</form>
</body>
</html>