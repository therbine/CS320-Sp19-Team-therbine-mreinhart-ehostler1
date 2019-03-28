package model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class GameModel {
	private static Map<String, String> descriptions = new HashMap<String, String>();
	private static LinkedList<String> outputHistory = new LinkedList<String>();
	private static String user;
	private static String lastPlayerInput;
	private static String gameDisplay;
	
	public GameModel() {
		user = null;
		lastPlayerInput = null;
		gameDisplay = null;
		outputHistory.add("You wake up on a beach with nothing but a rusty sword and the clothes on your back."
				+" To the north there seams to be a dense jungle while to your east and west there's"
				+" a bunch of washed up wreckage.");
	}
	
	public String getDescription(String name) {
		return descriptions.get(name);
	}
	
	public String getPlayerInput() {
		return lastPlayerInput;
	}
	
	public String getPlayer() {
		return user;
	}
	
	public void setPlayerInput(String input) {
		lastPlayerInput = input;
	}
	
	public void setPlayer(String player) {
		user = player;
	}
	
	public void addHistory(String newHistory) {
		outputHistory.add(newHistory);
	}
	
	public String getHistory(int index) {
		return outputHistory.get(index);
	}
	
	public Integer getHistorySize() {
		return outputHistory.size();
	}
	
	public void setGameDisplay(String display) {
		gameDisplay = display;
	}
	
	public String getGameDisplay() {
		return gameDisplay;
	}
	
	
}
