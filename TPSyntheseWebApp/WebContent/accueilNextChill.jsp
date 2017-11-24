<%@page import="cal.tpfinal.bean.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Bienvenue NextChill</title>
	</head>
	<body>
		<%User user = (User)request.getAttribute("user"); %>
		<h1>Boujour <%=user.getNom() %> <%=user.getPrenom()%></h1>
	
	</body>
</html>