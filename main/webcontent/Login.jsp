<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
</head>
<body>
<h1>Login</h1>
		<form action="/web/LoginUser" method="get">
			<input name="login" placeholder="Login" pattern="[a-zA-Z]+">
			<input type= "password" name = "password" placeholder="Password" pattern="[a-zA-Z0-9]+">
			<button>login</button>
		</form>
				<% String notexists = (String)request.getAttribute("notexists");
				String inactive = (String)request.getAttribute("inactive");
				String passwordChanged = (String) request.getAttribute("pass_succes");
		if (notexists != null){
			response.getWriter().append("<h1>Username or password is incorrect, or you leave empty fields!</h1>");
		} else if(inactive != null){
				response.getWriter().append("<h1>Your account is inactive!</h1>");
			} else if (passwordChanged != null){
				response.getWriter().append("<h1>Password changed!</h1>");
			}
		%>
		<form action="/web/register" method="get">
			<button>Register</button>
		</form>
</body>
</html>