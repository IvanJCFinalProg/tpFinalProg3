<%@page import="java.util.ArrayList"%>
<%@page import="cal.tpfinal.model.ServiceApp"%>
<%@page import="cal.tpfinal.model.ServiceUser"%>
<%@page import="cal.tpfinal.bean.Commentaire"%>
<%@page import="cal.tpfinal.bean.Publication"%>
<%@page import="cal.tpfinal.bean.User"%>
<%@page import="java.util.List"%>
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
			User user = (User)session.getAttribute("user"); 
			session.setAttribute("user", user);
			session.setAttribute("idAfficher", user.getCredential().getId());
			session.setAttribute("idUser", user.getCredential().getId());
			List<Publication> feedAccueil;
			if((List<Publication>)session.getAttribute("feedAccueil") == null){
				feedAccueil = new ArrayList<Publication>();
			}else{
				feedAccueil = (List<Publication>)session.getAttribute("feedAccueil");
			}
			
			session.setAttribute("feedAccueil",feedAccueil);
			%>
		<nav class="navbar navbar-default navbar-fixed-top">
          <div class="container">
              <div class="navbar-header">
                  <a class="navbar-brand" href="LoginController?action=accueil">NextChilling</a>
              </div>
              <div class="collapse navbar-collapse" id="myNavbar">
                  <ul class="nav navbar-nav navbar-right">
                  	<li><a href="UserController?action=afficherProfil&idAfficher=<%= user.getCredential().getId()%>&idUser=<%=user.getCredential().getId()%>"><span class="fa fa-user fa-2x"></span> Profil</a></li>
                  	<li class="dropdown" >
				       <li class="dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
				       <a href="#"><span class="fa fa-cog fa-2x"></span> Paramètres</a></li>

				        <ul class="dropdown-menu dropdown-menu-right" aria-labelledby="dropdownMenu1">
				          <li><a href="UserController?action=modifierInfos&idUser=<%=user.getCredential().getId()%>">Général</a></li>
				          <li><a href="UserController?action=contacter&idUser=<%=user.getCredential().getId()%>">Nous contacter</a></li>
				          <li>
				          	<form name="fermerCompte" method="post" action="LoginController?action=fermerCompte&idUser=<%= user.getCredential().getId()%>">
				          		<button type="submit">Fermer compte</button>
				          	</form>
				          </li>
				        </ul>
					</li>
                    <li><a href="LoginController?action=deconnexion&idUser=<%=user.getCredential().getId()%>"><span class="fa fa-sign-out fa-2x"></span> Déconnexion</a></li>
	            </ul>
		        </div>
		    </div>
		</nav>
		
		<section class="bg-1 container-fluid">
			<div class="row">
				<div class="col-md-2">
					<!-- Barre de navigation de côté qui permet de naviguer dans le contenu de la page -->
			        <nav id="navigCote" class="sidenav">
			            <a href="#logo"><span class="ion-earth"></span></a>
			            <a href="#solde"><span class="ion-arrow-graph-down-right"></span></a>
			            <a href="#innov"><span class="ion-power"></span></a>
			            <a href="#propos"><span class="ion-person"></span></a>
			            <a href="#qualites"><span class="ion-clipboard"></span></a>
			            <a href="#map"><span class="ion-map"></span></a>
			        </nav>
				</div>
				<div class="col-md-10">
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
							        <form id="publiForm" name="formPublication" action="UserController?action=publier" method="post">
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
							        </form>
						      	</div>
						      	
						    
						      	<%
						for(Publication publication : feedAccueil){
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
						        <div class="dropdown">
									  <button id="btnDrop" class="dropbtn"><span class="ion-more"></span></button>
									  <div class="dropdown-content">
									  	<form id="delPubliForm" name="delPublication" action="UserController?action=supprimerPublication&idPubli=<%=
											publication.getId()%>&idUser=<%=user.getCredential().getId() %>" method="post">
											 <a ><button id="btnSupprimerPub" type="submit">Supprimer publication</button></a>
										</form>
									  </div>
								</div>
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
														<form id="delCommentForm" name="delComment" action="UserController?action=supprimerCommentaire" method="post">
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
						          	<form id="publiForm" name="formPublication" action="UserController?action=commenter" method="post">
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
			</div>
		</section>
	</body>
</html>