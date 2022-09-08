<%@page import="java.sql.ResultSet"%>
<%@page import="by.http.dao.Dao"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Main page</title>
</head>
<body>
		<%@ page import="by.http.entity.User" %>
		<%@ page import="by.http.dao.DAOImpl" %>
		<% User user = (User)session.getAttribute("user");%>
		<% Dao dao = DAOImpl.getInstance();%>
		<h1><%="Welcome, " + user.getUsername() +"!"%></h1>
		
		<% by.http.logic.MainPageLogic mp = new by.http.logic.MainPageLogic();
		ArrayList<String> bills = mp.getBillsById(user.getId());
		ArrayList<String> cards = mp.getCreditCardsById(user.getId());
		ArrayList<Integer> billsID = mp.getBillsID(user.getId());
		ResultSet billsName = dao.getBillsByUserID(user.getId());
		ArrayList<String> BillNames = new ArrayList<>();
		while (billsName.next()){
			BillNames.add(billsName.getString(5));
		}
		%>
		<% 
			request.setAttribute( "cards", cards );
			request.setAttribute( "bills", bills );
			request.setAttribute( "billsID", billsID );
			request.setAttribute( "billNames", BillNames );
		%>  
		<form action="/web/BlockBill">
				<table>
					<tr><h4>Your Bills:</h4></tr>
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
		  <button type="submit">Block</button>
  		<button type="submit" formaction="/web/payment">
    	Make payment
  		</button>
  		<button type="submit" formaction="/web/replenish">
    	Fund
  		</button>
		</form>

		<br>
		<table>
			<tr><h4>Your credit cards:</h4></tr>
			<c:forEach items="${cards}" var="card">
				<tr>
				<div>
    				<p>${card}</p>
    			</div>
    			</tr>
    		</c:forEach>
		</table>
		<br>
		<% 
		String equalUsername = (String)request.getAttribute("equal_username");
		String zanyato = (String)request.getAttribute("zanyato");
		String result = (String)request.getAttribute("result");
		if (equalUsername != null){
			response.getWriter().append("<h3>Change username to a new one!</h3>");
		}  else if(zanyato != null){
			response.getWriter().append("<h3>Username is already registred!</h3>");
		}  else if(result != null){
			response.getWriter().append("<h3>"+result+"</h3>");
		}
		%>
		<h4>Show history</h4>
		<form>
				<button type="submit" formaction="/web/history">History</button>
		</form>
		<br>
		<h4>Change username</h4>
		<form action="/web/ChangeUsername" method="post">
			<input name="new_username" placeholder="new login" pattern="[a-zA-Z]+">
			<button>Change username</button>
		</form>
		<br>
		<% 
		String equalpassword = (String)request.getAttribute("equal_password");
		String matchpassword = (String)request.getAttribute("doesnt_match");
		
		if (matchpassword != null){
			response.getWriter().append("<h3>Old password is incorrect!</h3>");
		}

		%>
		<h4>Change password</h4>
		<form action="/web/ChangePassword" method="post">
			<input name="old_password" placeholder="Old password" pattern="[a-zA-Z]+">
			<input name = "new_password" placeholder="new password" pattern="[a-zA-Z]+">
			<button>Change password</button>
		</form>
		<br>
		<% 
		String billName = (String)request.getAttribute("bill_check");
		String empty = (String)request.getAttribute("empty_fields");
		if (empty != null){
			response.getWriter().append("<h3>Fill all fields!</h3>");
		}else if (billName != null){
			response.getWriter().append("<h3>You already have bill with that name!</h3>");
		}
		%>
		<h4>Create new bill</h4>
		<form action="/web/CreateBill" method="post">
		<input name="bill_name" placeholder="Bill name" pattern="[a-zA-Z]+">
		<input name="card_name" placeholder="Card name" pattern="[a-zA-Z]+">
		<button>Create bill</button>
		</form>
		<br>
		<form action="/web/logout" method="get">
			<button>Log out</button>
		</form>
				<br>
		<form action="/web/DeleteUser" method="post">
			<button>delete account</button>
		</form>
</body>
</html>