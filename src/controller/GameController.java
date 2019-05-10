package controller;

import java.util.List;

import database.persist.*;
import model.GameModel;
import model.UserDataModel;
import org.apache.commons.lang.SerializationUtils;

public class GameController {
	private GameModel model;
	private IDatabase db;
	
	public GameController() {
		model = null;
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();
	}
	
	public void setModel(GameModel model) {
		this.model = model;
		db = new DerbyDatabase();
	}
	
	//if a game doesn't already exist in the database, it creates a new game and loads it into the model
	//if a game already exists in the database it loads the game data into the model
	//does not ever save anything to the database at any point
	public void loadGame() throws Exception {
		
		//for the admin account
		if(model.getPlayer().equals("admin")) {
			model.setGameOfCurrentPlayer(model.getGameAdmin());
			return;
		}
		
		System.out.println("Getting list of byte arrays for "+model.getPlayer());
		List<byte[]> gameDataList = db.UserDataByUsernameQuery(model.getPlayer());
		
		if(gameDataList.get(0) == null) {
			System.out.println("Creating new game for user.");
			//no saved game for this user, create a new one
			model.createNewGame();
			model.getGameOfCurrentPlayer().addHistory(db.DescriptionByObjectQuery("initial").get(0));
		}
		else {
			//use getJavaObject method in ConvertObject class to change the arraylist of bytes back into a java object
			//and use that userdatamodel object as the game
			System.out.println("Attempting to load existing game from the database.");
			
			byte[] gameData = gameDataList.get(0);
			System.out.println("Loading game data: "+gameData);
			model.setGameOfCurrentPlayer((UserDataModel)SerializationUtils.deserialize(gameData));
		}
	}
	
	//saves current game to the database
	public void saveGame() throws Exception{
		System.out.println("saveGame method operating with user "+model.getPlayer());
		
		//for the admin account
		if(model.getPlayer().equals("admin")) {
			model.setGameAdmin(model.getGameOfCurrentPlayer());
		}
		else {
			System.out.println("Attempting to save game.");
			byte[] gameData = SerializationUtils.serialize(model.getGameOfCurrentPlayer());
			System.out.println("Saving game data: "+gameData);
			db.updateUserData(model.getPlayer(), gameData);
		}
	}
	
	//restarts users game to new
	public void restartGame() throws Exception{
		System.out.println("Attempting to restart game.");
		model.createNewGame();
		model.getGameOfCurrentPlayer().addHistory(db.DescriptionByObjectQuery("initial").get(0));
		saveGame();
	}
	
	public String startGame() {
			return "vanish";
	}
}
