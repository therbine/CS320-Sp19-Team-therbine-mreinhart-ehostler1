package model;

import java.util.HashMap;
import java.util.Map;

public class GameModel {
	private static Map<String, String> descriptions;
	private static Map<String, UserDataModel> savedGames;
	private static String player;
	
	public GameModel() {
		player = null;
		descriptions = new HashMap<String, String>();
		savedGames = new HashMap<String, UserDataModel>();
	}
	
	public String getDescription(String name) {
		return descriptions.get(name);
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
	
	public void setPlayer(String player) {
		this.player = player;
	}
}
