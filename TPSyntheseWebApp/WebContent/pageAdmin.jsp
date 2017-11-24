<%@page import="cal.tpfinal.bean.User"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Accueil</title>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link href="css/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet">
		<link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet">
		<link href="css/adStyle.css" rel="stylesheet">
		<link rel="stylesheet" href="css/bootstrap.min.css">
		<script src="js/libs/jquery/jquery-3.2.1.min.js"></script>
		<script src="js/libs/bootstrap/bootstrap.js"></script>
	</head>
	<body>
		<header class="container-fluid" >
			<h1>Logo Nm</h1>
		</header>	
		
		<!-- Ajouter un side nav  -->
		
		<section class="bg-3 container-fluid">
			<div class="col-md-2"></div>
			<div class="col-md-8">
				<h1>Liste des utilisateurs</h1>
				<div class="clear"></div>
				<table class="table">
				  <thead>
				    <tr>
			     	  <th></th>
				      <th>Id</th>
				      <th>Nom, Prénom</th>
				      <th>Email</th>
				      <th>Numéro de téléphone</th>
				      <th>Bloquer</th>
				      <th>Supprimer</th>
				      <th>Voir profil</th>
				    </tr>
				  </thead>
				  <tbody>
				  	<% 
				  		Map<Integer,User> mapUsers = (Map<Integer,User>)request.getAttribute("mapUsers");
				  		if(mapUsers!=null){
				  			for(User user : mapUsers.values()){
				  				%>
								<tr>
									 <td><span class="fa fa-user fa-1x"></span></td> 
								     <td><%=user.getCredential().getId()%></td>
								     <td><%=user.getNom()%>, <%=user.getPrenom()%></td>
								     <td><%=user.getCredential().getEmail()%></td>
								     <td><%=user.getPhoneNumber()%></td>
								     <td>
								     	<span>Bloquer</span>
								     </td>
									 <td>
								     	<span>Supprimer</span>
								     </td>
								     <td><a>Voir profil</a></td>
								</tr>
				  				
				  				<% 
				  			}
				  		}
				  		else{
				  			out.println("<div class=\"text-center error-message\"><h1> Il n'y pas d'utilisateurs inscrits!</h1><div>");
				  		}
				  	
				  	%>
				  </tbody>
				</table>
			</div>
			<div class="col-md-2"></div>
		</section>
	
	</body>
</html>