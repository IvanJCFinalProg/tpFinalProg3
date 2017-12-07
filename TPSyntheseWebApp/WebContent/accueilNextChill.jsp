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
		<%User user = (User)request.getAttribute("user"); request.setAttribute("user", user);%>
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
						<form id="publiForm" name="formPublication" action="LoginController?action=publier" method="post">
							<textarea name="publication" rows=4 cols=40 value=""></textarea>
							<input type="hidden" name="idUser" value="<%=user.getCredential().getId()%>"></input>
							<button type="submit" name="publi" value="Publier">Publier</button>
						</form>
						<%
						for(Publication publication : user.getFeed()){
							%>
							<div>
								<p><%=publication.getContent()%></p>
								<p>Publie le : <%=publication.getDate_publication()%></p>
								<p>Id user ayant publie : <%= publication.getId_User()%> </p>
								<p>Id de la publication : <%= publication.getId()%> </p>
							</div>
							<div>
								<%
									if(user.getCredential().getId() == publication.getId_User()){
								%>
								<form id="delPubliForm" name="delPublication[<%=publication.getId()%>]" action="LoginController?action=supprimer" method="post">
									<button type="submit">Supprimer publication</button>
									<input type="hidden" name="idPubli" value="<%=publication.getId()%>"></input>
									<input type="hidden" name="idUser" value="<%=user.getCredential().getId()%>"></input>
								</form>
								<%
									}
								%>
							
							</div>
							<div>
								<form id="publiForm" name="formPublication[<%=publication.getId()%>]" action="LoginController?action=commenter" method="post">
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
								%>
									<p>Commentaire #<%=commentaire.getId() %> : <%=commentaire.getContent() %>,
									<%=commentaire.getDate_publication() %>
									(Publication #<%=commentaire.getId_Publication() %>,
									User #<%=commentaire.getId_User() %>)</p>
								<%
								}
								%>
							</div>
							<br><br>
						<%
							
						}
						
						
						%>
						
						</table>
					</section>
				</div>
			</div>
		</section>
		
		
		
		
	</body>
</html>