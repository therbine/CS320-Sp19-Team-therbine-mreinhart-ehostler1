package controller;


import model.UserDataModel;

public class UserDataController {
	private UserDataModel model;
	
	public void setModel(UserDataModel model) {
		this.model = model;
	}
	
	public void updateHistory() {
		model.addHistory(model.getPlayerInput());
	}
	
	public void updateGameDisplay() {
		String newDisplay = "";
		for(int i = 0 ; i < model.getHistorySize() ; i++) {
			newDisplay += "<p>" + model.getHistory(i) + "</p>";
		}
		model.setGameDisplay(newDisplay);
	}
}
