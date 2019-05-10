package database.persist;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


///// TAKEN FROM LIBRARY EXAMPLE \\\\\

public class DerbyDatabase implements IDatabase {
	static {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		} catch (Exception e) {
			throw new IllegalStateException("Could not load Derby driver");
		}
	}
	
	private interface Transaction<ResultType> {
		public ResultType execute(Connection conn) throws SQLException;
	}

	private static final int MAX_ATTEMPTS = 10;

	
	@Override
	public List<String> PasswordByUsernameQuery(final String username) {
		return executeTransaction(new Transaction<List<String>>() {
			@Override
			public List<String> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					stmt = conn.prepareStatement(
							"select password " +
							"  from  accounts " +
							"  where username = ? "
					);
					stmt.setString(1, username);
					
					List<String> result = new ArrayList<String>();
					
					resultSet = stmt.executeQuery();
					
					// for testing that a result was returned
					Boolean found = false;
					
					while (resultSet.next()) {
						found = true;
						String password = resultSet.getString(1);
						result.add(password);
					}
					
					// check if the username was found
					if (!found) {
						System.out.println("<" + username + "> was not found in the accounts table");
					}
					
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	
	@Override
	public List<byte[]> UserDataByUsernameQuery(final String username) {
		return executeTransaction(new Transaction<List<byte[]>>() {
			@Override
			public List<byte[]> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {
					stmt = conn.prepareStatement(
							"select bytes " +
							"  from  accounts " +
							"  where username = ? "
					);
					stmt.setString(1, username);
					
					List<byte[]> result = new ArrayList<byte[]>();
					
					resultSet = stmt.executeQuery();
					while (resultSet.next()) {
						byte[] bytes = resultSet.getBytes(1);
						result.add(bytes);
					}
					
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	@Override
	public Integer updateUserData(final String username, final byte[] bytes) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				
				ResultSet resultSet1 = null;
				ResultSet resultSet2 = null;
				
				Integer account_id = -1;

				try {
					stmt1 = conn.prepareStatement(
							"update accounts " +
							"  set bytes = ? " +
							"  where username = ? "
					);
					
					stmt1.setBytes(1, bytes);
					stmt1.setString(2, username);
					
					stmt1.executeUpdate();
					
					stmt2 = conn.prepareStatement(
							"select account_id " +
							"  from accounts " +
							"  where username = ? "
					);
					stmt2.setString(1, username);
					
					resultSet2 = stmt2.executeQuery();
					
					if(resultSet2.next()) {
						account_id = resultSet2.getInt(1);
					}
					return account_id;
				} finally {
					DBUtil.closeQuietly(resultSet1);
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(stmt2);					
					DBUtil.closeQuietly(resultSet2);
				}
			}
		});
	}
	
	
	@Override
	public List<Account> removeAccountByUsername(final String username) {
		return executeTransaction(new Transaction<List<Account>>() {
			@Override
			public List<Account> execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;					
				
				ResultSet resultSet    = null;
				
				try {				
					// now get the Account(s) to be deleted
				
					stmt1 = conn.prepareStatement(
							"select accounts.* " +
							"  from  accounts " +
							"  where username = ? "
					);
					
					// get the Account(s)
					stmt1.setString(1, username);
					resultSet = stmt1.executeQuery();
					
					// assemble list of Accounts from query
					List<Account> accounts = new ArrayList<Account>();					
				
					while (resultSet.next()) {
						Account account = new Account();
						
						loadAccount(account, resultSet, 1);
						accounts.add(account);
					}
					
					stmt2 = conn.prepareStatement(
							"delete from accounts " +
							"  where username = ? "
					);
					
					stmt2.setString(1, username);
					stmt2.executeUpdate();
					
					System.out.println("Deleted account <" + username + "> from DB");									
					
					return accounts;
				} finally {
					DBUtil.closeQuietly(resultSet);
					
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(stmt2);				
				}
			}
		});
	}
	
	@Override
	public List<String> CommandQuery(){
		return executeTransaction(new Transaction<List<String>>() {
			@Override
			public List<String> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					stmt = conn.prepareStatement(
							"select command " +
							"  from  commands "
					);
					
					List<String> result = new ArrayList<String>();
					
					resultSet = stmt.executeQuery();
					
					// for testing that a result was returned
					Boolean found = false;
					
					while (resultSet.next()) {
						found = true;
						String command = resultSet.getString(1);
						result.add(command);
					}
					
					if (!found) {
						System.out.println("NO COMMANDS");
					}
					
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	@Override
	public Integer insertNewUser(final String username, final String password) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;		
				
				ResultSet resultSet1 = null;
				ResultSet resultSet3 = null;		
				
				// for saving account ID
				Integer account_id = -1;

				// try to retrieve account_id (if it exists) from DB, for Accounts's username, passed into query
				try {
					stmt1 = conn.prepareStatement(
							"select account_id from accounts " +
							"  where username = ? "
					);
					stmt1.setString(1, username);
					
					resultSet1 = stmt1.executeQuery();

					
					// if Account was found then save account_id					
					if (resultSet1.next())
					{
						account_id = resultSet1.getInt(1);
						System.out.println("Account <" + username + "> found with password: " + password);						
					}
					else
					{
						System.out.println("Account <" + username + "> not found");
				
						// if the Account is new, insert new Account into Accounts table
						if (account_id <= 0) {
							// prepare SQL insert statement to add Account to Accounts table
							stmt2 = conn.prepareStatement(
									"insert into accounts (username, password) " +
									"  values(?, ?) "
							);
							
							stmt2.setString(1, username);
							stmt2.setString(2, password);
							
							stmt2.executeUpdate();
							
							System.out.println("New account <" + username + "> inserted in Authors table");						
						
							// try to retrieve account_id for new Account - DB auto-generates account_id
							stmt3 = conn.prepareStatement(
									"select account_id from accounts " +
									"  where username = ? "
							);
							stmt3.setString(1, username);
												
							resultSet3 = stmt3.executeQuery();
													
							if (resultSet3.next())
							{
								account_id = resultSet3.getInt(1);
								System.out.println("New account <" + username + "> ID: " + account_id);						
							}
							else	// really should throw an exception here - the new author should have been inserted, but we didn't find them
							{
								System.out.println("New account <" + username + "> not found in Authors table (ID: " + account_id);
							}
						}
					}
					
					return account_id;
				} finally {
					DBUtil.closeQuietly(resultSet1);
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(stmt2);					
					DBUtil.closeQuietly(resultSet3);
					DBUtil.closeQuietly(stmt3);
				}
			}
		});
	}
	
	@Override
	public List<String> SpecifierByCommandQuery(final String command) {
		return executeTransaction(new Transaction<List<String>>() {
			@Override
			public List<String> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					stmt = conn.prepareStatement(
							"select specifier " +
							"  from  commands " +
							"  where command = ? "
					);
					stmt.setString(1, command);
					
					List<String> result = new ArrayList<String>();
					
					resultSet = stmt.executeQuery();
					
					// for testing that a result was returned
					Boolean found = false;
					
					while (resultSet.next()) {
						found = true;
						String specifier = resultSet.getString(1);
						result.add(specifier);
					}
					
					// check if the command was found
					if (!found) {
						System.out.println("<" + command + "> was not found in the commands table");
					}
					
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	@Override
	public List<String> DescriptionByObjectQuery(final String object) {
		return executeTransaction(new Transaction<List<String>>() {
			@Override
			public List<String> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					stmt = conn.prepareStatement(
							"select description " +
							"  from  descriptions " +
							"  where object = ? "
					);
					stmt.setString(1, object);
					
					List<String> result = new ArrayList<String>();
					
					resultSet = stmt.executeQuery();
					
					// for testing that a result was returned
					Boolean found = false;
					
					while (resultSet.next()) {
						found = true;
						String description = resultSet.getString(1);
						result.add(description);
					}
					
					// check if the object was found
					if (!found) {
						System.out.println("<" + object + "> was not found in the descriptions table");
					}
					
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	private void loadAccount(Account account, ResultSet resultSet, int index) throws SQLException {
		account.setAccountId(resultSet.getInt(index++));
		account.setUsername(resultSet.getString(index++));
		account.setPassword(resultSet.getString(index++));
		account.setBytes(resultSet.getBytes(index++));
	}
	
	
	public<ResultType> ResultType executeTransaction(Transaction<ResultType> txn) {
		try {
			return doExecuteTransaction(txn);
		} catch (SQLException e) {
			throw new PersistenceException("Transaction failed", e);
		}
	}
	
	public<ResultType> ResultType doExecuteTransaction(Transaction<ResultType> txn) throws SQLException {
		Connection conn = connect();
		
		try {
			int numAttempts = 0;
			boolean success = false;
			ResultType result = null;
			
			while (!success && numAttempts < MAX_ATTEMPTS) {
				try {
					result = txn.execute(conn);
					conn.commit();
					success = true;
				} catch (SQLException e) {
					if (e.getSQLState() != null && e.getSQLState().equals("41000")) {
						// Deadlock: retry (unless max retry count has been reached)
						numAttempts++;
					} else {
						// Some other kind of SQLException
						throw e;
					}
				}
			}
			
			if (!success) {
				throw new SQLException("Transaction failed (too many retries)");
			}
			
			// Success!
			return result;
		} finally {
			DBUtil.closeQuietly(conn);
		}
	}

	
	private Connection connect() throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:derby:./CS320-2019-ProceedWithCaution-DB/game.db;create=true");		
		
		// Set autocommit() to false to allow the execution of
		// multiple queries/statements as part of the same transaction.
		conn.setAutoCommit(false);
		
		return conn;
	}
	
	//  creates the Accounts, Commands, and Descriptions tables
	public void createTables() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;
			
				try {
					stmt = conn.prepareStatement(
						"create table accounts (" +
						"	account_id integer primary key " +
						"		generated always as identity (start with 1, increment by 1), " +									
						"	username varchar(40)," +
						"	password varchar(40)," +
						"   bytes blob" +
						")"
					);
					stmt.executeUpdate();
					
					System.out.println("Accounts table created");
					
					stmt2 = conn.prepareStatement(
							"create table commands (" +
							"	command_id integer primary key " +
							"		generated always as identity (start with 1, increment by 1), " +
							"	command varchar(15), " +
							"	specifier varchar(15)" +
							")"
					);
					stmt2.executeUpdate();
					
					System.out.println("Commands table created");
					
					stmt3 = conn.prepareStatement(
							"create table descriptions (" +
							"	description_id integer primary key " +
							"		generated always as identity (start with 1, increment by 1), " +
							"	object varchar(25), " +
							"	description varchar(500)" +
							")"
					);
					stmt3.executeUpdate();
					
					System.out.println("Descriptions table created");
					
					return true;
				} finally {
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(stmt2);
					DBUtil.closeQuietly(stmt3);
				}
			}
		});
	}
	
	// loads data retrieved from CSV files into DB tables in batch mode
	public void loadInitialData() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				List<Account> accountList;
				List<StringPair> descriptionList;
				List<StringPair> commandList;
				
				try {
					accountList = InitialData.getAccounts();
					descriptionList = InitialData.getDescriptions();
					commandList = InitialData.getCommands();
				} catch (IOException e) {
					throw new SQLException("Couldn't read initial data", e);
				}

				PreparedStatement insertAccount = null;
				PreparedStatement insertCommand = null;
				PreparedStatement insertDescription = null;

				try {
					///// Accounts Table \\\\\
					insertAccount = conn.prepareStatement("insert into accounts (username, password, bytes) values (?, ?, ?)");
					for (Account account : accountList) {
						insertAccount.setString(1, account.getUsername());
						insertAccount.setString(2, account.getPassword());
						insertAccount.setBytes(3, account.getBytes());
						insertAccount.addBatch();
					}
					insertAccount.executeBatch();
					System.out.println("Accounts table populated");
					
					///// Commands Table \\\\\
					insertCommand = conn.prepareStatement("insert into commands (command, specifier) values (?, ?)");
					for (StringPair command : commandList) {
						insertCommand.setString(1, command.getFirst());
						insertCommand.setString(2, command.getSecond());
						insertCommand.addBatch();
					}
					insertCommand.executeBatch();
					System.out.println("Commands table populated");
					
					///// Descriptions Table \\\\\
					insertDescription = conn.prepareStatement("insert into descriptions (object, description) values (?, ?)");
					for (StringPair description : descriptionList) {
						insertDescription.setString(1, description.getFirst());
						insertDescription.setString(2, description.getSecond());
						insertDescription.addBatch();
					}
					insertDescription.executeBatch();
					System.out.println("Descriptions table populated");
					
					return true;
				} finally {
					DBUtil.closeQuietly(insertAccount);
					DBUtil.closeQuietly(insertCommand);
					DBUtil.closeQuietly(insertDescription);
				}
			}
		});
	}
	
	// The main method creates the database tables and loads the initial data.
	public static void main(String[] args) throws IOException {
		System.out.println("Creating tables...");
		DerbyDatabase db = new DerbyDatabase();
		db.createTables();
		
		System.out.println("Loading initial data...");
		db.loadInitialData();
		
		System.out.println("Account DB successfully initialized!");
	}
}
