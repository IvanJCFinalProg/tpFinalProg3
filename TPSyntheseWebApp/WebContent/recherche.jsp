<%@page import="java.util.List"%>
<%@page import="cal.tpfinal.bean.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Recherche NextChill</title>
		<link href="css/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet">
        <link href="css/feedStyles.css" rel="stylesheet">
		<link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet">
		<link rel="stylesheet" href="css/bootstrap.min.css">
		<script src="js/libs/jquery/jquery-3.2.1.min.js"></script>
		<script src="js/libs/bootstrap/bootstrap.js"></script>
</head>
<body>
	<%
			//System.out.println(request.getSession().getAttribute("user"));
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
                  <a class="navbar-brand" href="LoginController?action=accueil">Bonjour <%=user.getPrenom()%> <%=user.getNom() %></a>
              </div>
              <div class="collapse navbar-collapse" id="myNavbar">
                  <ul class="nav navbar-nav navbar-right">
                  	<li><a href="UserController?action=afficherProfil&idAfficher=<%= user.getCredential().getId()%>&idUser=<%=user.getCredential().getId()%>"><span class="fa fa-user fa-2x"></span> Profil</a></li>
                  	<li><a href="#"><span class="fa fa-cog fa-2x"></span> Paramètres</a></li>
                    <li><a href="LoginController?action=deconnexion"><span class="fa fa-sign-out fa-2x"></span> Déconnexion</a></li>
	              </ul>
		      </div>
		  </div>
	</nav>
	<section class="bg-1 container-fluid">
			<div class="row">
				<div class="col-md-2">
					<!-- Barre de navigation de côté qui permet de naviguer dans le contenu de la page -->
			        <nav id="navigCote" class="sidenav">
			            <a href="#"><span class="ion-earth"></span></a>
			            <a href="#"><span class="ion-arrow-graph-down-right"></span>Soldes</a>
			            <a href="#"><span class="ion-power"></span>Innovation</a>
			            <a href="#"><span class="ion-person"></span>À propos</a>
			            <a href="#"><span class="ion-clipboard"></span>Nos qualités</a>
			            <a href="#"><span class="ion-map"></span>Emplacement</a>
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