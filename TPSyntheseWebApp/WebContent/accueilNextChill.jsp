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
		<!--<link href="vendor/datatables/dataTables.bootstrap4.css" rel="stylesheet">-->
	</head>
	<body>
		<%	
			User profil = ServiceUser.getUserById(10, ServiceUser.fromToXML(ServiceApp.getValue("2", 2)));
			User user = ServiceUser.getUserById(9, ServiceUser.fromToXML(ServiceApp.getValue("2", 2)));
			//User user = (User)request.getAttribute("user");  
        	request.setAttribute("user", profil);     
	    %> 
	      <nav class="navbar navbar-inverse"> 
	        <div class="container-fluid"> 
	          <div class="navbar-header"> 
	            <a class="navbar-brand" >Bonjour <%=user.getPrenom()%> <%=user.getNom() %></a> 
	          </div> 
	          <ul class="nav navbar-nav"> 
	            <li class="active"><a href="#">Home</a></li> 
	            <li><a href="#">Page 1</a></li> 
	            <li><a href="#">Page 2</a></li> 
	          </ul> 
	          <ul class="nav navbar-nav navbar-right"> 
	            <li><a href="#"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li> 
	            <li><a href="#"><span class="glyphicon glyphicon-log-in"></span> Login</a></li> 
	          </ul> 
	        </div> 
      	</nav> 
     
   	 	<h1></h1> 
   		<form id="publiForm" name="formPublication" action="UserController?action=publier" method="post"> 
			<textarea name="publication" rows=4 cols=40 value=""></textarea>
			<input type="hidden" name="idUser" value="<%=user.getCredential().getId()%>"/>
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
			<br>
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
		
	</body>
</html>