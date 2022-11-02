<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Replenish</title>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
</head>
<body>

		<fmt:setLocale value="${sessionScope.local}"/>
		<fmt:setBundle basename = "by.http.localization.local" var = "loc"/>
		<fmt:message bundle="${loc}" key = "locale.fund" var="fund"/>

	<form  action="/banking/Controller" method="get">
				<input name = "balance" placeholder="amount" pattern="[0-9]+">
				<input type="hidden" name = "command"  value = "FUND">
			<button><c:out value = "${fund}"/></button>
	</form>
</body>
</html>