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
import system.Command;
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
		Command command = main.Main.getCommand();
				
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
		
		//button commands
		if(req.getParameter("move") != null) {
			command.execute("move",req.getParameter("move"), userDataModel);
		}
		//restart game
		
		
		
		if(req.getParameter("Restart") != null) {
			try {
				req.setAttribute("Restart", "pressed");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(req.getParameter("Yes") != null) {
			try {
				gameController.restartGame();
				req.getRequestDispatcher("/_view/game.jsp").forward(req, resp);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(req.getParameter("No") != null) {
			try {
				req.setAttribute("Restart", null);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//save game
		if(req.getParameter("Save") != null) {
			System.out.println("Save game button pressed.");
			try {
				gameController.saveGame();
			}
			catch(Exception e) {
				
			}
		}
		
		//command action
		main.Main.getCommand().action(userDataModel);
		
		// add result objects as attributes
		// this adds the errorMessage text and the result to the response
		req.setAttribute("errorMessage", errorMessage);
		req.setAttribute("systeminfo", gameModel);
		req.setAttribute("gameinfo", userDataModel);
		req.setAttribute("player_x", userDataModel.getWorld().getPlayerCoords().getX());
		req.setAttribute("player_y", userDataModel.getWorld().getPlayerCoords().getY());
		req.setAttribute("map", userDataModel.getWorld().getMap());
		req.setAttribute("Start", gameController.startGame());
		// Forward to view to render the result HTML document
		
		req.getRequestDispatcher("/_view/game.jsp").forward(req, resp);
	}
}
