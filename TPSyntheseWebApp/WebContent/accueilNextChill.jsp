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
		<%	User user = (User)request.getAttribute("user");  
        	request.setAttribute("user", user);     
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
				<form id="delPubliForm" name="delPublication[<%=publication.getId()%>]" action="UserController?action=supprimerPublication" method="post">
					<button type="submit">Supprimer publication</button>
					<input type="hidden" name="idPubli" value="<%=publication.getId()%>"></input>
					<input type="hidden" name="idUser" value="<%=user.getCredential().getId()%>"></input>
				</form>
				<%
					}
				%>
			
			</div>
			<div>
				<form id="publiForm" name="formPublication[<%=publication.getId()%>]" action="UserController?action=commenter" method="post">
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
					<br>
					<p>Commentaire #<%=commentaire.getId() %> : <%=commentaire.getContent() %>,
					<%=commentaire.getDate_publication() %>
					(Publication #<%=commentaire.getId_Publication() %>,
					User #<%=commentaire.getId_User() %>)</p>
				<%
					if(user.getCredential().getId() == publication.getId_User() || user.getCredential().getId() == commentaire.getId_User()){
				%>
					<form id="delCommentForm" name="delComment[<%=commentaire.getId()%>]" action="UserController?action=supprimerCommentaire" method="post">
						<button type="submit">Supprimer Commentaire</button>
						<input type="hidden" name="idPubli" value="<%=commentaire.getId_Publication()%>"></input>
						<input type="hidden" name="idCommentaire" value="<%=commentaire.getId()%>"></input>
						<input type="hidden" name="idUserPublication" value="<%= publication.getId_User()%>"></input>
					</form>
				<%
					}
				%>
			<%
				}
			%>
			</div>
			<div>
			</div>
			<br><br>
		<%
			
		}
		
		
		%>
		
	</body>
</html>