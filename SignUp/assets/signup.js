/*
 * Author: Ariana Antonio
 * 
 * Project: SignUp
 * 
 * Package: com.arianaantonio.signup
 * 
 * File: signup.js
 * 
 * Purpose: This file controls the web form and passes the user input to MainActivity
 */ 

window.addEventListener("DOMContentLoaded", function(){
	var form = document.getElementsByTagName('form');

	function sendData() {
		var name = document.getElementById('nameInput').value;
		var email = document.getElementById('emailInput').value;
		var birthday = document.getElementById('birthdayInput').value;
		var comments = document.getElementById('commentInput').value;

		console.log('Entered: ' + name, email, birthday, comments);
		console.log('Email: ' + email);
		Android.saveData(name, email, birthday, comments);	
	}

	var sendEmail = document.getElementById('button');
	sendEmail.addEventListener("click", sendData);
});


/*
$(document).ready(function () {

	$('#newsletterForm').validate({

		rules: {
			nameInput: {
				required: true,
				lettersonly: true
			},
			emailInput: {
				required: true,
				email: true
			},
			birthdayInput: {
				required: true 
			},
			commentInput: {
				required: true
			}
		},
		submitHandler: function(form) {
			var name = $('#nameInput').val();
			var email = $('#emailInput').val();
			var birthday = $('#birthdayInput').val();
			var comments = $('commentInput').val();
			Console.log('Entered: ' + name, email, birthday, comments);

			Android.saveData(name, email, birthday, comments);
		}
	})
})*/