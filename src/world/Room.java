package world;

import java.util.ArrayList;

public class Room {
	private Terrain terrain;
	private ArrayList<Item> roomInv;
	
	public Room(Terrain terrain, ArrayList<Item> roomInv) {
		this.terrain = terrain;
		this.roomInv = roomInv;
	}
	
	public ArrayList<Item> getInv() {
		return roomInv;
	}
	
	public Terrain getTerrain() {
		return terrain;
	}
	
	public Item takeItem(int index) {
		Item taken = roomInv.get(index);
		roomInv.remove(index);
		return taken;
	}
	
	public void addItem(Item newItem) {
		roomInv.add(newItem);
	}
}
