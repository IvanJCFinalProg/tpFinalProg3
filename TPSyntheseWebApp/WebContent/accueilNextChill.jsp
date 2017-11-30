<%@page import="cal.tpfinal.bean.Commentaire"%>
<%@page import="cal.tpfinal.bean.Publication"%>
<%@page import="cal.tpfinal.bean.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Bienvenue NextChill</title>
	</head>
	<body>
		<%User user = (User)request.getAttribute("user"); 
			request.setAttribute("user", user);		
		%>
		<h1>Bonjour <%=user.getPrenom()%> <%=user.getNom() %></h1>
		<form id="publiForm" name="formPublication" action="LoginController?action=publier" method="post">
			<textarea name="publication" rows=4 cols=40></textarea>
			<input type="hidden" name="idUser" value="<%=user.getCredential().getId()%>"></input>
			<button type="submit">Publier</button>
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