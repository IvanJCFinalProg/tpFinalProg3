/**
 * 
 */
$(document).ready(function(){
	
	var errorEmail = false;
	var errorPassword = false;
	var errorNomInscript = false;
	var errorEmailInscript = false;
	var errorPrenomInscript = false;
	var errorPasswordInscript = false;
	var errorPasswordConfirmInscript = false;
	var errorAnneeNaissance = false;
	
	var errorNomNew = false;
	var errorPrenomNew = false;
	var errorEmailNew = false;
	
	var errorPasswordNew = false;
	
	$("#email").focusout(function() {
		validateEmailLogin();
	});
	
	$("#password").focusout(function() {
		validatePasswordLogin();
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
	
	$("#passwordInscriptConfirm").focusout(function() {
		validatePasswordConfirm();
	});
	
	$("#email-inscription").focusout(function() {
		validateEmail();
	});
	
	$("#date-inscription").focusout(function(){
		validateBirthDate();
	});
	
	// Erreur de user
	$("#email").focusout(function(){
		$("#error-user").hide();
	});
	
	$("#nom-new").focusout(function() {
		validateNewNom();
	});
	
	$("#prenom-new").focusout(function() {
		validateNewPrenom();
	});
	
	$("#email-new").focusout(function() {
		validateNewEmail();
	});
	
	$("#password-new").focusout(function() {
		validateNewPassword()
	});
	
	function validateNewPassword() {
		var password_length = $("#password-new").val().length;
		if(password_length <= 0 ){
			$("#error-password-new").html("<h5 class=\"errormsg\">Le mot de passe est requis.</h5>");
			$("#error-password-new").show();
			$("#password-new").css({"border-color":"#dd4b39"});
			errorPasswordNew = true;
		}
		else if(password_length < 8){
			$("#error-password-new").html("<h5 class=\"errormsg\">Les mots de passe sont faciles à deviner.</h5>" +
			"<h5 class=\"errormsg\">Veuillez recommancer en utilisant au moins 8 caractères</h5>");
			$("#password-new").css({"border-color":"#dd4b39"});
			$("#error-password-new").show();
			errorPasswordNew = true;
			
		}else{	
			$("#password-new").css({"border-color":""});
			$("#error-password-new").hide();
		}
	}
	
	/*** VALIDATION _ NEW INFOS***/
	function validateNewNom() {
		var pattern = new RegExp(/^[a-z]{2,20}$/i);
		var nom_length = $("#nom-new").val().length;
		if(nom_length==0 || (pattern.test($("#nom-new").val())) ){
			$("#nom-new").css({"border-color":""});
			$("#error-nom-new").hide();
		}else{
			$("#error-nom-new").html("<h5 class=\"errormsg\">Votre nom doit être composé de 2-20 charactères.</h5>");
			$("#error-nom-new").show();
			$("#nom-new").css({"border-color":"#dd4b39"});
			errorNomNew = true;
			
		}
	}
	
	function validateNewPrenom() {
		var pattern = new RegExp(/^[a-z]{2,20}$/i);
		var prenom_length = $("#prenom-new").val().length;
		if(prenom_length==0 || pattern.test($("#prenom-new").val()) ){
			$("#prenom-new").css({"border-color":""});
			$("#error-prenom-new").hide();
		}else{	
			$("#error-prenom-new").html("<h5 class=\"errormsg\">Votre prenom doit être composé de 2-20 charactères.</h5>");
			$("#error-prenom-new").show();
			$("#prenom-new").css({"border-color":"#dd4b39"});
			errorPrenomNew = true;
		}
	}
	
	function validateNewEmail() {
		var pattern = new RegExp(/^[a-z][\w.-]{4,20}[a-z0-9]@[a-z]*\.[a-z]{2,4}$/i);
		var email_length = $("#email-new").val().length;
		if(email_length==0 || pattern.test($("#email-new").val())){
			$("#email-new").css({"border-color":""});
			$("#error-email-new").hide();
		}else{
			$("#email-new").css({"border-color":"#dd4b39"});
			$("#error-email-new").html("<h5 class=\"errormsg\">L'adresse email est invalide.</h5>");
			$("#error-email-new").show;
			errorEmailNew = true;
		}
	}
	/*** ***/
	
	function validateBirthDate(){
		var str = $("#date-inscription").val();
		var age = (new Date(Date.now() - (new Date(str)).getTime())).getUTCFullYear() - 1970;
		errorAnneeNaissance = ( age > 120 || age < 13)? true:false;
		if(errorAnneeNaissance){
			$("#error-date-incript").html("<h5 class=\"errormsg\">Vous devez être agé entre 13 et 120 ans.</h5>");
			$("#error-date-incript").show;
			$("#email-inscription").css({"border-color":"#dd4b39"});
		}else{
			$("#email-inscription").css({"border-color":""});
			$("#error-date-incript").hide();
		}
	}
	
	/*** PARTIE LOGIN --- FORM LOGIN ***/
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
	/** FIN **/
	
	function validateNom() {
		var pattern = new RegExp(/^[a-z]{2,20}$/i);
		if(!(pattern.test($("#nom-inscription").val())) ){
			$("#error-name-incript").html("<h5 class=\"errormsg\">Votre nom doit être composé de 2-20 charactères.</h5>");
			$("#error-name-incript").show();
			$("#nom-inscription").css({"border-color":"#dd4b39"});
			errorNomInscript = true;
		}else{
			$("#nom-inscription").css({"border-color":""});
			$("#error-name-incript").hide();
		}
	}
	
	function validatePrenom() {
		var pattern = new RegExp(/^[a-z]{2,20}$/i);
		if(!(pattern.test($("#prenom-inscription").val())) ){
			$("#error-name-incript").html("<h5 class=\"errormsg\">Votre prenom doit être composé de 2-20 charactères.</h5>");
			$("#error-name-incript").show();
			$("#prenom-inscription").css({"border-color":"#dd4b39"});
			errorPrenomInscript = true;
		}else{	
			$("#prenom-inscription").css({"border-color":""});
			$("#error-name-incript").hide();
		}
	}
	
	function validatePassword() {
		var password_length = $("#password-inscription").val().length;
		if(password_length <= 0 ){
			$("#error-password-incript").html("<h5 class=\"errormsg\">Le mot de passe est requis.</h5>");
			$("#error-password-incript").show();
			$("#password-inscription").css({"border-color":"#dd4b39"});
			errorPasswordInscrip = true;
		
		}
		else if(password_length < 8){
			$("#error-password-incript").html("<h5 class=\"errormsg\">Les mots de passe sont faciles à deviner.</h5>" +
			"<h5 class=\"errormsg\">Veuillez recommancer en utilisant au moins 8 caractères</h5>");
			$("#password-inscription").css({"border-color":"#dd4b39"});
			$("#error-password-incript").show();
			errorPasswordInscrip = true;
			
		}else{	
			$("#password-inscription").css({"border-color":""});
			$("#error-password-incript").hide();
		}
	}
	
	function validatePasswordConfirm() {
		var password = $("#password-inscription").val();
		var passwordConfirm = $("#passwordInscriptConfirm").val();
		if(password != passwordConfirm){
			$("#error-passwordConfirm-incript").html("<h5 class=\"errormsg\">Les mots de passe ne se correspondent pas</h5>");
			$("#error-passwordConfirm-incript").show();
			$("#passwordInscriptConfirm").css({"border-color":"#dd4b39"});
			errorPasswordConfirmInscript = true;
		}else {
			$("#passwordInscriptConfirm").css({"border-color":""});
			$("#error-passwordConfirm-incript").hide();
		}
	}
	
	function validateEmail() {
		var pattern = new RegExp(/^[a-z][\w.-]{4,20}[a-z0-9]@[a-z]*\.[a-z]{2,4}$/i);
		if(pattern.test($("#email-inscription").val())){
			$("#email-inscription").css({"border-color":""});
			$("#error-email-incript").hide();
		}else{
			$("#email-inscription").css({"border-color":"#dd4b39"});
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
		errorAnneeNaissance = false;
		errorPasswordConfirmInscript = false;
		
		validateNom();
		validatePrenom();
		validateEmail();
		validatePassword();
		validateBirthDate();
		validatePasswordConfirm();
		
		if(!errorNomInscript && !errorEmailInscript && !errorPrenomInscript && !errorPasswordInscript && !errorAnneeNaissance && !errorPasswordConfirmInscript)
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
	
	$("#formModif").submit(function() {
		errorNomNew = false;
		errorPrenomNew = false;
		errorEmailNew = false;
		
		validateNewNom();
		validateNewPrenom();
		validateNewEmail();
		
		if(!errorNomNew && !errorPrenomNew && !errorEmailNew)
			return true;
		else
			return false;
	});
	
	$("#formModifPassword").submit(function() {
		errorPasswordNew = false;
		validateNewPassword();
		
		if(!errorPasswordNew)
			return true;
		else
			return false;
	});
	
});