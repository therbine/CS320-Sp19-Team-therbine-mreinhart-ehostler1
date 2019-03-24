package model;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


public class SignInPageModel {
	private static Map<String, String> users = new TreeMap<String, String>();
	private String givenUsername, givenPassword;
	
	public SignInPageModel() {
		users.put("admin", "admin");
		givenUsername = null;
		givenPassword = null;
	}
	
	public void setGivenUsername(String username) {
		this.givenUsername = username;
	}
	
	public void setGivenPassword(String password) {
		this.givenPassword = password;
	}
	
	public String getGivenUsername() {
		return this.givenUsername;
	}
	
	public String getGivenPassword() {
		return this.givenPassword;
	}
	
	public void addUser(String username, String password) {
		SignInPageModel.users.put(username, password);
	}
	
	public boolean checkForUser(String username) {
		return SignInPageModel.users.containsKey(username);
	}
	
	public String getPassword(String username) {
		return SignInPageModel.users.get(username);
	}
	
	public void deleteUser(String username) {
		SignInPageModel.users.remove(username);
	}
	
	public Set<String> getSetOfUsers() {
		return SignInPageModel.users.keySet();
	}
	

}
