/**
 * 
 */

$(document).ready(function(){
	
    // Animation sur input nom-inscription
    $('#nom-inscription').mouseover(function() {
        $('#nom-inscription').css({
            "border-color":"#536976"
        });
        $(".fa-user").css({
            "font-size": "1em",
            "opacity": "1",
            "left":"23px",
            "color": "#fff",
            "transition": "all 0.25s ease-out"
        });
    });

    $('#nom-inscription').mouseout(function() {
        $('#nom-inscription').css({
            "border-color":"#ccc"
        });
        $(".fa-user").css({
            "font-size": "1em",
            "position": "absolute",
            "margin-top": "-38px",
            "opacity": "0",
            "left": "0",
            "transition": "all 0.1s ease-in"
        });
    });

    $('#nom-inscription').click(function() {
        $('#nom-inscription').css({
            "border-color":"#536976"
        });
        $(".fa-user").css({
            "font-size": "1em",
            "opacity": "1",
            "left":"23px",
            "color": "#ffffff",
            "transition": "all 0.25s ease-out"
        });
    });
    
 // Animation sur input prenom
    $('#prenom-inscription').mouseover(function() {
        $('#prenom-inscription').css({
            "border-color":"#536976"
        });
        $(".fa-user-circle-o").css({
            "font-size": "1em",
            "opacity": "1",
            "left":"21px",
            "color": "#fff",
            "transition": "all 0.25s ease-out"
        });
    });

    $('#prenom-inscription').mouseout(function() {
        $('#prenom-inscription').css({
            "border-color":"#ccc"
        });
        $(".fa-user-circle-o").css({
            "font-size": "1em",
            "position": "absolute",
            "margin-top": "-38px",
            "opacity": "0",
            "left": "0",
            "transition": "all 0.1s ease-in"
        });
    });

    $('#prenom-inscription').click(function() {
        $('#prenom-inscription').css({
            "border-color":"#536976"
        });
        $(".fa-user-circle-o").css({
            "font-size": "1em",
            "opacity": "1",
            "left":"21px",
            "color": "#ffffff",
            "transition": "all 0.25s ease-out"
        });
    });

// Animation sur input password
    $('.password').mouseover(function() {
        $('.password').css({
            "border-color":"#536976"
        });
        $(".fa-lock").css({
            "font-size": "1em",
            "opacity": "1",
            "left":"23px",
            "color": "#fff",
            "transition": "all 0.25s ease-out"
        });
    });

    $('.password').mouseout(function() {
        $('.password').css({
            "border-color":"#ccc"
        });
        $(".fa-lock").css({
            "font-size": "1em",
            "position": "absolute",
            "margin-top": "-38px",
            "opacity": "0",
            "left": "0",
            "transition": "all 0.1s ease-in"
        });
    });

    $('.password').click(function() {
        $('.password').css({
            "border-color":"#536976"
        });
        $(".fa-lock").css({
            "font-size": "1em",
            "opacity": "1",
            "left":"23px",
            "color": "#ffffff",
            "transition": "all 0.25s ease-out"
        });
    });

    // pasword - confirmation
    $('#passwordInscriptConfirm').mouseover(function() {
        $('#passwordInscriptConfirm').css({
            "border-color":"#536976"
        });
        $(".fa-check-circle").css({
            "font-size": "1em",
            "opacity": "1",
            "left":"21px",
            "color": "#fff",
            "transition": "all 0.25s ease-out"
        });
    });

    $('#passwordInscriptConfirm').mouseout(function() {
        $('#passwordInscriptConfirm').css({
            "border-color":"#ccc"
        });
        $(".fa-check-circle").css({
            "font-size": "1em",
            "position": "absolute",
            "margin-top": "-38px",
            "opacity": "0",
            "left": "0",
            "transition": "all 0.1s ease-in"
        });
    });

    $('#passwordInscriptConfirm').click(function() {
        $('#passwordInscriptConfirm').css({
            "border-color":"#536976"
        });
        $(".fa-check-circle").css({
            "font-size": "1em",
            "opacity": "1",
            "left":"21px",
            "color": "#ffffff",
            "transition": "all 0.25s ease-out"
        });
    });
    
    //
    $('.email').mouseover(function() {
        $('.email').css({
            "border-color":"#536976"
        });
        $(".fa-at").css({
            "font-size": "1em",
            "opacity": "1",
            "left":"22px",
            "color": "#fff",
            "transition": "all 0.25s ease-out"
        });
    });

    $('.email').mouseout(function() {
        $('.email').css({
            "border-color":"#ccc"
        });
        $(".fa-at").css({
            "font-size": "1em",
            "position": "absolute",
            "margin-top": "-38px",
            "opacity": "0",
            "left": "0",
            "transition": "all 0.1s ease-in"
        });
    });

    $('.email').click(function() {
        $('.email').css({
            "border-color":"#536976"
        });
        $(".fa-at").css({
            "font-size": "1em",
            "opacity": "1",
            "left":"22px",
            "color": "#ffffff",
            "transition": "all 0.25s ease-out"
        });
    });

});