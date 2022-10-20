<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Payment</title>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
</head>
<body>

		<fmt:setLocale value="${sessionScope.local}"/>
		<fmt:setBundle basename = "by.http.localization.local" var = "loc"/>
		<fmt:message bundle="${loc}" key = "locale.payment" var="payment"/>
		
				<form action="Controller" method="get">
			<input name="reciever_login" placeholder="Reciever login" pattern="[a-zA-Z]+">
			<input name="reciever_name" placeholder="Reciever Bill name" pattern="[a-zA-Z]+">
			<input name="amount" placeholder="amount" pattern="[1-9]+">
			<input type="hidden" name = "command"  value = "PAYMENT">
			<button><c:out value = "${payment}"/></button>
		</form>
</body>
</html>