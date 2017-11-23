<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Accueil</title>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link href="css/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet">
		<link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet">
		<link href="css/adStyle.css" rel="stylesheet">
		<link rel="stylesheet" href="css/bootstrap.min.css">
		<script src="js/libs/jquery/jquery-3.2.1.min.js"></script>
		<script src="js/libs/bootstrap/bootstrap.js"></script>
	</head>
	<body>
		<header class="container-fluid" >
			<h1>Logo</h1>
		</header>	
		
		<!--<nav class="navbar " role="navigation">
			<ul class="nav sidebar-nav">
                <li class="sidebar-brand"><a href="#">Brand</a></li>
                <li><a href="#"><i class="fa fa-user"></i> Home</a></li>
                <li><a href="#"><i class="fa fa-user"></i> About</a></li>
                <li><a href="#">Events</a></li>
                <li><a href="#">Team</a></li>
                <li><a href="#">Services</a></li>
                <li><a href="#">Contact</a></li>
          	 </ul>
		</nav>-->
		
		<section style="color: lightblue" class="permissionWrapper bg-1 container-fluid">
			<div class="col-md-2"></div>
			<div class="col-md-10">
				<h1>Liste des utilisateurs</h1>
				<div class="clear"></div>
				<table id="listFilterPermission" class="listFilterContainer permissionsTable" cellspacing="0" cellpadding="0">
				    <thead id="permissionsHead">
				      <tr class="doNotFilter">
				        <th>Nom, Prénom</th>
				        <th><div id="view" class="permissionTag" data-perm="view">View</div></th>
				        <th><div id="edit" class="permissionTag" data-perm="edit">Edit</div></th>
				        <th><div id="delete" class="permissionTag" data-perm="delete">Delete</div></th>
				        <th><div id="owner" class="permissionTag" data-perm="owner">Owner</div></th>
				        <th><div id="admin" class="permissionTag" data-perm="admin">Admin</div></th>
				        <th></th>
				      </tr>
				    </thead>
				    <tbody id="permissionsBody">
				      <tr>
				        <td><span class="iconUser"></span><span contenteditable="true" class="userName">Chuck Norris</span></td>
				        <td><div class="permissionTag active" data-perm="view">View</div></td>
				        <td><div class="permissionTag active" data-perm="edit">Edit</div></td>
				        <td><div class="permissionTag active" data-perm="delete">Delete</div></td>
				        <td><div class="permissionTag active" data-perm="owner">Owner</div></td>
				        <td><div class="permissionTag active" data-perm="admin">Admin</div></td>
				        <td><a href="#" class="iconRemove deleteUser" title="Remove this user"></a></td>
				      </tr>
				    </tbody>
				</table>
			</div>
			<div class="col-md-2"></div>
		</section>
	
	</body>
</html>