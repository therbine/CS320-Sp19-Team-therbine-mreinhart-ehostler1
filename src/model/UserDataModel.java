package model;

import java.util.LinkedList;
import java.io.Serializable;
import world.*;

public class UserDataModel implements Serializable {
	private static final long serialVersionUID = 2L;
	
	private LinkedList<String> outputHistory;
	private String lastPlayerInput;
	private String gameDisplay;
	private World world;
	
	
	public UserDataModel() {
		this.outputHistory = new LinkedList<String>();
		this.lastPlayerInput = null;
		this.gameDisplay = null;
		this.outputHistory.add("You wake up on a beach with nothing but a rusty sword and the clothes on your back."
				+" To the north there seams to be a dense jungle while to your east and west there's"
				+" a bunch of washed up wreckage.");
		this.world = new World();
	}
	
	public String getPlayerInput() {
		return this.lastPlayerInput;
	}
	
	public void setPlayerInput(String input) {
		this.lastPlayerInput = input;
	}
	
	public void addHistory(String newHistory) {
		this.outputHistory.add(newHistory);
		
		if(this.outputHistory.size() > 50) {
			this.outputHistory.remove();
		}
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
	
	public World getWorld(){
		return world;
	}
	
	
	
}
