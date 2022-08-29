 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin page</title>
</head>
<body>
		<%@ page import="by.http.entity.User" %>
		<%@ page import="by.http.dao.DAOImpl" %>
		<%@page import="by.http.dao.Dao"%>
		<% User user = (User)session.getAttribute("user");%>
		<% Dao dao = DAOImpl.getInstance();;
		   String username = (String)request.getAttribute("user");
		   String bill = (String)request.getAttribute("bill");
		   if (username != null){
				response.getWriter().append("<h3>"+username+"</h3>");
		   }
		   if (bill != null){
				response.getWriter().append("<h3>"+bill+"</h3>");
		   }
		
		
		%>
		<h1><%="Welcome, " + user.getUsername() +", my dear admin!"%></h1> <br>
		<h4>Unblock Bill</h4>
		<form action = "/web/UnblockBill" method = "get">
		<input name="bill" placeholder="Bill name" pattern="[a-zA-Z]+">
				<input name="username" placeholder="username" pattern="[a-zA-Z]+">
		<button>restore</button>
		</form>
		<br>
		<h4>Restore User</h4>
		<form action="/web/RestoreUser" method="get">
		<input name="name" placeholder="username" pattern="[a-zA-Z]+">
		<button>restore</button>
		</form>
		<br>
		<form action="/web/logout" method="get">
			<button>Log out</button>
		</form>
</body>
</html>