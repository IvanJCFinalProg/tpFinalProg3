
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
                  <span id="logo" class="ion-person"></span><a class="navbar-brand" href="LoginController?action=accueil">NextChilling</a>
              </div>
              <div class="collapse navbar-collapse" id="myNavbar">
              	<div class="col-md-5">
					<form method="post" class="search form-search combo-input-button" action="RechercheController?action=rechercher&idUser=<%=user.getCredential().getId()%>">
						<input type="text" name="tagRecherche" class="form-control input-sm rechercher" maxlength="64" placeholder=" Recherche .... "/>
						<button id="btnSearch" type="submit" class="btn btn-primary btn-sm"><span class="fa fa-search fa-2x"></span></button>
					</form>
				</div>
              	<ul class="nav navbar-nav navbar-right">
                  	<li><a href="UserController?action=afficherProfil&idAfficher=<%=user.getCredential().getId()%>&idUser=<%=user.getCredential().getId()%>"><span class="ion ion-ios-person fa-2x"></span> Profil</a></li> 	
                  	<li class="dropdown">
				       <a id="btnDropMenu" class="dropbtn"><span class="ion ion-gear-b fa-2x"></span> Paramètres</a>
				       <ul class="dropdown-content2">
							<li><a href="UserController?action=modifierInfos&idUser=<%=user.getCredential().getId()%>">Général</a></li>
					          <li><a href="UserController?action=contacter&idUser=<%=user.getCredential().getId()%>">Nous contacter</a></li>
					          <li>
					          	<a>
						          	<form name="fermerCompte" method="post" action="LoginController?action=fermerCompte&idUser=<%= user.getCredential().getId()%>">
						          		<button type="submit">Fermer compte</button>
						          	</form>
					          	</a>
					          </li>
				       </ul>
					</li>
                    <li><a href="LoginController?action=deconnexion&idUser=<%=user.getCredential().getId()%>"><span class="ion ion-log-out fa-2x"></span> Déconnexion</a></li>
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
							<br>
							<input class="center-block" type="submit" name="submitModif" value="Sauvegarder" >
						</form>
					</div>
					<div>
						<form id="formModifPassword" name="formModifPassword" action="UserController?action=saveModifsPassword" method="post">
							<input class="hidden" value="<%=user.getCredential().getId()%>" name="idUser">
							<h1>Modifier votre mot de passe</h1>
							<input type="password" name="newPassword" id="password-new" class="form-control password" placeholder="Nouveau mot de passe" />
							<div id="error-password-new"><%if(mapErreurs!=null){out.print(mapErreurs.get("errorPassword"));}%></div>
							<input class="center-block" type="submit" name="submitPassword" value="Sauvegarder" >
						</form>
					</div>
				</div>
				<div class="col-md-3"></div>
			</div>
		</section>
	</body>
</html>