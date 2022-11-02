<%@page import="java.util.regex.Matcher"%>
<%@page import="java.util.regex.Pattern"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>History</title>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
</head>
<body>


		<fmt:setLocale value="${sessionScope.local}"/>
		<fmt:setBundle basename = "by.http.localization.local" var = "loc"/>
		<fmt:message bundle="${loc}" key = "locale.incoming" var="incomingtext"/>
		<fmt:message bundle="${loc}" key = "locale.outgoing" var="outgoingtext"/>
		
		<%
			request.setAttribute( "incomingru", (ArrayList<String>)session.getAttribute("incomingHistoryru") );
			request.setAttribute( "outgoingru", (ArrayList<String>)session.getAttribute("outgoingHistoryru") );
			request.setAttribute( "incomingeng", (ArrayList<String>)session.getAttribute("incomingHistoryeng") );
			request.setAttribute( "outgoingeng", (ArrayList<String>)session.getAttribute("outgoingHistoryeng") );
		%>  
		
		<c:if test = "${local == 'ru'}">
         		<table>
			<tr><c:out value = "${incomingtext}"/></tr>
			<c:forEach items="${incomingru}" var="bill">
				<tr>
    				<p>${bill}</p>
    			</tr>
    		</c:forEach>
		</table>
		<br>
		<table>
			<tr><c:out value = "${outgoingtext}"/></tr>
			<c:forEach items="${outgoingru}" var="card">
				<tr>
    				<p>${card}</p>
    			</tr>
    		</c:forEach>
		</table>
      </c:if>
      
      
      	<c:if test = "${local == 'en'}">
        		<table>
			<tr><c:out value = "${incomingtext}"/></tr>
			<c:forEach items="${incomingeng}" var="bill">
				<tr>
    				<p>${bill}</p>
    			</tr>
    		</c:forEach>
		</table>
		<br>
		<table>
			<tr><c:out value = "${outgoingtext}"/></tr>
			<c:forEach items="${outgoingeng}" var="card">
				<tr>
    				<p>${card}</p>
    			</tr>
    		</c:forEach>
		</table>
      </c:if>
      
      
      <c:if test = "${local == null}">
         		<table>
			<tr><c:out value = "${incomingtext}"/></tr>
			<c:forEach items="${incomingru}" var="bill">
				<tr>
    				<p>${bill}</p>
    			</tr>
    		</c:forEach>
		</table>
		<br>
		<table>
			<tr><c:out value = "${outgoingtext}"/></tr>
			<c:forEach items="${outgoingru}" var="card">
				<tr>
    				<p>${card}</p>
    			</tr>
    		</c:forEach>
		</table>
      </c:if>

</body>
</html>