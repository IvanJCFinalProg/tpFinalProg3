
<%@page import="java.util.Map"%>
<%@page import="cal.tpfinal.bean.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Général - NextChill</title>
		<link href="css/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet">
		<link href="css/ionicons-2.0.1/css/ionicons.css" rel="stylesheet">
        <link href="css/feedStyles.css" rel="stylesheet">
		<link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet">
		<link rel="stylesheet" href="css/bootstrap.min.css">
		<script src="js/libs/jquery/jquery-3.2.1.min.js"></script>
		<script src="js/libs/bootstrap/bootstrap.js"></script>
		<script src="js/scriptVerification.js"></script>
	</head>
	
	<body>
		<%
			Map<String, String> mapErreurs = null;
			if(request.getAttribute("mapErreurs")!=null){
				mapErreurs = (Map<String, String>)request.getAttribute("mapErreurs");
			}
			User user = (User)session.getAttribute("user"); 
			request.setAttribute("idUser", user.getCredential().getId());
		%>
		<nav class="navbar navbar-default navbar-fixed-top">
          <div class="container">
              <div class="navbar-header">
                  <a class="navbar-brand" href="LoginController?action=accueil">Bonjour <%=user.getPrenom()%> <%=user.getNom() %></a>
              </div>
              <div class="collapse navbar-collapse" id="myNavbar">
                  <ul class="nav navbar-nav navbar-right">
                  	<li><a href="UserController?action=afficherProfil&idAfficher=<%= user.getCredential().getId()%>&idUser=<%=user.getCredential().getId()%>"><span class="fa fa-user fa-2x"></span> Profil</a></li>
                  	<li class="dropdown" >
				        <button class="dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
				          <li><a href="#"><span class="fa fa-cog fa-2x"></span>Paramètres</a></li>
				        </button>
				        <ul class="dropdown-menu dropdown-menu-right" aria-labelledby="dropdownMenu1">
				          <li><a href="#">Paramètres</a></li>
				          <li><a href="#">Amis</a></li>
				          <li><a href="#">Général</a></li>
				          <li><a href="#">Sécurité</a></li>
				        </ul>
					</li>
                    <li><a href="LoginController?action=deconnexion"><span class="fa fa-sign-out fa-2x"></span> Déconnexion</a></li>
	            </ul>
		        </div>
		    </div>
		</nav>
		
		<section id="sectionGeneral1" class="container-fluid bg-1 ">
			<div class="row">
				<div class="col-md-3"></div>
				<div class="col-md-6">
					<div>
						<form id="formModif" name="formModif" action="UserController?action=saveModifs" method="post">
							<input class="hidden" value="<%=user.getCredential().getId()%>" name="idUser">
							<h1>Paramètres</h1>
							<br>
							<h2>Nom :</h2>
							<h4>Nom actuel : <span class="infosActuels"><%=user.getNom() %></span></h4>
							<input type="text" name="newNom" id="nom-new" class="form-control nom" placeholder="Nouveau nom" />
							<div id="error-nom-new"></div>
							<br>
							<h2>Prénom :</h2>
							<h4>Prénom actuel : <span class="infosActuels"><%=user.getPrenom() %></span></h4>
							<input type="text" name="newPrenom" id="prenom-new" class="form-control nom" placeholder="Nouveau prénom" />
							<div id="error-prenom-new"></div>
							<br>
							<h2>Email :</h2>
							<h4>Email actuel : <span class="infosActuels"><%=user.getCredential().getEmail() %></span></h4>
							<input type="text" name="newEmail" id="email-new" class="form-control email" placeholder="Nouvel email" />
							<div id="error-email-new"><%if(mapErreurs!=null){out.print(mapErreurs.get("errorEmail"));}%></div>
							<br><br>
							<input class="center-block" type="submit" name="submitModif" >
						</form>
					</div>
				</div>
				<div class="col-md-3"></div>
			</div>
		</section>
	</body>
</html>