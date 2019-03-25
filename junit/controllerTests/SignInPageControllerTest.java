package controllerTests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import controller.SignInPageController;
import model.SignInPageModel;

public class SignInPageControllerTest {
	private SignInPageModel model;
	private SignInPageController controller;
		
	@Before
	public void setUp() {
		model = new SignInPageModel();
		controller = new SignInPageController();
			
		model.setGivenUsername("Clever Username");
		model.setGivenPassword("Strong Password");
			
		controller.setModel(model);
	}
		
	@Test
	public void testMultiply() {
		try {
			controller.deleteAccount();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(model.checkForUser("Clever Username"), false);
	}
}
