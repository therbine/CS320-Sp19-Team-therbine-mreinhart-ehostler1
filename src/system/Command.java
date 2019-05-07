package system;

import java.util.ArrayList;
import java.util.List;

import database.persist.*;
import model.UserDataModel;
import world.Item;
import world.Room;
import world.World;
import controller.UserDataController;
import main.Main;

public class Command {
	private IDatabase db;
	
	public Command() {
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();
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
		if(db.SpecifierByCommandQuery(command).contains(specifier)) {
			//correct command and specifier
			model.addHistory(input);
				
			execute(command, specifier, model);
		}
		
		contoller.updateGameDisplay();
	}
	

	//executes the needed command method
	public void execute(String command, String specifier, UserDataModel model) {
		//execute the necessary actions of the given valid command
		if(model.getWorld().getMap()) {
			model.getWorld().togglemap();
		}
		
		if(command.equals("help")) {
			help(specifier, model);
		}
		else if(command.equals("move")) {
			move(specifier, model);
			model.getWorld().setmove_buttons();
		}
		else if(command.equals("look")) {
			look(specifier, model);
		}
		else if(command.equals("map")) {
			map(specifier, model);
		}
		else if(command.equals("flee")) {
			flee(specifier, model);
		}
		else if(command.equals("attack")) {
			attack(specifier, model);
		}
		else if(command.equals("use")) {
			use(specifier, model);
		}
		else if(command.equals("drop")) {
			drop(specifier, model);
		}
		else if(command.equals("take")) {
			take(specifier, model);
		}
	}

	//adds a system reply to the history
	private void reply(String replyTag, UserDataModel model) {
		
		List<String> replyList = db.DescriptionByObjectQuery(replyTag);
		
		if(replyList.isEmpty()) {
			model.addHistory("No Reply Found for this action or description");
		}
		else {
			model.addHistory(replyList.get(0));
		}
	}

	//the help command method
	private void help(String specifier, UserDataModel model) {
		if(specifier.equals("none")) {
			
			List<String> commandList = db.CommandQuery();
			List<String> uniqueCommandList = new ArrayList<String>();
			
			for(int i = 0; i < commandList.size() ; i++) {
				String command = commandList.get(i);
				if(!uniqueCommandList.contains(command)) {
					uniqueCommandList.add(command);
				}
			}
			
			String helpString = "Available Commands and their Specifiers";
			
			for(int i = 0; i < uniqueCommandList.size() ; i++) {
				String command = uniqueCommandList.get(i);
				List<String> commandSpecifiers = db.SpecifierByCommandQuery(command);
				helpString += "<br>--> "+command+" "+commandSpecifiers.toString();
			}
			
			model.addHistory(helpString);
		}
	}
	
	//the move command method
	private void move(String specifier, UserDataModel model) {
		int x = model.getWorld().getPlayerCoords().getX();
		int y = model.getWorld().getPlayerCoords().getY();
		World world = model.getWorld();
		
		//determine the direction
		if(specifier.equals("north")) {
			//execute code for moving north
			try {
				world.setPlayerCoords(x, y+1);
				Room newRoom = model.getWorld().getPlayerLocation();
				reply(newRoom.getTag(), model);
			}catch (Exception e) {
				reply("worldEdge", model);
			}
		}
		else if(specifier.equals("south")) {
			//execute code for moving south
			try {
				world.setPlayerCoords(x, y-1);
				Room newRoom = model.getWorld().getPlayerLocation();
				reply(newRoom.getTag(), model);
			}catch (Exception e) {
				reply("worldEdge", model);
			}
		}
		else if(specifier.equals("east")) {
			//execute code for moving east
			try {
				world.setPlayerCoords(x+1, y);
				Room newRoom = model.getWorld().getPlayerLocation();
				reply(newRoom.getTag(), model);
			}catch (Exception e) {
				reply("worldEdge", model);
			}
		}
		else if(specifier.equals("west")) {
			//execute code for moving west
			try {
				world.setPlayerCoords(x-1, y);
				Room newRoom = model.getWorld().getPlayerLocation();
				reply(newRoom.getTag(), model);
			}catch (Exception e) {
				reply("worldEdge", model);
			}
		}
		else {
			model.addHistory("That's not a direction.");
		}
	}
	
	//the description command method
	private void look(String specifier, UserDataModel model) {
		reply("look " + specifier, model);
	}
	
	//the map command method
	private void map(String specifier, UserDataModel model) {
		if(specifier.equals("none")) {
			
			model.getWorld().togglemap();
			
		}
	}
	
	//the take command method
	private void take(String specifier, UserDataModel model) {
		
		ArrayList<Item> roomInv = model.getWorld().getPlayerLocation().getInv();
		
		for(Item item : roomInv) {
			if(item.getName().equals(specifier)) {
				model.getWorld().getPlayer().
			}
		}
		
	}

	private void drop(String specifier, UserDataModel model) {
		// TODO Auto-generated method stub
		
	}

	private void use(String specifier, UserDataModel model) {
		// TODO Auto-generated method stub
		
	}

	private void attack(String specifier, UserDataModel model) {
		// TODO Auto-generated method stub
		
	}

	private void flee(String specifier, UserDataModel model) {
		// TODO Auto-generated method stub
		
	}
}

