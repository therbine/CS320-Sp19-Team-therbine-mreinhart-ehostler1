package system;

import java.util.ArrayList;
import java.util.List;

import model.UserDataModel;
import controller.UserDataController;

public class Command {
	private List<String> commands;
	
	public Command() {
		commands = new ArrayList<String>();
		commands.add("help");
	}
	
	//checks the most recent player input and performs an action based on that
	public void action(UserDataModel model) {
		UserDataController contoller = new UserDataController();
		contoller.setModel(model);
		
		if(commands.contains(model.getPlayerInput())) {
			//execute the valid command
			model.addHistory(model.getPlayerInput());
			execute(model.getPlayerInput(), model);
		}
		else {
			//invalid command
			model.addHistory("Invalid Command");
		}
		
		contoller.updateGameDisplay();
	}
	
	private void execute(String command, UserDataModel model) {
		//execute the necessary actions of the given valid command
		if(command.equals("help")) {
			model.addHistory("No help was found.");
		}
	}
	
}

