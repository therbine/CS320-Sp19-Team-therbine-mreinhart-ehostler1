package model;

public class GameModel {
	private static UserDataModel gameOfCurrentPlayer;
	private static String currentPlayer;
	
	//game for admin testing that does not persist on the database
	private static UserDataModel gameAdmin;
	
	public GameModel() {
		currentPlayer = null;
		gameOfCurrentPlayer = null;
		gameAdmin = new UserDataModel();
	}
	
	public UserDataModel getGameOfCurrentPlayer() {
		return gameOfCurrentPlayer;
	}
	
	public void setGameOfCurrentPlayer(UserDataModel game) {
		gameOfCurrentPlayer = game;
	}
	
	public String getPlayer() {
		return currentPlayer;
	}
	
	public void setPlayer(String Player) {
		currentPlayer = Player;
	}
	
	public void createNewGame() {
		gameOfCurrentPlayer = new UserDataModel();
	}
	
	public UserDataModel getGameAdmin() {
		return gameAdmin;
	}
	
	public void setGameAdmin(UserDataModel adminGame) {
		gameAdmin = adminGame;
	}
}
