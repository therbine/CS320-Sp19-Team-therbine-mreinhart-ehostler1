package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.GameController;
import controller.UserDataController;
import model.GameModel;
import model.UserDataModel;

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
		GameModel gameModel = main.Main.getGameModel();
		UserDataModel userDataModel = gameModel.getGameOfCurrentPlayer();
				
		// create SignInPage controller - controller does not persist between requests
		// must recreate it each time a Post comes in
		GameController gameController = new GameController();
		UserDataController userDataController = new UserDataController();
		
		// assign model reference to controller so that controller can access model
		gameController.setModel(gameModel);
		userDataController.setModel(userDataModel);
		
		// holds the error message text, if there is any
		String errorMessage = null;
		//update user input
		userDataModel.setPlayerInput(req.getParameter("userInput"));
		
		//command action
		main.Main.getCommand().action(userDataModel);
		
		// add result objects as attributes
		// this adds the errorMessage text and the result to the response
		req.setAttribute("errorMessage", errorMessage);
		req.setAttribute("systeminfo", gameModel);
		req.setAttribute("gameinfo", userDataModel);
		
		// Forward to view to render the result HTML document
		req.getRequestDispatcher("/_view/game.jsp").forward(req, resp);
	}
}
