package controller;

import model.GameModel;

public class GameController {
	private GameModel model;
	
	public void setModel(GameModel model) {
		this.model = model;
	}
	
	public String getMessage() {
		String result = "test";
		return result;
	}
}
