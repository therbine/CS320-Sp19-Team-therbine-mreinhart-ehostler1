package model;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


public class SignInPageModel {
	private static Map<String, String> users = new TreeMap<String, String>();
	
	public SignInPageModel() {
		users.put("admin", "admin");
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
