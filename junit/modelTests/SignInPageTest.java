package modelTests;

import static org.junit.Assert.assertEquals;
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
	public void testGetGivenUsername() {
		model.setGivenUsername("Clever Username");
		assertEquals(model.getGivenUsername(), "Clever Username");
	}
	
	@Test
	public void testGetGivenPassword() {
		model.setGivenPassword("Strong Password");
		assertEquals(model.getGivenPassword(), "Strong Password");
	}
}
