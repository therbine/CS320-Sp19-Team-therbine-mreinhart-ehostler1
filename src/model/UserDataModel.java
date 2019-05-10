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
	private IDatabase db;
	
	public UserDataModel() {
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();
		
		this.outputHistory = new LinkedList<String>();
		this.lastPlayerInput = null;
		this.gameDisplay = null;
		this.outputHistory.add(db.DescriptionByObjectQuery("initial").get(0));
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
