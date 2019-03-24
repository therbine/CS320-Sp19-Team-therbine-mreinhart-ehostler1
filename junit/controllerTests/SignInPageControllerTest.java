package controllerTests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import controller.SignInPageController;
import model.SignInPageModel;

public class SignInPageControllerTest {
	public class NumbersControllerTest {
		private SignInPageModel model;
		private SignInPageController controller;
		
		@Before
		public void setUp() {
			model = new SignInPageModel();
			controller = new SignInPageController();
			
			model.setFirst(2.0);
			model.setSecond(3.0);
			
			controller.setModel(model);
		}
		
		@Test
		public void testAdd() {
			controller.add();
			assertEquals((Double) 9.0, controller.add());
		}
		
		@Test
		public void testMultiply() {
			controller.multiply();
			assertEquals((Double) 6.0, controller.multiply());
		}
	}
}
