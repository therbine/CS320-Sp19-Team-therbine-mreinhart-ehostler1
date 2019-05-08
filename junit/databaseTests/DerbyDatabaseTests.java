package databaseTests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import database.persist.*;

public class DerbyDatabaseTests {

	private IDatabase db = null;
	
	List<byte[]> bytes = null;
	List<String> passwords = null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		// creating DB instance here
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();		
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPasswordByUsernameQuery() {
		System.out.println("\n*** Testing PasswordByUsernameQuery ***");
		
		String username = "therbine";

		passwords = db.PasswordByUsernameQuery(username);
		
		if (passwords.isEmpty()) {
			System.out.println("No account found in database with username <" + username + ">");
			fail("No account with username <" + username + "> returned from Accounts DB");
		}
		else {			
			assertTrue(passwords.contains("therbine"));		
		}
	}

	@Test
	public void testUserDataByUsernameQuery() {
		System.out.println("\n*** Testing UserDataByUsernameQuery ***");
		String username = "therbine";
		bytes = db.UserDataByUsernameQuery(username);
		
		assertNotEquals(bytes, null);
	}

	@Test
	public void testInsertNewUser() {
		System.out.println("\n*** Testing insertNewUser ***");

		String username = "testUsername";
		String password = "testPassword";
  
				
		// insert new user into DB
		Integer account_id = db.insertNewUser(username, password);

		// check the return value - should be a account_id > 0
		if (account_id > 0)
		{
			passwords = db.PasswordByUsernameQuery(username);
			
			if (passwords.isEmpty()) {
				System.out.println("No password found for username <" + username + ">");
				fail("Failed to insert new user <" + username + "> into Accounts DB");
			}
			
			else {
				System.out.println("New user (ID: " + account_id + ") successfully added to Accounts table: <" + username + ">");
				
				// now delete Book (and its Author) from DB
				// leaving the DB in its previous state - except that an author_id, and a book_id have been used
				/*List<Account> accounts = */db.removeAccountByUsername(username);				
			}
		}
		else
		{
			System.out.println("Failed to insert new user (ID: " + account_id + ") into Accounts table: <" + username + ">");
			fail("Failed to insert new user <" + username + "> into Accounts DB");
		}
	}
	

	@Test
	public void testRemoveAccountByUsername() {
		System.out.println("\n*** Testing removeAccountByUsername ***");
		
		String username = "testing";
		String password = "remove";
				
		// insert new book (and new author) into DB
		Integer account_id = db.insertNewUser(username, password);
		
		// check to see that insertion was successful before proceeding
		if (account_id > 0) {
			db.removeAccountByUsername(username);
			assertTrue(db.PasswordByUsernameQuery(username).isEmpty());
		}
		else {
			System.out.println("Failed to insert new account (ID: " + account_id + ") into table: <" + username + ">");
			fail("Failed to insert new account <" + username + "> into Accounts DB");			
		}
	}
}
