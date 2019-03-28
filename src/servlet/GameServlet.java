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
		
		//get the persisted sign in page model
		GameModel model = main.Main.getGameModel();
				
		// create SignInPage controller - controller does not persist between requests
		// must recreate it each time a Post comes in
		GameController controller = new GameController();
		
		// assign model reference to controller so that controller can access model
		controller.setModel(model);
		
		// holds the error message text, if there is any
		String errorMessage = null;
		//update user input
		model.setPlayerInput(req.getParameter("userInput"));
		
		//update the history
		controller.updateHistory();
		
		/*
		 * 
		 * EXECUTE commands here
		 * 
		 */
		
		
		
		// add result objects as attributes
		// this adds the errorMessage text and the result to the response
		req.setAttribute("errorMessage", errorMessage);
		req.setAttribute("gameinfo", model);
		
		// Forward to view to render the result HTML document
		req.getRequestDispatcher("/_view/game.jsp").forward(req, resp);
	}
}
