<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="java.util.ArrayList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Main page</title>
</head>
<body>

		<fmt:setLocale value="${sessionScope.local}"/>
		<fmt:setBundle basename = "by.http.localization.local" var = "loc"/>
		<fmt:message bundle="${loc}" key = "locale.welcome" var="welcome"/>
		<fmt:message bundle="${loc}" key = "locale.bills" var="billstext"/>
		<fmt:message bundle="${loc}" key = "locale.cards" var="cardstext"/>
		<fmt:message bundle="${loc}" key = "locale.block" var="block"/>
		<fmt:message bundle="${loc}" key = "locale.payment" var="payment"/>
		<fmt:message bundle="${loc}" key = "locale.fund" var="fund"/>
		<fmt:message bundle="${loc}" key = "locale.history" var="history"/>
		<fmt:message bundle="${loc}" key = "locale.historybtn" var="historybtn"/>
		<fmt:message bundle="${loc}" key = "locale.block" var="block"/>
		<fmt:message bundle="${loc}" key = "locale.changeusername" var="changeusername"/>
		<fmt:message bundle="${loc}" key = "locale.changeusernamebtn" var="changeusernamebtn"/>
		<fmt:message bundle="${loc}" key = "locale.changepswrd" var="changepswrd"/>
		<fmt:message bundle="${loc}" key = "locale.createbill" var="createbill"/>
		<fmt:message bundle="${loc}" key = "locale.createbillbtn" var="createbillbtn"/>
		<fmt:message bundle="${loc}" key = "locale.logout" var="logout"/>
		<fmt:message bundle="${loc}" key = "locale.deleteaccount" var="deleteaccount"/>
		<fmt:message bundle="${loc}" key = "locale.rubtn" var="rubtn"/>
		<fmt:message bundle="${loc}" key = "locale.engbtn" var="engbtn"/>
		<fmt:message bundle="${loc}" key = "locale.newlogin" var="newlogin"/>
		<fmt:message bundle="${loc}" key = "locale.oldpswrd" var="oldpswrd"/>
		<fmt:message bundle="${loc}" key = "locale.newpswrd" var="newpswrd"/>
		<fmt:message bundle="${loc}" key = "locale.billname" var="billname"/>
		<fmt:message bundle="${loc}" key = "locale.cardname" var="cardname"/>
		
		
		
		
		<%
			by.http.service.impl.HomePageService.executeMainPage(request);
			request.setAttribute( "bills", (ArrayList<String>)session.getAttribute("result_bill_array_list") );
			request.setAttribute( "cards", (ArrayList<String>)session.getAttribute("result_card_array_list") );
			request.setAttribute( "billsID", (ArrayList<Integer>)session.getAttribute("bill_id_array") );
			request.setAttribute( "billNames", (ArrayList<String>)session.getAttribute("bill_names_array") );
		%>  
		
		<h1><c:out value = "${welcome}, ${username}"/>!</h1>
				<h2>
				<c:if test="${errorhp != null}">
		         <p><c:out value = "${errorhp}"/><p>
				</c:if>
		</h2>
		<form action="/banking/Controller">
				<table>
					<tr><h4><c:out value = "${billstext}"/></h4></tr>
				<c:forEach items="${bills}" var="bill">
					<tr>
					<div>
    				    <input type="radio" id="${bill}" name="name" value="${bill}">
						<label for="${bill}">${bill}</label>
					</div>
    				</tr>
    				</c:forEach>
		</table>
		<br>
		  <button type="submit" name = "command" value = "BLOCK"><c:out value = "${block}"/></button>
  		<button type="submit" name = "command" value = "PAYMENT_PAGE">
    	<c:out value = "${payment}"/>
  		</button>
  		<button type="submit" name = "command" value = "FUND_PAGE">
    	<c:out value = "${fund}"/>
  		</button>
		</form>

		<br>
		<table>
			<tr><h4><c:out value = "${cardstext}"/></h4></tr>
			<c:forEach items="${cards}" var="card">
				<tr>
				<div>
    				<p>${card}</p>
    			</div>
    			</tr>
    		</c:forEach>
		</table>
		<br>	
		<h4><c:out value = "${history}"/></h4>
		<form>
				<input type="hidden" name = "command"  value = "HISTORY_PAGE">
				<button type="submit" formaction="/banking/Controller">
				<c:out value = "${historybtn}"/>
				</button>
		</form>
		<br>
		<h4><c:out value = "${changeusername}"/></h4>
		<form action="/banking/Controller" method="get">
			<input name="new_username" placeholder="${newlogin}" pattern="[a-zA-Z]+">
					<input type="hidden" name = "command"  value = "CHANGE_USERNAME">
			<button><c:out value = "${changeusername}"/></button>
		</form>
		<br>

		<h4><c:out value = "${changepswrd}"/></h4>
		<form action="/banking/Controller" method="post">
			<input name="old_password" placeholder="${oldpswrd}" pattern="[a-zA-Z0-9]+">
			<input name = "new_password" placeholder="${newpswrd}" pattern="[a-zA-Z0-9]+">
			<input type="hidden" name = "command"  value = "CHANGE_PASSWORD">
			<button><c:out value = "${changepswrd}"/></button>
		</form>
		<br>
		<h4><c:out value = "${createbill}"/></h4>
		<form action="/banking/Controller" method="get">
		<input name="bill_name" placeholder="${billname}" pattern="[a-zA-Z]+">
		<input name="card_name" placeholder="${cardname}" pattern="[a-zA-Z]+">
		<input type="hidden" name = "command"  value = "CREATE_BILL">
		<button><c:out value = "${createbillbtn}"/></button>
		</form>
		<br>
		<form action="/banking/Controller" method="get">
					<input type="hidden" name = "command"  value = "LOGOUT">
			<button><c:out value = "${logout}"/></button>
		</form>
				<br>
		<form action=""/banking/Controller"" method="get">
					<input type="hidden" name = "command"  value = "DELETE_ACCOUNT">
			<button><c:out value = "${deleteaccount}"/></button>
		</form>
		
				<form action="/banking/Controller" method="get">
		<input type="hidden" name = "command"  value = "CHANGE_LOCALE">
				<input type="hidden" name = "local"  value = "ru">
		<input type="hidden" name = "last_command"  value = "HomePage.jsp">
			<button><c:out value = "${rubtn}"/></button>
		</form>
				<form action="/banking/Controller" method="get">
		<input type="hidden" name = "command"  value = "CHANGE_LOCALE">
				<input type="hidden" name = "local"  value = "en">
		<input type="hidden" name = "last_command"  value = "HomePage.jsp">
			<button><c:out value = "${engbtn}"/></button>
		</form>
</body>
</html>