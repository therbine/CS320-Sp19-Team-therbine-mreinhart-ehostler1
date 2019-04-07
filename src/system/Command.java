package system;

import java.util.ArrayList;
import java.util.List;

import model.UserDataModel;
import controller.UserDataController;

public class Command {
	private static List<String> commands;
	private static List<String> specifiers;
	
	public Command() {
		commands = new ArrayList<String>();
		specifiers = new ArrayList<String>();
		
		//COMMANDS
		commands.add("help");
		commands.add("move");
		
		//SPECIFIERS
		specifiers.add("none");
		specifiers.add("north");
		specifiers.add("south");
		specifiers.add("east");
		specifiers.add("west");
	}
	
	//checks the most recent player input and performs an action based on that
	public void action(UserDataModel model) {
		UserDataController contoller = new UserDataController();
		contoller.setModel(model);
		
		//find the command and specifier
		String input = model.getPlayerInput();
		String command;
		String specifier;
		
		try {
			Integer spaceIndex = input.indexOf(" ");
			command = input.substring(0 , spaceIndex);
			specifier = input.substring(spaceIndex + 1, input.length());
		} catch(Exception e) {
			command = input;
			specifier = "none";
		}
		
		//check if the given command and specifier exist and execute accordingly
		if(commands.contains(command)) {
			if(specifiers.contains(specifier)) {
				//correct command and specifier
				execute(command, specifier, model);
			}
			else {
				//incorrect specifier
				model.addHistory("-Invalid Specifier-");
			}
		}
		else {
			//incorrect command
			model.addHistory("-Invalid Command-");
		}
		
		contoller.updateGameDisplay();
	}
	

	//executes the needed command method
	private void execute(String command, String specifier, UserDataModel model) {
		//execute the necessary actions of the given valid command
		if(command.equals("help")) {
			help(specifier, model);
		}
		else if(command.equals("move")) {
			move(specifier, model);
		}
	}

	//the help command method
	private void help(String specifier, UserDataModel model) {
		if(specifier.equals("none")) {
			
			model.addHistory("Available Commands: " + commands.toString());
			model.addHistory("Available Specifiers: " + specifiers.toString());
			
		}
		else {
			model.addHistory("-No Specifiers Available-");
		}
	}
	
	//the move command method
	private void move(String specifier, UserDataModel model) {
		
		//determine the direction
		if(specifier.equals("north")) {
			//execute code for moving north
			model.addHistory("You move north.");
		}
		else if(specifier.equals("south")) {
			//execute code for moving south
			model.addHistory("You move south");
		}
		else if(specifier.equals("east")) {
			//execute code for moving east
			model.addHistory("You move east");
		}
		else if(specifier.equals("west")) {
			//execute code for moving west
			model.addHistory("You move west");
		}
		else {
			model.addHistory("Yes, but where?");
		}
	}
	
}

