package model;

public class SignInPageModel {
	private static String givenUsername, givenPassword;
	
	public SignInPageModel() {
		givenUsername = null;
		givenPassword = null;
	}
	
	public void setGivenUsername(String username) {
		givenUsername = username;
	}
	
	public void setGivenPassword(String password) {
		givenPassword = password;
	}
	
	public String getGivenUsername() {
		return givenUsername;
	}
	
	public String getGivenPassword() {
		return givenPassword;
	}
}
