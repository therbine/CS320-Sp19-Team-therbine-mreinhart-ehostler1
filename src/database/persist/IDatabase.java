package database.persist;

import model.UserDataModel;

public interface IDatabase {
	public String PasswordByUsernameQuery(String username);
	public UserDataModel UserDataByUsernameQuery(String username);
	public Integer insertNewUser(String username, String password);
}
