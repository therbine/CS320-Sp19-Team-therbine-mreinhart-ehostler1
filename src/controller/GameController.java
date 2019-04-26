package controller;

import java.util.List;

import database.persist.DerbyDatabase;
import model.GameModel;
import model.ConvertObject;

public class GameController {
	private GameModel model;
	private DerbyDatabase db;
	private ConvertObject converter;
	
	public void setModel(GameModel model) {
		this.model = model;
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
		
		List<byte[]> gameDataList = db.UserDataByUsernameQuery(model.getPlayer());
		
		if(gameDataList.isEmpty()) {
			//no saved game for this user, create a new one
			model.createNewGame();
		}
		else {
			//use getJavaObject method in ConvertObject class to change the arraylist of bytes back into a java object
			//and use that userdatamodel object as the game
			byte[] gameData = gameDataList.get(0);
			model.setGameOfCurrentPlayer(converter.getJavaObject(gameData));
		}
	}
	
	//saves current game to the database
	public void saveGame() throws Exception{
		
		//for the admin account
		if(model.getPlayer().equals("admin")) {
			model.setGameAdmin(model.getGameOfCurrentPlayer());
		}
		else {
			db.updateUserData(model.getPlayer(), converter.getByteArrayObject(model.getGameOfCurrentPlayer()));
		}
	}
}
