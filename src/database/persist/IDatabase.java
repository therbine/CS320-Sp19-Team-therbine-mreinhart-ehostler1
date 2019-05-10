package database.persist;

import java.util.List;

public interface IDatabase {
	public List<String> PasswordByUsernameQuery(String username);
	public List<byte[]> UserDataByUsernameQuery(String username);
	public Integer insertNewUser(String username, String password);
	public List<Account> removeAccountByUsername(String username);
	public Integer updateUserData(String username, byte[] bytes);
	public List<String> SpecifierByCommandQuery(final String command);
	public List<String> DescriptionByObjectQuery(final String object);
}
