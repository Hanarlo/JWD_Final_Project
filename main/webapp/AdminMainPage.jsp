 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin page</title>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
</head>
<body>

		<fmt:setLocale value="${sessionScope.local}"/>
		<fmt:setBundle basename = "by.http.localization.local" var = "loc"/>
		<fmt:message bundle="${loc}" key = "locale.welcome" var="welcome"/>
		<fmt:message bundle="${loc}" key = "locale.unblockbill" var="unblockbill"/>
		<fmt:message bundle="${loc}" key = "locale.restore" var="restore"/>
		<fmt:message bundle="${loc}" key = "locale.restoreuser" var="restoreuser"/>
		<fmt:message bundle="${loc}" key = "locale.logout" var="logout"/>
		<fmt:message bundle="${loc}" key = "locale.billname" var="billname"/>
		<fmt:message bundle="${loc}" key = "locale.rubtn" var="rubtn"/>
		<fmt:message bundle="${loc}" key = "locale.logout" var="logout"/>
		<fmt:message bundle="${loc}" key = "locale.engbtn" var="engbtn"/>
		<fmt:message bundle="${loc}" key = "locale.username" var="usernameinput"/>
		<fmt:message bundle="${loc}" key = "locale.billname" var="billname"/>

		<h1><c:out value = "${welcome}, ${username}"/>!</h1>
		<h2>
				<c:if test="${errormp != null}">
		         <p><c:out value = "${errormp}"/><p>
				</c:if>
		</h2>
		<h4><c:out value = "${unblockbill}"/></h4>
		<form action = "/banking/Controller" method = "get">
		<input type="hidden" name = "command"  value = "RESTORE_BILL">
		<input name="bill" placeholder="<c:out value = "${billname}"/>" pattern="[a-zA-Z]+">
				<input name="username" placeholder="<c:out value = "${usernameinput}"/>" pattern="[a-zA-Z]+">
		<button><c:out value = "${restore}"/></button>
		</form>
		<br>
		<h4><c:out value = "${restoreuser}"/></h4>
		<form action="/banking/Controller" method="get">
		<input type="hidden" name = "command"  value = "RESTORE_USER">
		<input name="name" placeholder="<c:out value = "${usernameinput}"/>" pattern="[a-zA-Z]+">
		<button><c:out value = "${restore}"/></button>
		</form>
		<br>
		<form action="/banking/Controller" method="get">
		<input type="hidden" name = "command"  value = "LOGOUT">
			<button><c:out value = "${logout}"/></button>
		</form>
						<form action="/banking/Controller" method="get">
		<input type="hidden" name = "command"  value = "CHANGE_LOCALE">
				<input type="hidden" name = "local"  value = "ru">
		<input type="hidden" name = "last_command"  value = AdminMainPage.jsp>
			<button><c:out value = "${rubtn}"/></button>
		</form>
						<form action="/banking/Controller" method="get">
		<input type="hidden" name = "command"  value = "CHANGE_LOCALE">
				<input type="hidden" name = "local"  value = "en">
		<input type="hidden" name = "last_command"  value = "AdminMainPage.jsp">
			<button><c:out value = "${engbtn}"/></button>
		</form>
</body>
</html>