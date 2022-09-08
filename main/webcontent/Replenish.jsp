<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Replenish</title>
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
	<form  action="/web/ReplenishBalance" method="post">
				<input name = "balance" placeholder="amount" pattern="[0-9]+">
			<button>Add to balance</button>
	</form>
</body>
</html>