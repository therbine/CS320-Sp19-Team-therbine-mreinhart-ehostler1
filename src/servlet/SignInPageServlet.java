package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.SignInPageController;
import model.SignInPageModel;

public class SignInPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("Sign-In Page Servlet: doGet");
		
		// call JSP to generate empty form
		req.getRequestDispatcher("/_view/signInPage.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("SignInPage Servlet: doPost");

		//get the persisted sign in page model
		SignInPageModel model = main.Main.getSignInPageModel();
		
		// create SignInPage controller - controller does not persist between requests
		// must recreate it each time a Post comes in
		SignInPageController controller = new SignInPageController();
		
		// assign model reference to controller so that controller can access model
		controller.setModel(model);
		
		// holds the error message text, if there is any
		String errorMessage = null;
		
		//update the given username and password in the model based on what is in the username and password fields
		model.setGivenUsername(getString(req, "username"));
		model.setGivenPassword(getString(req, "password"));
		
		// check which button has been pressed
		if (req.getParameter("signInRequest") != null) {
			try {
				//perform operations for sign in
				
				if(controller.verifySignInAccount()) {
					//credentials are correct, begin request for game page and user data
					/*
					 * 
					 * code for log in HERE
					 * 
					 */
					req.getRequestDispatcher("/_view/game.jsp").forward(req, resp);
					
				}
				else {
					//credentials aren't correct
					errorMessage = "Incorrect Password";
				}
			} catch (Exception e) {
				errorMessage = e.getMessage();
			}
		} else if (req.getParameter("createAccountRequest") != null) {
			try {
				//perform operations for creating a new account
				controller.newAccount();
				
			} catch (Exception e) {
				errorMessage = e.getMessage();
			}
		} else if (req.getParameter("deleteAccountRequest") != null) {
			try {
				//perform operations for deleting a account
				controller.deleteAccount();
				
			} catch (Exception e) {
				errorMessage = e.getMessage();
			}
		} else {
			throw new ServletException("Unknown command");
		}
		
		// set "userinfo" attribute to the model reference
		// the JSP will reference the model elements through "userinfo"
		// also set "errorMessage" so the JSP can display any error
		req.setAttribute("userinfo", model);
		req.setAttribute("errorMessage", errorMessage);
		
		// now call the JSP to render the new page
		req.getRequestDispatcher("/_view/signInPage.jsp").forward(req, resp);
	}

	// gets an Integer from the Posted form data, for the given attribute name
	private String getString(HttpServletRequest req, String name) {
		return req.getParameter(name);
	}
}
