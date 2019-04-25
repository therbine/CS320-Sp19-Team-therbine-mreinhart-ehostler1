package world;

import java.util.ArrayList;

public class World {
	private Room[][] roomArr;
	private Character character;
	private Entity enemy1;
	private Entity enemy2;
	private Entity enemy3;
	private Object map;
	
	public World() {
		// Initialize rooms
		roomArr = new Room[3][3];
		populate();
		
		// Create character
		character = new Character(roomArr[1][0]);
		
		// Spawn enemies
		enemy1 = new Entity(roomArr[2][2], 50, 10, 0);
		enemy2 = new Entity(roomArr[1][0], 40, 15, 0);
		enemy3 = new Entity(roomArr[0][2], 30, 20, 0);
		
		map = null;
	}
	
	public void populate() {
		// Create rooms
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				roomArr[i][j] = new Room(Terrain.getRandomTerrain(), new ArrayList<Item>());
			}
		}
		
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
		if(map == null) {
			map = 1;
		}else {
			map = null;
		}
	}
	
	public Object getMap() {
		return map;
	}
}
