/**
 * 
 */
$(document).ready(function(){
	
	/*$("#error-name-incript").hide();
	$("#error-email-incript").hide();
	$("#error-password-incript").hide();
	$("#error-passwordConfirm-incript").hide();*/
	//$("").hide();
	var errorEmail = false;
	var errorPassword = false;
	var errorNomInscript = false;
	var errorEmailInscript = false;
	var errorPrenomInscript = false;
	var errorPasswordInscript = false;
	var errorPasswordConfirmInscript = false;
	
	$("#email").focusout(function() {
		validateEmailLogin();
	});
	
	$("#password").focusout(function() {
		validatePasswordLogin();
	});
	
	$("#nom-inscription").focusout(function() {
		validatePassword();
	});
	
	$("#nom-inscription").focusout(function() {
		validateNom();
	});
	
	$("#prenom-inscription").focusout(function() {
		validatePrenom();
	});
	
	$("#password-inscription").focusout(function() {
		validatePassword();
	});
	
	$("#password-inscription").focusout(function() {
		validatePassword();
	});
	
	$("email-inscription").focusout(function() {
		validateEmail();
	});
	
	function validateEmailLogin() {
		var email_length = $("#email").val().length;
		if(email_length <= 0 ){
			$("#error-email").html("<h5 class=\"errormsg\">Votre email est requis.</h5>");
			$("#error-email").show();
			$("#email").css({"border-color":"#dd4b39"});
			errorEmail = true;
		}else{
			$("#email").css({"border-color":""});
			$("#error-email").hide();
		}
	}
	
	function validatePasswordLogin() {
		var password_length = $("#password").val().length;
		if(password_length <= 0 ){
			$("#error-password").html("<h5 class=\"errormsg\">Votre mot de passe est requis.</h5>");
			$("#error-password").show();
			$("#password").css({"border-color":"#dd4b39"});
			errorPassword = true;
		}else{
			$("#password").css({"border-color":""});
			$("#error-password").hide();
		}
	}
	
	function validateNom() {
		var nom_length = $("#nom-inscription").val().length;
		if(nom_length < 2 || nom_length > 20 ){
			$("#error-name-incript").html("<h5 class=\"errormsg\">Votre nom doit être composé de 2-20 charactères.</h5>");
			$("#error-name-incript").show();
			errorNomInscript = true;
		}else{
			$("#error-name-incript").hide();
		}
	}
	
	function validatePrenom() {
		var prenom_length = $("#prenom-inscription").val().length;
		if(prenom_length < 2 || prenom_length > 20 ){
			$("#error-name-incript").html("<h5 class=\"errormsg\">Votre prenom doit être composé de 2-20 charactères.</h5>");
			$("#error-name-incript").show();
			errorPrenomInscript = true;
		}else{	
			$("#error-name-incript").hide();
		}
	}
	
	function validatePassword() {
		var password_length = $("#password-inscription").val().length;
		if(password_length <= 0 ){
			$("#error-password-incript").html("<h5 class=\"errormsg\">Le mot de passe est requis.</h5>");
			$("#error-password-incript").show();
			errorPasswordInscrip = true;
		
		}
		else if(password_length < 8){
			$("#error-password-incript").html("<h5 class=\"errormsg\">Les mots de passe sont faciles à deviner.</h5>" +
			"<h5 class=\"errormsg\">Veuillez recommancer en utilisant au moins 8 caractères</h5>");
			$("#error-password-incript").show();
			errorPasswordInscrip = true;
			
		}else{	
			$("#error-password-incript").hide();
		}
	}
	
	function validateEmail() {
		var pattern = new RegExp(/^[^._-][+a-zA-Z0-9._-][^._-]{5,20}+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i);
		if(pattern.test($("#email-inscription").val())){
			$("#error-email-incript").hide();
		}else{
			$("#error-email-incript").html("<h5 class=\"errormsg\">L'adresse email est invalide.</h5>");
			$("#error-email-incript").show;
			errorEmailInscript = true;
		}
	}
	
	$("#leFormulaire2").submit(function() {
		errorNomInscript = false;
		errorEmailInscript = false;
		errorPrenomInscript = false;
		errorPasswordInscript = false;
		
		validateNom();
		validatePrenom();
		validateEmail();
		validatePassword();
		
		if(!errorNomInscript && !errorEmailInscript && !errorPrenomInscript && !errorPasswordInscript)
			return true;
		else
			return false;
	});
	
	$("#leFormulaire").submit(function() {
		errorEmail = false;
		errorPassword = false;
		
		validateEmailLogin();
		validatePasswordLogin();
		
		if(!errorEmail && !errorPassword)
			return true;
		else
			return false;
	});
	
});