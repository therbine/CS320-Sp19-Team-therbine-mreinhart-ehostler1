package worldTests;

import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

import world.*;
import world.Character;

public class EntityTest {
	private Entity entity;
	
	@Before
	public void setUp() {
		Room room = new Room(Terrain.cave, new ArrayList<Item>());
		entity = new Entity(room, 3, 4, 5);
	}
	
	@Test
	public void testGetLocation() {
		Terrain terrain = entity.getLocation().getTerrain();
		int size = entity.getLocation().getInv().size();
		
		assertEquals(terrain, Terrain.cave);
		assertEquals(size, 0);
	}
	
	@Test
	public void testGetHealth() {
		assertEquals(entity.getHealth(), 3);
	}
	
	@Test
	public void testGetDamage() {
		assertEquals(entity.getDamage(), 4);
	}
	
	@Test
	public void testGetArmor() {
		assertEquals(entity.getArmor(), 5);
	}
	
	@Test
	public void testSetLocation() {
		Room room = new Room(Terrain.jungle, new ArrayList<Item>());
		entity.setLocation(room);
		
		Terrain terrain = entity.getLocation().getTerrain();
		int size = entity.getLocation().getInv().size();
		
		assertEquals(terrain, Terrain.jungle);
		assertEquals(size, 0);
	}
	
	@Test
	public void testSetHealth() {
		entity.setHealth(1);
		assertEquals(entity.getHealth(), 1);
	}
	
	@Test
	public void testSetDamage() {
		entity.setDamage(6);
		assertEquals(entity.getDamage(), 6);
	}
	
	@Test
	public void testSetArmor() {
		entity.setArmor(0);
		assertEquals(entity.getArmor(), 0);
	}
	
	@Test
	public void testAttack() {
		Room room = new Room(Terrain.hostile, new ArrayList<Item>());
		Character character = new Character(room);
		Entity enemy = new Entity(room, 50, 10, 0);
		character.attack(enemy);
		
		assertEquals(40, enemy.getHealth());
	}
	
	@Test
	public void testFleeSuccess() {
		Room startingRoom = new Room(Terrain.beach, new ArrayList<Item>());
		Room currentRoom = new Room(Terrain.hostile, new ArrayList<Item>());
		
		Character character = new Character(startingRoom);
		character.setLocation(currentRoom);
		character.flee();
		
		Room location = character.getLocation();
		assertEquals(location, startingRoom);
	}
	
	@Test
	public void testFleeFail() {
		Room room = new Room(Terrain.hostile, new ArrayList<Item>());
		
		Character character = new Character(room);
		character.flee();
		
		Room location = character.getLocation();
		assertEquals(location, room);
	}
	
	@Test
	public void testIsDoneContinue() {
		Room room = new Room(Terrain.hostile, new ArrayList<Item>());
		Entity attacker = new Entity(room, 15, 10, 0);
		Entity enemy = new Entity(room, 15, 0, 0);
		attacker.attack(enemy);
		
		assertEquals(attacker.isDone(enemy), false);
	}
	
	@Test
	public void testIsDoneFinished() {
		Room room = new Room(Terrain.hostile, new ArrayList<Item>());
		Entity attacker = new Entity(room, 15, 10, 0);
		Entity enemy = new Entity(room, 10, 0, 0);
		attacker.attack(enemy);
		
		assertEquals(attacker.isDone(enemy), true);
	}
}
