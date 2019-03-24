package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.GameController;
import model.GameModel;

public class GameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("Game Servlet: doGet");
		
		// call JSP to generate empty form
		req.getRequestDispatcher("/_view/game.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("Game Servlet: doPost");
		
		// holds the error message text, if there is any
		String errorMessage = null;

		// result of calculation goes here
		String result = null;
		
		// decode POSTed form parameters and dispatch to controller
		try {
			GameController controller = new GameController();
			GameModel model = new GameModel();
			controller.setModel(model);
			
			String userInput = req.getParameter("userInput");
			
			// check for errors in the form data before using is in a calculation
			if (userInput == null) {
				errorMessage = "Please enter command";
			}
			else {
				
				result = model.getDescription("Intro");
			}
		} catch (NumberFormatException e) {
			errorMessage = "Invalid double";
		}
		
		req.setAttribute("userInput", req.getParameter("userInput"));
		req.setAttribute("result", result);
		
		// add result objects as attributes
		// this adds the errorMessage text and the result to the response
		req.setAttribute("errorMessage", errorMessage);
		
		// Forward to view to render the result HTML document
		req.getRequestDispatcher("/_view/game.jsp").forward(req, resp);
	}
}
