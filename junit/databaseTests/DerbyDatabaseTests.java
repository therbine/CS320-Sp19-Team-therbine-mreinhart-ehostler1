package databaseTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
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
		System.out.println("\n*** Testing insertBookIntoBooksTable ***");

		String title     = "Wired for War";
		String isbn      = "0-143-11684-3";
		int    published = 2009;
		String lastName  = "Singer";
		String firstName = "P.J.";
  
				
		// insert new book (and possibly new author) into DB
		Integer book_id = db.insertBookIntoBooksTable(title, isbn, published, lastName, firstName);

		// check the return value - should be a book_id > 0
		if (book_id > 0)
		{
			// try to retrieve the book and author from the DB
			// get the list of (Author, Book) pairs from DB
			authorBookList = db.findAuthorAndBookByAuthorLastName(lastName);
			
			if (authorBookList.isEmpty()) {
				System.out.println("No books found for author <" + lastName + ">");
				fail("Failed to insert new book <" + title + "> into Library DB");
			}
			// otherwise, the test was successful.  Now remove the book just inserted to return the DB
			// to it's original state, except for using an author_id and a book_id
			else {
				System.out.println("New book (ID: " + book_id + ") successfully added to Books table: <" + title + ">");
				
				// now delete Book (and its Author) from DB
				// leaving the DB in its previous state - except that an author_id, and a book_id have been used
				List<Author> authors = db.removeBookByTitle(title);				
			}
		}
		else
		{
			System.out.println("Failed to insert new book (ID: " + book_id + ") into Books table: <" + title + ">");
			fail("Failed to insert new book <" + title + "> into Library DB");
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
			assertEquals(db.PasswordByUsernameQuery(username), null);
		}
		else {
			System.out.println("Failed to insert new account (ID: " + account_id + ") into table: <" + username + ">");
			fail("Failed to insert new account <" + username + "> into Accounts DB");			
		}
	}
}
