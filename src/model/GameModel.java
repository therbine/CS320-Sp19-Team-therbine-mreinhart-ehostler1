package model;

public class GameModel {
	private static UserDataModel gameOfCurrentPlayer;
	private static String currentPlayer;
	
	public GameModel() {
		currentPlayer = null;
		gameOfCurrentPlayer = null;
	}
	
	public UserDataModel getGameOfCurrentPlayer() {
		return gameOfCurrentPlayer;
	}
	
	public void setGameOfCurrentPlayer(UserDataModel game) {
		this.gameOfCurrentPlayer = game;
	}
	
	public String getPlayer() {
		return currentPlayer;
	}
	
	public void setPlayer(String currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	
	public void createNewGame() {
		this.gameOfCurrentPlayer = new UserDataModel();
	}
}
