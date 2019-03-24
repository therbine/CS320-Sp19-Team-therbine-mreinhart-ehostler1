package modelTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import model.SignInPageModel;

public class SignInPageTest {
	private SignInPageModel model;
	
	@Before
	public void setUp() {
		model = new SignInPageModel();
	}
	
	@Test
	public void testSetGivenUsername() {
		model.setGivenUsername("Clever Username");
		assertEquals("Clever Username", model.getGivenUsername());
	}
	
	@Test
	public void testSetGivenPassword() {
		model.setGivenPassword("Strong Password");
		assertEquals("Strong Password", model.getGivenPassword());
	}
	
	@Test
	public void testAddUser() {
		model.addUser("Clever Username", "Strong Password");
		assertEquals(model.getPassword("Clever Username"), "Strong Password");
	}
	
	@Test
	public void testCheckForUser() {
		model.addUser("Clever Username", "Strong Password");
		assertTrue(model.checkForUser("Clever Username"));
	}
}
