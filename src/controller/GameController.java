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
	
}
