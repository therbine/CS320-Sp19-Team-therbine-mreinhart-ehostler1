package model;

import java.util.HashMap;
import java.util.Map;

public class GameModel {
	private static Map<String, UserDataModel> savedGames;
	private static String player;
	
	public GameModel() {
		player = null;
		savedGames = new HashMap<String, UserDataModel>();
	}
	
	public UserDataModel getGameOfCurrentPlayer() {
		return savedGames.get(player);
	}
	
	public void createNewGame(String user) {
		savedGames.put(user, new UserDataModel());
	}
	
	public String getPlayer() {
		return player;
	}
	
	public void setPlayer(String currentPlayer) {
		player = currentPlayer;
	}
	public int getPlayerpostion(){
		return 1;
	}
}
