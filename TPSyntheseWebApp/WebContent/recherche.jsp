<%@page import="java.util.List"%>
<%@page import="cal.tpfinal.bean.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Bienvenue NextChill</title>
		<link href="css/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet">
		<link href="css/ionicons-2.0.1/css/ionicons.css" rel="stylesheet">
        <link href="css/feedStyles.css" rel="stylesheet">
		<link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet">
		<link rel="stylesheet" href="css/bootstrap.min.css">
		<script src="js/libs/jquery/jquery-3.2.1.min.js"></script>
		<script src="js/libs/bootstrap/bootstrap.js"></script>
</head>
<body>
	<%
		User user = (User)session.getAttribute("user");
		session.setAttribute("user", user);
		session.setAttribute("idAfficher", user.getCredential().getId());
		session.setAttribute("idUser", user.getCredential().getId());
		List<User> listeRecherche = (List<User>) session.getAttribute("listeRecherche");
		session.setAttribute("listeRecherche", listeRecherche);
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
		<section class="bg-1 container-fluid">
			<div class="row">
				<div class="col-md-2">
					<!-- Barre de navigation de côté qui permet de naviguer dans le contenu de la page -->
			        <nav id="navigCote" class="sidenav">
			            
			        </nav>
				</div>
				<div class="col-md-1"></div>
				<div class="col-md-8">
					<div>
						<form method="post" action="RechercheController?action=rechercher&idUser=<%=user.getCredential().getId()%>">
							<input type="text" name="tagRecherche"/>
							<button type="submit">Rechercher</button>
						</form>
					</div>
					<section class="">
						
						<%
							for(User userRecherche : listeRecherche){
						%>
							<div>
								<a href="UserController?action=afficherProfil&idAfficher=<%= userRecherche.getCredential().getId()%>
								&idUser=<%=user.getCredential().getId()%>"><%=userRecherche.getPrenom()+" "+userRecherche.getNom()%></a>
							</div>
						<%
							}
						%>
										
					</section>
				</div>
			</div>
		</section>
</body>
</html>