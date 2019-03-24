package controller;

import model.SignInPageModel;

public class SignInPageController {
	private SignInPageModel model;
	
	public void setModel(SignInPageModel model) {
		this.model = model;
	}
	
	public void newAccount() throws Exception {
		String newUsername = model.getNewUsername();
		String newPassword = model.getNewPassword();
		
		if(model.checkForUser(newUsername)) {
			throw new Exception("The username " + newUsername + " is already in use."); 
		}
		
		model.addUser(newUsername, newPassword);
	}

}
