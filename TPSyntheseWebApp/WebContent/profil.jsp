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
		<link href="css/ionicons-2.0.1/css/ionicons.css" rel="stylesheet">
        <link href="css/feedStyles.css" rel="stylesheet">
		<link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet">
		<link rel="stylesheet" href="css/bootstrap.min.css">
		<script src="js/libs/jquery/jquery-3.2.1.min.js"></script>
		<script src="js/libs/bootstrap/bootstrap.js"></script>
	</head>
	<body>
		<%
			User profil = (User)session.getAttribute("profil");
			boolean ami =(boolean) session.getAttribute("amitie");
			User user = (User)session.getAttribute("user"); 
			
			session.setAttribute("user", user);
			session.setAttribute("profil", profil);
			session.setAttribute("idAfficher", profil.getCredential().getId());
			session.setAttribute("idUser", user.getCredential().getId());
			
			%>
		<nav class="navbar navbar-default navbar-fixed-top">
          <div class="container">
              <div class="navbar-header">
                  <span id="logo" class="ion-person"></span><a class="navbar-brand" href="UserController?action=afficherProfil&idAfficher=<%=user.getCredential().getId()%>&idUser=<%=user.getCredential().getId()%>">Profil de <%=user.getNom() %></a>
              </div>
              <div class="collapse navbar-collapse" id="myNavbar">
              	<div class="col-md-1">
					<form method="post" class="search form-search combo-input-button" action="RechercheController?action=rechercher&idUser=<%=user.getCredential().getId()%>">
						<input type="text" name="tagRecherche" class="form-control input-sm rechercher" maxlength="64" placeholder=" Recherche .... "/>
						<button id="btnSearch" type="submit" class="btn btn-primary btn-sm"><span class="fa fa-search fa-2x"></span></button>
					</form>
				</div>
              	<ul class="nav navbar-nav navbar-right">
                  	<li><a href="LoginController?action=accueil"><span class="ion ion-ios-home fa-2x"></span>NextChill</a></li> 	
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
				<div class="col-md-7">
					<section class="">
						<div class="container">
						  <div class="row">
						    <div class="col-md-offset-3 col-md-6 col-xs-12">
						    	<br>
						    	<h1 class="titreProfil">Profil de <%=profil.getPrenom() %> <%=profil.getNom() %></h1>
						    	<br>
						    	<h2 class="titreProfil">Votre fil d'actualité</h2>
						    	<br>
						    	<div class="well well-sm well-social-post">
						    	
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
							            <li class='pull-right'><button type="submit" name="publi" id="btnPubli" value="Publier">Publier</button></li>
							          </ul>
							          <input type="hidden" name="idUser" value="<%=user.getCredential().getId()%>"></input>
									  <input type="hidden" name="idAfficher" value="<%=profil.getCredential().getId()%>"/>
							        </form>
						      	</div>
						      	
						      	  	<%
						for(Publication publication : profil.getFeed()){
							%>
							<%
									//User publicateur = ServiceUser.getUserById(publication.getId_User(), ServiceUser.fromToXML(ServiceApp.getValue("2", 2)));
									User publicateur = publication.getUser();
							%>
							<div class="box text">
						      <div class="box-header">
						        <h3><a href="UserController?action=afficherProfil&idAfficher=<%= publicateur.getCredential().getId()%>
									&idUser=<%=user.getCredential().getId()%>"><%=publicateur.getPrenom()+" "+publicateur.getNom()%></a>
						          <span><%= publication.getDate_publication()%><i class="fa fa-globe"></i></span>
						        </h3>
						         <%if(user.getCredential().getId() == publication.getId_User()) {%>
						        <div class="dropdown">
									  <button id="btnDrop" class="dropbtn"><span class="ion-more"></span></button>
									  <div class="dropdown-content">
									  	<form id="delPubliForm" name="delPublication" action="ProfilController?action=supprimerPublication&idPubli=<%=
											publication.getId()%>&idUser=<%=user.getCredential().getId() %>&idAfficher=<%= profil.getCredential().getId()%>" method="post">
											 <a ><button id="btnSupprimerPub" type="submit">Supprimer publication</button></a>
										</form>
									  </div>
								</div>
								<%} %>
						        <!--  <span>
						        	<i class="ion-more"></i>
						        </span>-->
						        <div class="window"><span></span></div>
						      </div>
						       <div class="box-content">
						        <div class="content">
						          <p class="text-align"><%=publication.getContent()%></p>
						        </div>
						      </div>
						      <div class="box-likes">
						        <div class="row">
						          <!--<span>+99</span>
						          <span>aime </span>-->
						        </div>
						        <div class="row">
						          <span><%=publication.getListeCommentaires().size() %> commentaires</span>
						        </div>
						      </div>
						      <div class="box-buttons">
						        <div class="row">
						          <button><!--<span class="fa fa-thumbs-up"></span> 
						          	<a href="UserController?action=aimer&idPublication=<%=publication.getId()%>">J'aime</a>--></button>
						          <button><span class="ion-chatbox-working"></span> Commenter</button>
						        </div>
						      </div>
						      <div class="box-comments">
						      <%
										for(Commentaire commentaire : publication.getListeCommentaires()){
											User commenteur = commentaire.getUser();
									%>
											<div class="comment">
										          <div class="content">
										            <h3><a href="UserController?action=afficherProfil&idAfficher=<%= commenteur.getCredential().getId()%>
													&idUser=<%=user.getCredential().getId()%>"><%=commenteur.getPrenom()+" "+commenteur.getNom()%></a><span>
													<time><%=commentaire.getDate_publication()%></time></span></h3><p><%=commentaire.getContent() %></p>
													<%
														if(user.getCredential().getId() == publicateur.getCredential().getId()
															|| user.getCredential().getId() == commenteur.getCredential().getId()) {
													%>
														<form id="delCommentForm" name="delComment" action="ProfilController?action=supprimerCommentaire&idAfficher=<%= profil.getCredential().getId()%>" method="post">
															<button id="btnSupprimer" type="submit"><span class="ion-close-round"></span></button>
															<input type="hidden" name="idPubli" value="<%=commentaire.getId_Publication()%>"></input>
															<input type="hidden" name="idCommentaire" value="<%=commentaire.getId()%>"></input>
															<input type="hidden" name="idUser" value="<%= user.getCredential().getId()%>"/>
														</form>
														
													<% } %>
										          </div>
						        			</div>											
										
										<%
										}
								%>
							  </div>
						      <div class="box-new-comment">
						          <div class="content">
						          	<form id="publiForm" name="formPublication" action="ProfilController?action=commenter&idAfficher=<%= profil.getCredential().getId()%>" method="post">
						          		<input type="hidden" name="idUserPublication" value="<%=publication.getUser().getCredential().getId()%>"></input>
										<input type="hidden" name="idPublication" value="<%=publication.getId()%>"></input>
										<input type="hidden" name="idUser" value="<%=user.getCredential().getId()%>"></input>
							            <div id="textareaRow" class="row">
							              <textarea class="form-control" name="commentaire" placeholder="   Commenter ..."></textarea>
							            </div>
							            <div id="commnentRow" class="row">
							              <button id="btnCommnent" type="submit">Commenter</button>
							            </div>
						            </form>
						          </div>
						        </div>
					    	</div>
						   <% } %> 
					   
						</div>
						</div>					
						</div>						
					</section>
				</div>
				<div class="col-md-3">
					<div class="sectionListeAmi">
						<br>
						<h3 class="titreProfil">Liste d'ami(e)s </h3>
						<% 
							for(User profilAmi : profil.getListeAmi()){
						%>
							<div>
								<a href="UserController?action=afficherProfil&idAfficher=<%= profilAmi.getCredential().getId()%>
								&idUser=<%=user.getCredential().getId()%>"><%=profilAmi.getPrenom()+" "+profilAmi.getNom()%></a>
							</div>
							<%if(user.getCredential().getId() == profil.getCredential().getId() || profilAmi.getCredential().getId() == user.getCredential().getId()){ %>
							<a href="ProfilController?action=enleverAmi&idRemove=<%=profilAmi.getCredential().getId()%>"><span class="ion-ios-minus"></span></a>
							<%} %>
						<%
						}
						%>
					</div>
				</div>
			</div>
		</section>
	</body>
</html>