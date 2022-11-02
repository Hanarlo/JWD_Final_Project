<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
</head>
<body>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename = "by.http.localization.local" var = "loc"/>
<fmt:message bundle="${loc}" key = "locale.login" var="login"/>
<fmt:message bundle="${loc}" key = "locale.register" var="register"/>
<fmt:message bundle="${loc}" key = "locale.rubtn" var="rubtn"/>
<fmt:message bundle="${loc}" key = "locale.engbtn" var="engbtn"/>
<fmt:message bundle="${loc}" key = "locale.username" var="username"/>
<fmt:message bundle="${loc}" key = "locale.password" var="password"/>

<h1><c:out value = "${login}"/></h1>
		<form action="/banking/Controller" method="post">
				<input type="hidden" name = "command"  value = "LOGIN">
			<input name="login" placeholder="${username}" pattern="[a-zA-Z]+">
			<input type= "password" name = "password" placeholder="${password}" pattern="[a-zA-Z0-9]+">
			<button><c:out value = "${login}"/></button>
		</form>
		<c:if test="${error != null}">
		         <p><c:out value = "${error}"/><p>
		</c:if>
		<form action="/banking/Controller" method="get">
		<input type="hidden" name = "command"  value = "REGISTER_PAGE">
			<button><c:out value = "${register}"/></button>
		</form>
		
		<form action="/banking/Controller" method="get">
		<input type="hidden" name = "command"  value = "CHANGE_LOCALE">
				<input type="hidden" name = "local"  value = "ru">
		<input type="hidden" name = "last_command"  value = "Login.jsp">
			<button><c:out value = "${rubtn}"/></button>
		</form>
				<form action="/banking/Controller" method="get">
		<input type="hidden" name = "command"  value = "CHANGE_LOCALE">
				<input type="hidden" name = "local"  value = "en">
		<input type="hidden" name = "last_command"  value = "Login.jsp">
			<button><c:out value = "${engbtn}"/></button>
		</form>
		
</body>
</html>