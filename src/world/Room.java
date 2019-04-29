package world;

import java.util.ArrayList;
import java.io.Serializable;

public class Room implements Serializable {
	private static final long serialVersionUID = 3L;
	
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
