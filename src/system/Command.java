package system;

import java.util.ArrayList;
import java.util.List;
import java.math.*;

import database.persist.*;
import model.UserDataModel;
import world.Entity;
import world.Item;
import world.ItemType;
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
			model.addHistory(" > " + input);
				
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
		else if(command.equals("run")) {
			run(specifier, model);
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
		else if(command.equals("inventory")) {
			inventory(specifier, model);
		}
	}
		
	//adds a system reply to the history
	private void reply(String replyTag, UserDataModel model) {
		
		List<String> replyList = db.DescriptionByObjectQuery(replyTag);
		
		if(replyList.isEmpty()) {
			model.addHistory("No Reply Found for >"+replyTag+"<");
		}
		else {
			model.addHistory(replyList.get(0));
			
			ArrayList<Entity> roomEnt = model.getWorld().getPlayerLocation().getEnt();
			if(replyTag.contains("room") && !roomEnt.isEmpty()) {
				for(Entity ent : roomEnt) {
					model.addHistory(db.DescriptionByObjectQuery(ent.getName()).get(0));
				}
			}
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
		
		//don't allow the player to move if there is an enemy in the room
		if(!model.getWorld().getPlayerLocation().getEnt().isEmpty()) {
			reply("trapped",model);
			return;
		}
		
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
		
		model.getWorld().getPlayerLocation().setRoomEntered(true);
	}
	
	//the description command method
	private void look(String specifier, UserDataModel model) {
		
		ArrayList<Item> roomInv = model.getWorld().getPlayerLocation().getInv();
		ArrayList<Entity> roomEnt = model.getWorld().getPlayerLocation().getEnt();
		String lookString = "";
		
		if(!roomInv.isEmpty()) {
			lookString += "This area contains";
			for(int i = 0; i < roomInv.size() ; i++) {
				if(i == roomInv.size() - 1) {
					if(roomInv.size() == 1) {
						lookString += " a "+roomInv.get(i).getName()+".";
					}
					else {
						lookString += " and a "+roomInv.get(i).getName()+".";
					}
				}
				else {
					lookString += " a "+roomInv.get(i).getName()+",";
				}
			}
		}
		else {
			lookString += " There appears to be no items in this area.";
		}
		
		if(!roomEnt.isEmpty()) {
			lookString += " This area also contains";
			for(int i = 0; i< roomInv.size() ; i++) {
				if(i == roomInv.size() - 1) {
					if(roomInv.size() == 1) {
						lookString += " a "+roomEnt.get(i).getName()+".";
					}
					else {
						lookString += " and a "+roomEnt.get(i).getName()+".";
					}
				}
				else {
					lookString += " a "+roomEnt.get(i).getName()+",";
				}
			}
		}
		else {
			lookString += " There also appears to be no enemies.";
		}
		
		model.addHistory(lookString);
	}
	
	//the inventory command method
	private void inventory(String specifier, UserDataModel model) {
		if(specifier.equals("none")) {
			
			ArrayList<Item> playerInv = model.getWorld().getPlayer().getInventory();
			String inventoryString = "Inventory";
			
			if(playerInv.isEmpty()) {
				inventoryString += "<br>[empty]";
			}
			
			for(Item item : playerInv) {
				ItemType type = item.getType();
				inventoryString += "<br>| "+item.getName()+"(";
				if(type.equals(ItemType.armor)) {
					inventoryString += "+"+item.getArmorValue()+" armor";
				}
				else if(type.equals(ItemType.key)) {
					inventoryString += "unlock stuff";
				}
				else if(type.equals(ItemType.potion)) {
					inventoryString += item.getHealingValue()+" health";
				}
				else if(type.equals(ItemType.weapon)) {
					inventoryString += "+"+item.getDamage()+" damage";
				}
				inventoryString += ")";
			}
			
			model.addHistory(inventoryString);
		}
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
		
		if(model.getWorld().getPlayer().isFull()) {
			reply("inventoryFull", model);
			return;
		}
		
		for(Item item : roomInv) {
			if(item.getName().equals(specifier)) {
				model.getWorld().getPlayer().addItem(item);
				model.getWorld().getPlayerLocation().removeItem(item);
				reply("take " + specifier, model);
				break;
			}
		}
	}

	private void drop(String specifier, UserDataModel model) {
		
		ArrayList<Item> playerInv = model.getWorld().getPlayer().getInventory();
		
		for(Item item : playerInv) {
			if(item.getName().equals(specifier)) {
				model.getWorld().getPlayerLocation().addItem(item);
				model.getWorld().getPlayer().removeItem(item);
				reply("drop " + specifier, model);
				break;
			}
		}
		
	}

	private void use(String specifier, UserDataModel model) {

		if(specifier.equals("bandage") || specifier.equals("med kit")) {
			//use bandage or med kit
			ArrayList<Item> playerInv = model.getWorld().getPlayer().getInventory();
			
			for(Item item : playerInv) {
				if(item.getName().equals(specifier) && item.getType() == ItemType.potion) {
					model.getWorld().getPlayer().heal(item.getHealingValue());
					model.getWorld().getPlayer().removeItem(item);
					break;
				}
			}
		}
		else if(specifier.equals("keys")) {
			//use key
			//TODO
		}
		else {
			reply("notUseable", model);
		}
		
	}

	private void attack(String specifier, UserDataModel model) {
		ArrayList<Entity> targetList = model.getWorld().getPlayerLocation().getEnt();
		
		if(specifier.equals("none") && !targetList.isEmpty()) {
			
			//get the first enemy in the room
			Entity target = targetList.get(0);
			
			reply("attack",model);
			
			//calculate player attack
			int attack = model.getWorld().getPlayer().getDamage();
			System.out.println("Player base attack is:" + attack);
			ArrayList<Item> inventory = model.getWorld().getPlayer().getInventory();
			for(Item item : inventory) {
				if(item.getType() == ItemType.weapon) {
					attack += item.getDamage();
				}
			}
			System.out.println("Player total attack is:" + attack);
			
			//account for enemy armor value (probably zero)
			attack = (int)Math.ceil((double)attack / ((double)target.getArmor() + 1.0));
			System.out.println("Player total attack with enemy armor is:" + attack);
			
			//deal damage to target
			target.damage(attack);
			
			System.out.println("Target is dead?" + target.isDead());
			
			if(target.isDead()) {
				reply("kill", model);
				
				String keyName = target.getName() + " key";
				model.getWorld().getPlayerLocation().getInv().add(new Item(keyName,ItemType.key,0,0,0));
				
				model.getWorld().getPlayerLocation().killEntity(0);
			}
			else if(targetList.isEmpty()) {
				reply("noEnemy",model);
			}
		}
	}

	private void run(String specifier, UserDataModel model) {
		if(specifier.equals("none")) {
			model.getWorld().getPlayer().flee();
		}
	}
}

