<%@page import="java.util.ArrayList"%>
<%@page import="cal.tpfinal.model.ServiceApp"%>
<%@page import="cal.tpfinal.model.ServiceUser"%>
<%@page import="cal.tpfinal.bean.Commentaire"%>
<%@page import="cal.tpfinal.bean.Publication"%>
<%@page import="cal.tpfinal.bean.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Bienvenue NextChill</title>
		<link href="css/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet">
        <link href="css/feedStyles.css" rel="stylesheet">
		<link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet">
		<link rel="stylesheet" href="css/bootstrap.min.css">
		<script src="js/libs/jquery/jquery-3.2.1.min.js"></script>
		<script src="js/libs/bootstrap/bootstrap.js"></script>
	</head>
	<body>
		<%
			User profil = (User)session.getAttribute("profil");
			session.setAttribute("profil", profil);
			boolean ami = false;
			User user = (User)session.getAttribute("user"); 
			
			ami =(boolean) session.getAttribute("amitie");
			
			session.setAttribute("user", user);
			
			session.setAttribute("idAfficher", profil.getCredential().getId());
			session.setAttribute("idUser", user.getCredential().getId());
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
			            <a href="#logo"><span class="ion-earth"></span>Logo</a>
			            <a href="#solde"><span class="ion-arrow-graph-down-right"></span>Soldes</a>
			            <a href="#innov"><span class="ion-power"></span>Innovation</a>
			            <a href="#propos"><span class="ion-person"></span>À propos</a>
			            <a href="#qualites"><span class="ion-clipboard"></span>Nos qualités</a>
			            <a href="#map"><span class="ion-map"></span>Emplacement</a>
			        </nav>
				</div>
				<div class="col-md-8">
					<section class="">
						<div class="container">
						  <div class="row">
						    <div class="col-md-offset-3 col-md-6 col-xs-12">
						    	<div>
									<form method="post" action="RechercheController?action=rechercher&idUser=<%=user.getCredential().getId()%>">
										<input type="text" name="tagRecherche"/>
										<button type="submit">Rechercher</button>
									</form>
								</div>
						    	<div class="well well-sm well-social-post">
						    		<h2>Profil de :<%=profil.getPrenom()%> <%=profil.getNom()%></h2>
						    	<%
						    		if(user.getCredential().getId() != profil.getCredential().getId()){
						    	%>
						    		<a href="ProfilController?action=<%= (!ami)? "ajouterAmi":"enleverAmi"%>
						      		"><%= (!ami)? "Ajouter ami!":"Enlever ami!"  %></a>
						      	<%
						    		}
						      	%>
							        <form id="publiForm" name="formPublication" action="ProfilController?action=publier" method="post">
							          <ul class="list-inline" id='list_PostActions'>
							            <li class='active'>Publier<a href='#'></a></li>
							          </ul>
							          <textarea class="form-control" name="publication" placeholder="Exprimez-vous ?"></textarea>
							          <ul class='list-inline post-actions'>
							            <li><a href="#"><span class="fa fa-user"></span></a></li>
							            <li><a href="#" class='fa fa-map-marker'></a></li>
							            <li class='pull-right'><button type="submit" name="publi" value="Publier">Publier</button></li>
							          </ul>
							          <input type="hidden" name="idUser" value="<%=user.getCredential().getId()%>"></input>
									  <input type="hidden" name="idAfficher" value="<%=profil.getCredential().getId()%>"/>
							        </form>
						      	</div>
				
						<%
						for(Publication publication : profil.getFeed()){
							%>
							<div>
								<%
									//User publicateur = ServiceUser.getUserById(publication.getId_User(), ServiceUser.fromToXML(ServiceApp.getValue("2", 2)));
									User publicateur = publication.getUser();
								%>
								<p><%="#"+publication.getId()+"-"%>
									<a href="UserController?action=afficherProfil&idAfficher=<%= publicateur.getCredential().getId()%>
									&idUser=<%=user.getCredential().getId()%>"><%=publicateur.getPrenom()+" "+publicateur.getNom()%></a>
									
									<%="("+publication.getId_User()+")"+"-"+"\t"+publication.getDate_publication()%></p>
								<p><%=publication.getContent()%></p>
							</div>
							<div>
								<%
									if(user.getCredential().getId() == publicateur.getCredential().getId() || user.getCredential().getId() == profil.getCredential().getId()){
								%>
								<form id="delPubliForm" name="delPublication" action="ProfilController?action=supprimerPublication" method="post">
									<button type="submit">Supprimer publication</button>
									<input type="hidden" name="idPubli" value="<%=publication.getId()%>"/>
									<input type="hidden" name="idUser" value="<%=user.getCredential().getId()%>"/>
									<input type="hidden" name="idAfficher" value="<%=profil.getCredential().getId()%>"/>
								</form>
								<%
									}
								%>
							
							</div>
							<div>
								<form id="publiForm" name="formPublication" action="ProfilController?action=commenter" method="post">
									<input type="text" name="commentaire"></input>
									<input type="hidden" name="idUserPublication" value="<%=publication.getUser().getCredential().getId()%>"></input>
									<input type="hidden" name="idPublication" value="<%=publication.getId()%>"></input>
									<input type="hidden" name="idUser" value="<%=user.getCredential().getId()%>"></input>
									<input type="hidden" name="idAfficher" value="<%=profil.getCredential().getId()%>"/>
									<button type="submit">Commenter</button>
								</form>
							</div>
							<div>
								<%
									for(Commentaire commentaire : publication.getListeCommentaires()){
										User commenteur = commentaire.getUser();
								%>
										<br>
										<p>#<%=commentaire.getId()%>-
											<a href="UserController?action=afficherProfil&idAfficher=<%= commenteur.getCredential().getId()%>
												&idUser=<%=user.getCredential().getId()%>"><%=commenteur.getPrenom()+" "+commenteur.getNom()%></a>
											(<%=commenteur.getCredential().getId()%>)- <%=commentaire.getDate_publication()%>
											<br>
											<%=commentaire.getContent() %>
										</p>
										
									<%
										if(user.getCredential().getId() == publication.getId_User() 
										|| user.getCredential().getId() == commenteur.getCredential().getId()){
									%>
										<form id="delCommentForm" name="delComment" action="ProfilController?action=supprimerCommentaire" method="post">
											<button type="submit">Supprimer Commentaire</button>
											<input type="hidden" name="idPubli" value="<%=commentaire.getId_Publication()%>"></input>
											<input type="hidden" name="idUser" value="<%=user.getCredential().getId() %>"/>
											<input type="hidden" name="idAfficher" value="<%=profil.getCredential().getId() %>"/>
											<input type="hidden" name="idCommentaire" value="<%=commentaire.getId()%>"></input>
											
										</form>
									<%
										}
									}
								%>
						</div>
						<br><br>
					<%
												
						}
											
											
					%>
											
					</section>
				</div>
				<div class="col-md-2">
					<h4>Liste d'amis</h4>
					<% 
						for(User profilAmi : profil.getListeAmi()){
					%>
						<div>
							<a href="UserController?action=afficherProfil&idAfficher=<%= profilAmi.getCredential().getId()%>
							&idUser=<%=user.getCredential().getId()%>"><%=profilAmi.getPrenom()+" "+profilAmi.getNom()%></a>
						</div>
					<%
					}
					%>
				</div>
			</div>
		</section>
	</body>
</html>