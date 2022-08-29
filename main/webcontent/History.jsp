<%@page import="java.util.regex.Matcher"%>
<%@page import="java.util.regex.Pattern"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>History</title>
</head>
<body>
		<%@ page import="by.http.entity.User" %>
		<%@ page import="by.http.dao.DAOImpl" %>
		<% User user = (User)session.getAttribute("user");%>
		<% DAOImpl dao = DAOImpl.getInstance();%>
		
	<% by.http.logic.HistoryLogic mp = new by.http.logic.HistoryLogic();
		ArrayList<String> incomingHistory = mp.getIncomingHistory(user.getId());
		ArrayList<String> outgoingHistory = mp.getOutgoingHistory(user.getId());
		%>
		
		<% 
			request.setAttribute( "incoming", incomingHistory );
			request.setAttribute( "outgoing", outgoingHistory );
		%>  
		<table>
			<tr>Incoming history:</tr>
			<c:forEach items="${incoming}" var="bill">
				<tr>
    				<p>${bill}</p>
    			</tr>
    		</c:forEach>
		</table>
		<br>
		<table>
			<tr>Outgoing history:</tr>
			<c:forEach items="${outgoing}" var="card">
				<tr>
    				<p>${card}</p>
    			</tr>
    		</c:forEach>
		</table>
</body>
</html>