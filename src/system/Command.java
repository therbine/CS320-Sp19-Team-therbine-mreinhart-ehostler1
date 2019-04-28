package system;

import java.util.ArrayList;
import java.util.List;

import model.UserDataModel;
import world.World;
import controller.UserDataController;
import main.Main;

public class Command {
	private static List<String> commands;
	private static List<String> specifiers;
	
	public Command() {
		commands = new ArrayList<String>();
		specifiers = new ArrayList<String>();
		
		//COMMANDS
		commands.add("help");
		commands.add("move");
		commands.add("describe");
		commands.add("map");
		
		//SPECIFIERS
		specifiers.add("");
		specifiers.add("none");
		specifiers.add("north");
		specifiers.add("south");
		specifiers.add("east");
		specifiers.add("west");
		specifiers.add("sword");
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
		
		System.out.println("The command is: >"+ command +"<");
		System.out.println("The specifier is: >"+specifier +"<");
		
		//check if the given command and specifier exist and execute accordingly
		if(commands.contains(command)) {
			if(specifiers.contains(specifier)) {
				//correct command and specifier
				model.addHistory(input);
				
				execute(command, specifier, model);
				
			}
			else {
				//incorrect specifier
				model.addHistory("-Invalid Specifier-");
			}
		}
		else {
			//incorrect command
			//model.addHistory("-Invalid Command-");
		}
		
		contoller.updateGameDisplay();
	}
	

	//executes the needed command method
	public void execute(String command, String specifier, UserDataModel model) {
		//execute the necessary actions of the given valid command
		if(command.equals("help")) {
			help(specifier, model);
		}
		else if(command.equals("move")) {
			move(specifier, model);
		}
		else if(command.equals("describe")) {
			describe(specifier, model);
		}
		else if(command.equals("map")) {
			map(specifier, model);
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
		SystemOutput output = Main.getSystemOutput();
		int x = model.getWorld().getPlayerCoords().getX();
		int y = model.getWorld().getPlayerCoords().getY();
		World world = model.getWorld();
		
		//determine the direction
		if(specifier.equals("north")) {
			//execute code for moving north
			try {
				world.setPlayerCoords(x, y+1);
				model.addHistory(output.getAction("move north"));
			}catch (Exception e) {
				model.addHistory(output.getAction("worldEdge"));
			}
		}
		else if(specifier.equals("south")) {
			//execute code for moving south
			try {
				world.setPlayerCoords(x, y-1);
				model.addHistory(output.getAction("move south"));
			}catch (Exception e) {
				model.addHistory(output.getAction("worldEdge"));
			}
		}
		else if(specifier.equals("east")) {
			//execute code for moving east
			try {
				world.setPlayerCoords(x+1, y);
				model.addHistory(output.getAction("move east"));
			}catch (Exception e) {
				model.addHistory(output.getAction("worldEdge"));
			}
		}
		else if(specifier.equals("west")) {
			//execute code for moving west
			try {
				world.setPlayerCoords(x-1, y);
				model.addHistory(output.getAction("move west"));
			}catch (Exception e) {
				model.addHistory(output.getAction("worldEdge"));
			}
		}
		else {
			model.addHistory("That's not a direction.");
		}
	}
	
	//the description command method
	private void describe(String specifier, UserDataModel model) {
		SystemOutput output = Main.getSystemOutput();
		
		if (output.getDescription(specifier) == null) {
			model.addHistory("I don't know what that is.");
		}
		else {
			model.addHistory(output.getDescription(specifier));
		}
	}
	
	//the map command method
	private void map(String specifier, UserDataModel model) {
		if(specifier.equals("none")) {
			
			model.getWorld().togglemap();
			
		}
		else {
			model.addHistory("-No Specifiers Available-");
		}
	}
}

