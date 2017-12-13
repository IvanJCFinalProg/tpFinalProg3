<%@page import="cal.tpfinal.bean.Commentaire"%>
<%@page import="cal.tpfinal.bean.Publication"%>
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
		<%
		User profil = (User)session.getAttribute("profil");
		%>
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
			<div class="col-md-2">Profil de <%=profil.getPrenom()+" "+profil.getNom()%></div>
				<div class="col-md-8">
				<%
						for(Publication publication : profil.getFeed()){
							%>
							<div>
								<%
									User publicateur = publication.getUser();
								%>
								<p><%="#"+publication.getId()+"-"%>
									<a href="AdminController?action=Profil&idUser=<%= publicateur.getCredential().getId()%>">
									<%=publicateur.getPrenom()+" "+publicateur.getNom()%></a>
									
									<%="("+publication.getId_User()+")"+"-"+"\t"+publication.getDate_publication()%></p>
								<p><%=publication.getContent()%></p>
							</div>
							<div>
								<form id="delPubliForm" name="delPublication" action="AdminController?action=supprimerPublication" method="post">
									<button type="submit">Supprimer publication</button>
									<input type="hidden" name="idPubli" value="<%=publication.getId()%>"/>
									<input type="hidden" name="idUser" value="<%=profil.getCredential().getId()%>"/>
								</form>
							
							</div>
							<div>
								<%
									for(Commentaire commentaire : publication.getListeCommentaires()){
										User commenteur = commentaire.getUser();
								%>
										<br>
										<p>#<%=commentaire.getId()%>-
											<a href="AdminController?action=Profil&idUser=<%= commenteur.getCredential().getId()%>">
											<%=commenteur.getPrenom()+" "+commenteur.getNom()%></a>
											(<%=commenteur.getCredential().getId()%>)- <%=commentaire.getDate_publication()%>
											<br>
											<%=commentaire.getContent() %>
										</p>
										
										<form id="delCommentForm" name="delComment" action="AdminController?action=supprimerCommentaire" method="post">
											<button type="submit">Supprimer Commentaire</button>
											<input type="hidden" name="idPubli" value="<%=commentaire.getId_Publication()%>"></input>
											<input type="hidden" name="idUser" value="<%=profil.getCredential().getId() %>"/>
											<input type="hidden" name="idCommentaire" value="<%=commentaire.getId()%>"></input>
											
										</form>
									<%
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
							<a href="AdminController?action=Profil&idUser=<%= profilAmi.getCredential().getId()%>">
							<%=profilAmi.getPrenom()+" "+profilAmi.getNom()%></a>
						</div>
						<a href="AdminController?action=enleverAmi&idUser=<%=profil.getCredential().getId()%>&idRemove=<%=profilAmi.getCredential().getId()%>">Enlever ami</a>
					<%
					}
					%>
				</div>
			</div>
			<div class="col-md-2"></div>
		</section>
	
	</body>
</html>