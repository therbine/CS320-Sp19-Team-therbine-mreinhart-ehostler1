package controller;

import model.SignInPageModel;
import database.persist.DerbyDatabase;

public class SignInPageController {
	private SignInPageModel model;
	private DerbyDatabase db;
	
	public void setModel(SignInPageModel model) {
		this.model = model;
	}
	
	//method to create a new account using the given username and password
	public void newAccount() throws Exception {
		String newUsername = model.getGivenUsername();
		String newPassword = model.getGivenPassword();
		
		if(!db.PasswordByUsernameQuery(newUsername).isEmpty() || newUsername.equals("admin")) {
			throw new Exception("The username " + newUsername + " is already in use."); 
		}
		else {
			db.insertNewUser(newUsername, newPassword);
		}
	}
	
	//method to delete an existing account with the given username, if the password is correct
	public void deleteAccount() throws Exception {
		String oldUsername = model.getGivenUsername();
		String oldPassword = model.getGivenPassword();
		
		if(db.PasswordByUsernameQuery(oldUsername).isEmpty()) {
			throw new Exception("The user " + oldUsername + " does not exist.");
		}
		if(!db.PasswordByUsernameQuery(oldUsername).contains(oldPassword)) {
			throw new Exception("The given password is incorrect.");
		}
		
		db.removeAccountByUsername(oldUsername);
	}
	
	//method to check if username and password are correct
	//true if username matches password
	//false if not
	public boolean verifySignInAccount() throws Exception {
		String signInUsername = model.getGivenUsername();
		String signInPassword = model.getGivenPassword();
		
		//POSSIBLY TEMPORARY allow sign in of the admin admin username and password credentials
		boolean adminAccountActive = true;
		if(signInUsername.equals("admin") && signInPassword.equals("admin") && adminAccountActive) {
			return true;
		}
		
		if(db.PasswordByUsernameQuery(signInUsername).isEmpty()) {
			throw new Exception("The user " + signInUsername + " does not exist.");
		}
		if(db.PasswordByUsernameQuery(signInUsername).contains(signInPassword)) {
			return true;
		}
		else {
			return false;
		}
	}

}
