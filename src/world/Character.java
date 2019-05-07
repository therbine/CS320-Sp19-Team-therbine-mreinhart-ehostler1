package world;

import java.util.ArrayList;

public class Character extends Entity {
	private static final long serialVersionUID = 7L;
	
	private int score;
	private ArrayList<Item> inventory;
	private Room location;
	private Room prevRoom;
	
	public Character(Room room) {
		super(100, 10, 0, "player");
		score = 0;
		inventory = new ArrayList<Item>();
		this.location = room;
		this.prevRoom = null;
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
	
	public void removeItem(Item oldItem) {
		inventory.remove(oldItem);
	}
	
	public void addItem(Item item) {
		inventory.add(item);
	}
	
	public boolean isFull() {
		if(inventory.size() >= 5) {
			return true;
		}else {
			return false;
		}
	}
	
	public Room getLocation() {
		return location;
	}
	
	public void setLocation(Room room) {
		this.prevRoom = this.location;
		this.location = room;
	}
	
	public void flee() {
		if(this.prevRoom != null) {
			setLocation(prevRoom);
		}
	}
}
