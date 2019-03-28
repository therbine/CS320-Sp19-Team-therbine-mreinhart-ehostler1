package controller;

import model.GameModel;

public class GameController {
	private GameModel model;
	
	public void setModel(GameModel model) {
		this.model = model;
	}
	
	public void updateHistory() {
		model.addHistory(model.getPlayerInput());
	}
	
	public void updateGameDisplay() {
		String newDisplay = "";
		for(int i = model.getHistorySize() - 1 ; i >= 0 ; i--) {
			newDisplay += "<p>" + model.getHistory(i) + "</p>";
		}
		model.setGameDisplay(newDisplay);
	}
	
}
