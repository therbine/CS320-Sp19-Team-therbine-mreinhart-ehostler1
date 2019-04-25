package database.persist;

public class Account {
	private String username;
	private String password;
	private byte[] bytes;
	
	public Account() {
		
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public byte[] getBytes() {
		return bytes;
	}
}
