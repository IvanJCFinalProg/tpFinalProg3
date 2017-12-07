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
		<nav class="navbar navbar-expand-lg navbar-light bg-light">
		  <a class="navbar-brand" href="#">Navbar</a>
		  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
		    <span class="navbar-toggler-icon"></span>
		  </button>
		  <div class="collapse navbar-collapse" id="navbarNavDropdown">
		    <ul class="navbar-nav">
		      <li class="nav-item active">
		        <a class="nav-link" href="#">Home Akkk<span class="sr-only">(current)</span></a>
		      </li>
		      <li class="nav-item">
		        <a class="nav-link" href="#">Features</a>
		      </li>
		      <li class="nav-item">
		        <a class="nav-link" href="#">Pricing</a>
		      </li>
		      <li class="nav-item dropdown">
		        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		          Dropdown link
		        </a>
		        <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
		          <a class="dropdown-item" href="#">Action</a>
		          <a class="dropdown-item" href="#">Another action</a>
		          <a class="dropdown-item" href="#">Something else here</a>
		        </div>
		      </li>
		    </ul>
		  </div>
		</nav>
		<%User user = (User)request.getAttribute("user"); 
			request.setAttribute("user", user);
		%>
		<h1>Bonjour <%=user.getPrenom()%> <%=user.getNom() %></h1>
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
	</body>
</html>