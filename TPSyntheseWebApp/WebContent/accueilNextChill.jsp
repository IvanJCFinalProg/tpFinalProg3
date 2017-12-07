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
			User profil = ServiceUser.getUserById(10, ServiceUser.fromToXML(ServiceApp.getValue("2", 2)));
			User user = ServiceUser.getUserById(9, ServiceUser.fromToXML(ServiceApp.getValue("2", 2)));
			request.setAttribute("user", profil);
			//User user = (User)request.getAttribute("user"); request.setAttribute("user", user);%>
		<nav class="navbar navbar-default navbar-fixed-top">
          <div class="container">
              <div class="navbar-header">
                  <a class="navbar-brand" href="">Bonjour <%=user.getPrenom()%> <%=user.getNom() %></a>
              </div>
              <div class="collapse navbar-collapse" id="myNavbar">
                  <ul class="nav navbar-nav navbar-right">
                  	<li><a href="#"><span class="fa fa-user fa-2x"></span> Profil</a></li>
                  	<li><a href="#"><span class="fa fa-cog fa-2x"></span> Paramètres</a></li>
                    <li><a href="#"><span class="fa fa-sign-out fa-2x"></span> Déconnexion</a></li>
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
				<div class="col-md-10">
					<section class="container-fluid">
						<form id="publiForm" name="formPublication" action="UserController?action=publier" method="post">
							<textarea name="publication" rows=4 cols=40 value=""></textarea>
							<input type="hidden" name="idUser" value="<%=user.getCredential().getId()%>"></input>
							<input type="hidden" name="idProfil" value="<%=profil.getCredential().getId()%>"/>
							<button type="submit" name="publi" value="Publier">Publier</button>
						</form>
						<%
						for(Publication publication : profil.getFeed()){
							%>
							<div>
								<%
									User publicateur = ServiceUser.getUserById(publication.getId_User(), ServiceUser.fromToXML(ServiceApp.getValue("2", 2)));
								
								%>
								<p><%="#"+publication.getId()+"-"%>
									<form id="affichForm" name="affichUser" action="UserController?action=afficherProfil" method="post">
										<button type="submit"><%=publicateur.getPrenom()+" "+publicateur.getNom()%></button>
										<input type="hidden" name="idAfficher" value="<%=publicateur.getCredential().getId()%>">
									</form>
									<%="("+publication.getId_User()+")"+"-"+"\t"+publication.getDate_publication()%></p>
								<p><%=publication.getContent()%></p>
							</div>
							<div>
								<%
									if(user.getCredential().getId() == publication.getId_User() || user.getCredential().getId() == profil.getCredential().getId()){
								%>
								<form id="delPubliForm" name="delPublication" action="UserController?action=supprimerPublication" method="post">
									<button type="submit">Supprimer publication</button>
									<input type="hidden" name="idPubli" value="<%=publication.getId()%>"></input>
									<input type="hidden" name="idUser" value="<%=user.getCredential().getId()%>"></input>
								</form>
								<%
									}
								%>
							
							</div>
							<div>
								<form id="publiForm" name="formPublication" action="UserController?action=commenter" method="post">
									<input type="text" name="commentaire"></input>
									<input type="hidden" name="idUserPublication" value="<%=publication.getId_User()%>"></input>
									<input type="hidden" name="idPublication" value="<%=publication.getId()%>"></input>
									<input type="hidden" name="idUser" value="<%=user.getCredential().getId()%>"></input>
									<button type="submit">Commenter</button>
								</form>
							</div>
							<div>
								<%
									for(Commentaire commentaire : publication.getListeCommentaires()){
										User commenteur = ServiceUser.getUserById(commentaire.getId_User(), ServiceUser.fromToXML(ServiceApp.getValue("2", 2)));
								%>
										<br>
										<p>#<%=commentaire.getId()%>-
											<form id="affichForm" name="affichUser" action="UserController?action=afficherProfil" method="post">
												<button type="submit"><%=commenteur.getPrenom()+" "+commenteur.getNom()%></button>
												<input type="hidden" name="idAfficher" value="<%=commenteur.getCredential().getId()%>">
											</form>
											(<%=commenteur.getCredential().getId()%>)- <%=commentaire.getDate_publication()%>
											<br>
											<%=commentaire.getContent() %>
										</p>
										
									<%
										if(user.getCredential().getId() == publication.getId_User() || user.getCredential().getId() == commentaire.getId_User()){
									%>
										<form id="delCommentForm" name="delComment" action="UserController?action=supprimerCommentaire" method="post">
											<button type="submit">Supprimer Commentaire</button>
											<input type="hidden" name="idPubli" value="<%=commentaire.getId_Publication()%>"></input>
											<input type="hidden" name="idCommentaire" value="<%=commentaire.getId()%>"></input>
											<input type="hidden" name="idUserPublication" value="<%= publication.getId_User()%>"></input>
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
			</div>
		</section>
		
		
		
		
	</body>
</html>