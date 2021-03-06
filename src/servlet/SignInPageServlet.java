package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.GameController;
import controller.SignInPageController;
import model.GameModel;
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
		SignInPageModel signInModel = main.Main.getSignInPageModel();
		GameModel gameModel = main.Main.getGameModel();
		
		// create SignInPage controller - controller does not persist between requests
		// must recreate it each time a Post comes in
		SignInPageController signInController = new SignInPageController();
		GameController gameController = new GameController();
		
		// assign model reference to controller so that controller can access model
		signInController.setModel(signInModel);
		gameController.setModel(gameModel);
		
		// holds the error message text, if there is any
		String errorMessage = null;
		
		//update the given username and password in the model based on what is in the username and password fields
		signInModel.setGivenUsername(req.getParameter("username"));
		signInModel.setGivenPassword(req.getParameter("password"));
		
		// check which button has been pressed
		if (req.getParameter("signInRequest") != null) {
			try {
				//perform operations for sign in
				
				if(signInController.verifySignInAccount()) {
					//credentials are correct, begin request for game page and user data
					/*
					 * 
					 * code for log in HERE
					 * 
					 */
					//set the player
					gameModel.setPlayer(signInModel.getGivenUsername());
					gameController.loadGame();
					
					req.getRequestDispatcher("/_view/game.jsp").forward(req, resp);
					return;
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
				signInController.newAccount();
				
			} catch (Exception e) {
				errorMessage = e.getMessage();
			}
		} else if (req.getParameter("deleteAccountRequest") != null) {
			try {
				//perform operations for deleting a account
				signInController.deleteAccount();
				
			} catch (Exception e) {
				errorMessage = e.getMessage();
			}
		} else {
			throw new ServletException("Unknown command");
		}
		
		// set "userinfo" attribute to the model reference
		// the JSP will reference the model elements through "userinfo"
		// also set "errorMessage" so the JSP can display any error
		req.setAttribute("userinfo", signInModel);
		req.setAttribute("errorMessage", errorMessage);
		
		// now call the JSP to render the new page
		req.getRequestDispatcher("/_view/signInPage.jsp").forward(req, resp);
	}
}
