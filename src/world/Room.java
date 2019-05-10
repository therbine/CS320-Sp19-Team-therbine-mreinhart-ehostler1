package world;

import java.util.ArrayList;
import java.io.Serializable;

public class Room implements Serializable {
	private static final long serialVersionUID = 3L;
	
	private Terrain terrain;
	private ArrayList<Item> roomInv;
	private ArrayList<Entity> roomEnt;
	private String descriptionTag; // for getting description for the room from the database
	private boolean roomEntered;
	
	public Room(Terrain terrain, ArrayList<Item> roomInv, String descriptionTag) {
		this.terrain = terrain;
		this.roomInv = roomInv;
		this.roomEnt = new ArrayList<Entity>();
		this.descriptionTag = descriptionTag;
		this.roomEntered = false;
	}
	
	public boolean roomEntered() {
		return this.roomEntered;
	}
	
	public void setRoomEntered(boolean roomEntered) {
		this.roomEntered = roomEntered;
	}
	
	public ArrayList<Item> getInv() {
		return roomInv;
	}
	
	public ArrayList<Entity> getEnt(){
		return roomEnt;
	}
	
	public Terrain getTerrain() {
		return terrain;
	}
	
	public String getTag() {
		return descriptionTag;
	}
	
	public void removeItem(Item oldItem) {
		roomInv.remove(oldItem);
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
