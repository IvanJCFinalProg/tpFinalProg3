<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>NextChill - Connexion</title>
		<link href="css/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet">
        <link href="css/connectionStyle.css" rel="stylesheet">
		<link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet">
		<link rel="stylesheet" href="css/bootstrap.min.css">
		<script src="js/libs/jquery/jquery-3.2.1.min.js"></script>
		<script src="js/libs/bootstrap/bootstrap.js"></script>
		<script src="js/scriptLogin.js"></script>
		<script src="js/scriptVerification.js"></script>
	</head>
	
	<body>
		<div id="section_principale" class="container-fluid bg-2 text-center">
			<div class="row">
				<div class="col-md-2"></div>
				
				<div class="section_centrale col-md-4">
			        
			        <ul class="nav nav-tabs">
			            <li class="active tabs"><a data-toggle="tab" href="#connexion">Connexion</a></li>
			            <li class="tabs"><a data-toggle="tab" href="#inscription">S'inscrire</a></li>
			        </ul>
			        
					<div class="contenu tab-content">
		            	<div id="connexion" class="tab-pane fade in active">
		                	<form id="leFormulaire" name="formLogin" action="LoginController?action=loginMembre" method="post" >
		                		<div class="col-md-1"></div>
		                	 	<div class="col-md-8">
		                	 		<h2 class="text-left titre-form">Connexion</h2>
		                	 		<input type="text" name="email" id="email" class="form-control email" required="required" placeholder="Email" autocomplete="on">
				                    <i class="fa fa-at"></i>
				                    <div id="error-email"></div>
				                    <input type="password" name="password" id="password" class="form-control password" required="required" placeholder="Mot de passe">
		            				<i class="fa fa-lock"></i>
		            				<div id="error-password"></div>
								  	<hr>
				                    <input type="checkbox" id="checkbox"/>
         							<label for="checkbox" ><span class="ui"></span>Se rappeler</label>
				                    <div class="submit-wrap">
			                       		<input type="submit" value="Se connecter" class="submit">
			                    	</div>
		                	 	</div>
		                	 	<div class="col-md-2"></div>
	    					</form>
	 				    </div>
	 				    
			    		<div id="inscription" class="tab-pane fade">
			                <form id="leFormulaire2" name="formInscription" action="LoginController?action=inscriptionMembre" method="post">
			                	<div class="col-md-1"></div>
			                	<div class="col-md-10">
			                		<h2 class="text-left titre-form">Inscription</h2>
			                		<hr>
			                		<div class="row">
			                			<div class="col-md-6">
			                				<input type="text" name="nomInscript" id="nom-inscription" class="form-control" required="required" placeholder="Nom">
				                			<i class="fa fa-user"></i>
			                			</div>
			                			<div class="col-md-6">
			                				<input type="text" name="prenomInscript" id="prenom-inscription" class="form-control" required="required" placeholder="Prénom">
				                    		<i class="fa fa-user-circle-o"></i>
			                			</div>
			                			<div id="error-name-incript"></div>
			                		</div>    
				                    <input type="email" name="emailInscript" id="email-inscription" class="form-control email" required="required" placeholder="Email">
				                    <i class="fa fa-at"></i>
				                    <div id="error-email-incript"></div>
				                    <input type="password" name="passwordInscript" id="password-inscription" class="form-control password" required="required" placeholder="Mot de passe">
		            				<i class="fa fa-lock"></i>
		            				<div id="error-password-incript"></div>
		            				<input type="password" name="passwordInscriptConfirm" id="passwordInscriptConfirm" class="form-control" required="required" placeholder="Confirmation du mot de passe">
		            				<i class="fa fa-check-circle"></i>
		            				<div id="error-passwordConfirm-incript"></div>
		            				<input type="date" name="dateBirth" id="date-inscription" class="form-control" required="required" placeholder="Date de naissance">
		            				<div id="error-date-incript"></div>
		            				<hr>
								    <div class="type-Sexe">
								      <input type="radio" value="Homme" id="compte-client" name="sexe" checked/>
								      <label for="compte-client" class="radio" checked>Homme</label>
								      <input type="radio" value="Femme" id="compte-entrepise" name="sexe" />
								      <label for="compte-entrepise" class="radio">Femme</label>
								    </div>
				                    <div class="submit-wrap">
				                        <input type="submit" value="S'inscrire" class="submit"/>
				                    </div>
			                	</div>
			                	<div class="col-md-2"></div>
						     </form>
			            </div>
			            
					</div>
				</div>	
				<div class="col-md-4 section_image">
					<h1>NextChill</h1>
					<h4>Bienvenue à NextCHill !</h4>
				</div>
				<div class="col-md-2"></div>

		    </div>
		</div>
	</body>
</html>