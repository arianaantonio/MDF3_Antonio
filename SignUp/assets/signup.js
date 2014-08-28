/*
 * Author: Ariana Antonio
 * 
 * Project: SignUp
 * 
 * Package: Assets
 * 
 * File: signup.js
 * 
 * Purpose: This file controls the web form and passes the user input to MainActivity
 */ 

window.addEventListener("DOMContentLoaded", function(){
	var form = document.getElementsByTagName('form');

	//get data entered into form
	function sendData() {
		var name = document.getElementById('nameInput').value;
		var email = document.getElementById('emailInput').value;
		var birthday = document.getElementById('birthdayInput').value;
		var comments = document.getElementById('commentInput').value;

		console.log('Entered: ' + name, email, birthday, comments);
		console.log('Email: ' + email);
		
		//send data to MainActivity
		Android.saveData(name, email, birthday, comments);
		
	
		
		document.getElementById('nameInput').value = "";
		document.getElementById('emailInput').value = "";
		document.getElementById('birthdayInput').value = "";
		document.getElementById('commentInput').value = "";
		
	}

	var sendEmail = document.getElementById('button');
	sendEmail.addEventListener("click", sendData);
});
