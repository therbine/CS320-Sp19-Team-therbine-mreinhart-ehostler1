package world;

import java.util.ArrayList;

public class Character extends Entity {
	private int score;
	private ArrayList<Item> inventory;
	
	public Character(Room room) {
		super(room, 100, 10, 0);
		score = 0;
		inventory = new ArrayList<Item>();
	}
	
	public int getScore() {
		return score;
	}
	
	public ArrayList<Item> getInventory(){
		return inventory;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public void dropItem(int index) {
		if(index <= inventory.size() - 1) {
			super.getLocation().addItem(inventory.get(index));
			inventory.remove(index);
		}
	}
	
	public void pickUpItem(Item item) {
		if(!isFull()) {
			inventory.add(item);
		}
	}
	
	public boolean isFull() {
		if(inventory.size() >= 5) {
			return true;
		}else {
			return false;
		}
	}
}
