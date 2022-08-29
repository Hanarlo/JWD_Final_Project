<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Payment</title>
</head>
<body>
<%
String asd =(String) request.getParameter("name");
session.setAttribute("name", asd);
try {
if (asd.contains("BLOCKED")) {
	request.setAttribute("result", "Bill is Blocked!");
	getServletContext().getRequestDispatcher("/Mainpage.jsp").forward(request, response);
}
} catch(NullPointerException e){
	response.sendRedirect("/web/main");
}
%>
				<form action="/web/MakePayment" method="get">
			<input name="reciever_login" placeholder="Reciever login" pattern="[a-zA-Z]+">
			<input name="reciever_name" placeholder="Reciever Bill name" pattern="[a-zA-Z]+">
			<input name="amount" placeholder="amount" pattern="[1-9]+">
			<button>Create bill</button>
		</form>
</body>
</html>