package controller;

import model.SignInPageModel;

public class SignInPageController {
	private SignInPageModel model;
	
	public void setModel(SignInPageModel model) {
		this.model = model;
	}
	
	//method to create a new account using the given username and password
	public void newAccount() throws Exception {
		String newUsername = model.getGivenUsername();
		String newPassword = model.getGivenPassword();
		
		if(model.checkForUser(newUsername)) {
			throw new Exception("The username " + newUsername + " is already in use."); 
		}
		
		model.addUser(newUsername, newPassword);
	}
	
	//method to delete an existing account with the given username, if the password is correct
	public void deleteAccount() throws Exception {
		String oldUsername = model.getGivenUsername();
		String oldPassword = model.getGivenPassword();
		
		if(!model.checkForUser(oldUsername)) {
			throw new Exception("The user " + oldUsername + " does not exist.");
		}
		if(!model.getPassword(oldUsername).equals(oldPassword)) {
			throw new Exception("The given password is incorrect.");
		}
		
		model.deleteUser(oldUsername);
	}
	
	//method to check if username and password are correct
	//true if username matches password
	//false if not
	public boolean verifySignInAccount() throws Exception {
		String signInUsername = model.getGivenUsername();
		String signInPassword = model.getGivenPassword();
		
		if(!model.checkForUser(signInUsername)) {
			throw new Exception("The user " + signInUsername + " does not exist.");
		}
		if(model.getPassword(signInUsername).equals(signInPassword)) {
			return true;
		}
		else {
			return false;
		}
	}

}
