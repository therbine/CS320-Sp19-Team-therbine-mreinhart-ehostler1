package database.persist;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Blob;


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
					
					// check if the title was found
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
	
	
	// transaction that retrieves a list of Books with their Authors, given Author's last name
	@Override
	public List<byte[]> UserDataByUsernameQuery(final String username) {
		return executeTransaction(new Transaction<List<byte[]>>() {
			@Override
			public List<byte[]> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				// try to retrieve Authors and Books based on Author's last name, passed into query
				try {
					stmt = conn.prepareStatement(
							"select bytes " +
							"  from  accounts " +
							"  where username = ? "
					);
					stmt.setString(1, username);
					
					// establish the list of (Author, Book) Pairs to receive the result
					List<byte[]> result = new ArrayList<byte[]>();
					
					// execute the query, get the results, and assemble them in an ArrayLsit
					resultSet = stmt.executeQuery();
					while (resultSet.next()) {
						Blob blob = resultSet.getBlob(1);
						byte[] bais = blob.getBytes((long) 1, (int) blob.length());
						result.add(bais);
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

				// try to retrieve author_id (if it exists) from DB, for Author's full name, passed into query
				try {
					stmt1 = conn.prepareStatement(
							"update accounts " +
							"  set bytes = ? " +
							"  where username = ? "
					);
					
					Blob blob = conn.createBlob();
					blob.setBytes((long) 1, bytes);
					
					stmt1.setBlob(1, blob);
					stmt1.setString(2, username);
					
					// execute the query, get the result
					resultSet1 = stmt1.executeQuery();
					
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
					// now get the Book(s) to be deleted
					// we will need the book_id to remove associated entries in BookAuthors (junction table)
				
					stmt1 = conn.prepareStatement(
							"select accounts.* " +
							"  from  accounts " +
							"  where username = ? "
					);
					
					// get the Book(s)
					stmt1.setString(1, username);
					resultSet = stmt1.executeQuery();
					
					// assemble list of Books from query
					List<Account> accounts = new ArrayList<Account>();					
				
					while (resultSet.next()) {
						Account account = new Account();
						
						loadAccount(account, resultSet, 1);
						accounts.add(account);
					}
					
					// first delete entries in BookAuthors junction table
					// can't delete entries in Books or Authors tables while they have foreign keys in junction table
					// prepare to delete the junction table entries (bookAuthors)
					stmt2 = conn.prepareStatement(
							"delete from accounts " +
							"  where username = ? "
					);
					
					// delete the junction table entries from the DB for this title
					// this works if there are not multiple books with the same name
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
	public Integer insertNewUser(final String username, final String password) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;		
				
				ResultSet resultSet1 = null;
				ResultSet resultSet3 = null;		
				
				// for saving author ID and book ID
				Integer account_id = -1;

				// try to retrieve author_id (if it exists) from DB, for Author's full name, passed into query
				try {
					stmt1 = conn.prepareStatement(
							"select account_id from accounts " +
							"  where username = ? "
					);
					stmt1.setString(1, username);
					
					// execute the query, get the result
					resultSet1 = stmt1.executeQuery();

					
					// if Author was found then save author_id					
					if (resultSet1.next())
					{
						account_id = resultSet1.getInt(1);
						System.out.println("Account <" + username + "> found with password: " + password);						
					}
					else
					{
						System.out.println("Account <" + username + "> not found");
				
						// if the Author is new, insert new Author into Authors table
						if (account_id <= 0) {
							// prepare SQL insert statement to add Author to Authors table
							stmt2 = conn.prepareStatement(
									"insert into accounts (username, password, bytes) " +
									"  values(?, ?, ?) "
							);
							Blob blob = conn.createBlob();
							
							stmt2.setString(1, username);
							stmt2.setString(2, password);
							stmt2.setBlob(3, blob);
							
							// execute the update
							stmt2.executeUpdate();
							
							System.out.println("New account <" + username + "> inserted in Authors table");						
						
							// try to retrieve author_id for new Author - DB auto-generates author_id
							stmt3 = conn.prepareStatement(
									"select account_id from accounts " +
									"  where username = ? "
							);
							stmt3.setString(1, username);
							
							// execute the query							
							resultSet3 = stmt3.executeQuery();
							
							// get the result - there had better be one							
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
	
	private void loadAccount(Account account, ResultSet resultSet, int index) throws SQLException {
		account.setAccountId(resultSet.getInt(index++));
		account.setUsername(resultSet.getString(index++));
		account.setPassword(resultSet.getString(index++));
		account.setBytes(resultSet.getBytes(index++));
	}
	
	
	// wrapper SQL transaction function that calls actual transaction function (which has retries)
	public<ResultType> ResultType executeTransaction(Transaction<ResultType> txn) {
		try {
			return doExecuteTransaction(txn);
		} catch (SQLException e) {
			throw new PersistenceException("Transaction failed", e);
		}
	}
	
	// SQL transaction function which retries the transaction MAX_ATTEMPTS times before failing
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

	// TODO: Here is where you name and specify the location of your Derby SQL database
	// TODO: Change it here and in SQLDemo.java under CS320_LibraryExample_Lab06->edu.ycp.cs320.sqldemo
	// TODO: DO NOT PUT THE DB IN THE SAME FOLDER AS YOUR PROJECT - that will cause conflicts later w/Git
	private Connection connect() throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:derby:./CS320-2019-ProceedWithCaution-DB/game.db;create=true");		
		
		// Set autocommit() to false to allow the execution of
		// multiple queries/statements as part of the same transaction.
		conn.setAutoCommit(false);
		
		return conn;
	}
	
	//  creates the Authors and Books tables
	public void createTables() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
			
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
					
					return true;
				} finally {
					DBUtil.closeQuietly(stmt);
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
				
				try {
					accountList = InitialData.getAccounts();
				} catch (IOException e) {
					throw new SQLException("Couldn't read initial data", e);
				}

				PreparedStatement insertAccount = null;

				try {
					// must completely populate Authors table before populating BookAuthors table because of primary keys
					insertAccount = conn.prepareStatement("insert into accounts (username, password, bytes) values (?, ?, ?)");
					for (Account account : accountList) {
						insertAccount.setString(1, account.getUsername());
						insertAccount.setString(2, account.getPassword());
						insertAccount.setBytes(3, account.getBytes());
						insertAccount.addBatch();
					}
					insertAccount.executeBatch();
					
					System.out.println("Accounts table populated");					
					
					return true;
				} finally {
					DBUtil.closeQuietly(insertAccount);		
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
