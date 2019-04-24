package database.persist;

import model.UserDataModel;
import java.util.List;

public interface IDatabase {
	public List<String> PasswordByUsernameQuery(String username);
	public List<byte[]> UserDataByUsernameQuery(String username);
	public Integer insertNewUser(String username, String password);
}
