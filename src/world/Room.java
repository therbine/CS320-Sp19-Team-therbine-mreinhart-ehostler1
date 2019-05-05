package world;

import java.util.ArrayList;
import java.io.Serializable;

public class Room implements Serializable {
	private static final long serialVersionUID = 3L;
	
	private Terrain terrain;
	private ArrayList<Item> roomInv;
	private ArrayList<Entity> roomEnt;
	public String descriptionTag; // for getting description for the room from the database
	
	public Room(Terrain terrain, ArrayList<Item> roomInv, String descriptionTag) {
		this.terrain = terrain;
		this.roomInv = roomInv;
		this.roomEnt = new ArrayList<Entity>();
		this.descriptionTag = descriptionTag;
	}
	
	public ArrayList<Item> getInv() {
		return roomInv;
	}
	
	public Terrain getTerrain() {
		return terrain;
	}
	
	public String getTag() {
		return descriptionTag;
	}
	
	public Item takeItem(int index) {
		Item taken = roomInv.get(index);
		roomInv.remove(index);
		return taken;
	}
	
	public void addItem(Item newItem) {
		roomInv.add(newItem);
	}
	
	public void newEntity(Entity entity) {
		roomEnt.add(entity);
	}
	
	public void killEntity(int entityIndex) {
		roomEnt.remove(entityIndex);
	}
	
	public Entity getEntity(int entityIndex) {
		return roomEnt.get(entityIndex);
	}
}
