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
		<link href="css/ionicons-2.0.1/css/ionicons.css" rel="stylesheet">
		<link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet">
		<link href="css/adStyle.css" rel="stylesheet">
		<link rel="stylesheet" href="css/bootstrap.min.css">
		<script src="js/libs/jquery/jquery-3.2.1.min.js"></script>
		<script src="js/libs/bootstrap/bootstrap.js"></script>
	</head>
	<body>
		<header class="container-fluid" >
			<div class="row">
				<div class="col-md-4">
					<h2>Bienvenue Admin,</h2>
				</div>
				<div class="col-md-6"></div>
				<div class="col-md-2">
					<div class="dropdown">
				        <button class="dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
				          <i class="ion ion-chevron-down"></i>
				        </button>
				        <ul class="dropdown-menu dropdown-menu-right" aria-labelledby="dropdownMenu1">
				          <li><a href="#">Paramètres</a></li>
				          <li><a href="AdminController?action=Deconnexion">Déconnexion</a></li>
				        </ul>
					</div>
				</div>	
			</div>
		</header>	
		
		<!-- Ajouter un side nav  -->
		
		<section class="bg-3 container-fluid">
			<div class="col-md-2"></div>
			<div class="col-md-8">
				<h1>Liste des utilisateurs</h1>
				<div class="clear"></div>
				<table class="table">
				<% 
			  		Map<Integer,User> mapUsers = (Map<Integer,User>)request.getAttribute("mapUsers");
			  		if(mapUsers!=null || !mapUsers.isEmpty()){
			  	%>
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
			  	<%
			  			for(User user : mapUsers.values()){
			  				%>
						  <tbody>
								<tr>
									 <td><span class="fa fa-user fa-1x"></span></td> 
								     <td><%=user.getCredential().getId()%></td>
								     <td><%=user.getNom()%>, <%=user.getPrenom()%></td>
								     <td><%=user.getCredential().getEmail()%></td>
								     <td><%=user.getPhoneNumber()%></td>
								     <td>
								     	<!--  <form id="bloqueUser" action="/AdminController?action=Bloquer">
								     		<input name="idUser" value="<%=user.getCredential().getId()%>" class="hidden"/>
								     		<input name="submit" type="submit" value="Bloquer"/>
								     	</form>-->
								     	<span><a href="AdminController?action=Bloquer&idUser=<%=user.getCredential().getId()%>">Bloquer</a></span>
								     </td>
									 <td>
									 	<!--  <form id="suprimmeUser" action="/AdminController?action=Suppresion">
									 		<input name="idUser" value="<%=user.getCredential().getId()%>" class="hidden"/>
									 		<input name="submit" type="submit" value="Supprimer"/>
								     	</form>-->
								     	<a href="AdminController?action=Supprimer&idUser=<%=user.getCredential().getId()%>">Supprimer</a>
								     </td>
								     <td>
								    	<!--<form id="profilUser" action="/AdminController?action=Profil">
								    		<input name="idUser" value="<%=user.getCredential().getId()%>" class="hidden"/>
								    		<input name="submit" type="submit" value="Voir profil"/>
								     	</form>-->
								     	<a href="AdminController?action=Profil&idUser=<%=user.getCredential().getId()%>">Voir profil</a>
								     </td>
								</tr>
							</tbody>
				  				<% 
				  			}
				  		}
				  		else{
				  			out.println("<div class=\"text-center error-message\"><h1> Il n'y pas d'utilisateurs inscrits!</h1><div>");
				  		}
				  	%>
				</table>
			</div>
			<div class="col-md-2"></div>
		</section>
	
	</body>
</html>