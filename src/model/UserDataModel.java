package model;

import java.util.LinkedList;

public class UserDataModel {
	private LinkedList<String> outputHistory;
	private String lastPlayerInput;
	private String gameDisplay;
	
	
	public UserDataModel() {
		this.outputHistory = new LinkedList<String>();
		this.lastPlayerInput = null;
		this.gameDisplay = null;
		this.outputHistory.add("You wake up on a beach with nothing but a rusty sword and the clothes on your back."
				+" To the north there seams to be a dense jungle while to your east and west there's"
				+" a bunch of washed up wreckage.");
	}
	
	public String getPlayerInput() {
		return this.lastPlayerInput;
	}
	
	public void setPlayerInput(String input) {
		this.lastPlayerInput = input;
	}
	
	public void addHistory(String newHistory) {
		this.outputHistory.add(newHistory);
	}
	
	public String getHistory(int index) {
		return this.outputHistory.get(index);
	}
	
	public Integer getHistorySize() {
		return this.outputHistory.size();
	}
	
	public void setGameDisplay(String display) {
		this.gameDisplay = display;
	}
	
	public String getGameDisplay() {
		return this.gameDisplay;
	}
	public int getPlayerpostion(){
		return 1;
	}

}
