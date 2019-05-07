package world;

import java.util.ArrayList;
import java.io.Serializable;

public class World implements Serializable {
	private static final long serialVersionUID = 2L;
	
	private Room[][] roomArr;
	private Character character;
	private Boolean map;
	
	public World() {
		// Initialize rooms
		roomArr = new Room[3][3];
		populate();
		
		// Create character
		character = new Character(roomArr[1][0]);
		
		map = false;
	}
	
	public void populate() {
		// Create rooms
		roomArr[0][0] = new Room(Terrain.jungle, new ArrayList<Item>(), "room00");
		roomArr[0][1] = new Room(Terrain.beach, new ArrayList<Item>(), "room01");
		roomArr[0][2] = new Room(Terrain.forest, new ArrayList<Item>(), "room02");
		
		roomArr[1][0] = new Room(Terrain.cave, new ArrayList<Item>(), "room10");
		roomArr[1][1] = new Room(Terrain.treasure, new ArrayList<Item>(), "room11");
		roomArr[1][2] = new Room(Terrain.getRandomTerrain(), new ArrayList<Item>(), "room12");
		
		roomArr[2][0] = new Room(Terrain.getRandomTerrain(), new ArrayList<Item>(), "room20");
		roomArr[2][1] = new Room(Terrain.getRandomTerrain(), new ArrayList<Item>(), "room21");
		roomArr[2][2] = new Room(Terrain.getRandomTerrain(), new ArrayList<Item>(), "room22");
		
		// Add items to room inventories
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(i % 2 == j % 2) {
					roomArr[i][j].addItem(new Item(ItemType.potion, 0, 10, 0));
				}
				else if(i % 2 == 0 && j % 2 == 1) {
					roomArr[i][j].addItem(new Item(ItemType.weapon, 5, 0, 0));
				}
				else {
					roomArr[i][j].addItem(new Item(ItemType.armor, 0, 0, 5));
				}
			}
		}
	}
	
	public Room getPlayerLocation() {
		return character.getLocation();
	}
	
	public Pair getPlayerCoords() {
		int x = 0;
		int y = 0;
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(roomArr[i][j] == character.getLocation()) {
					x = i;
					y = j;
					break;
				}
			}
		}
		return new Pair(x, y);
	}
	
	public void setPlayerCoords(int x, int y) {
		Room room = roomArr[x][y];
		character.setLocation(room);
	}
	public void togglemap() {
		if(map) {
			map = false;
		}else {
			map = true;
		}
	}
	
	public Boolean getMap() {
		return map;
	}
}
