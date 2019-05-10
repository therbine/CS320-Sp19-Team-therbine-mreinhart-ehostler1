package model;

import java.util.LinkedList;
import java.io.Serializable;
import world.*;

import database.persist.*;

public class UserDataModel implements Serializable {
	private static final long serialVersionUID = 2L;
	
	private LinkedList<String> outputHistory;
	private String lastPlayerInput;
	private String gameDisplay;
	private World world;
	private Boolean GameOver;
	
	public UserDataModel() {
		
		this.outputHistory = new LinkedList<String>();
		this.lastPlayerInput = null;
		this.gameDisplay = null;
		this.world = new World();
		this.GameOver = false;
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
	
	public void setGameOver() {
		this.GameOver = true;
		
	}
	
	public Boolean getGameOver() {
		return this.GameOver;
	}
	
}
