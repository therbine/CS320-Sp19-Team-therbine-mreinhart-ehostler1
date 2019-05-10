package worldTests;

import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import world.*;
import world.Character;

public class EntityTest {
	private Entity entity;
	private Character character;
	
	@Before
	public void setUp() {
		Room room = new Room(Terrain.cave, new ArrayList<Item>(), "");
		entity = new Entity(3, 4, 5, "");
		room.newEntity(entity);
		
		character = new Character(room);
	}
	
	@Test
	public void testGetLocation() {
		Terrain terrain = character.getLocation().getTerrain();
		int size = character.getLocation().getInv().size();
		
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
		Room room = new Room(Terrain.jungle, new ArrayList<Item>(), "");
		character.setLocation(room);
		
		Terrain terrain = character.getLocation().getTerrain();
		int size = character.getLocation().getInv().size();
		
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
		Room room = new Room(Terrain.cave, new ArrayList<Item>(), "");
		Character character = new Character(room);
		Entity enemy = new Entity(50, 10, 0, "");
		enemy.damage(character.getDamage());
		
		assertEquals(40, enemy.getHealth());
	}
	
	@Test
	public void testFleeSuccess() {
		Room startingRoom = new Room(Terrain.beach, new ArrayList<Item>(), "");
		Room currentRoom = new Room(Terrain.cave, new ArrayList<Item>(), "");
		
		Character character = new Character(startingRoom);
		character.setLocation(currentRoom);
		character.flee();
		
		Room location = character.getLocation();
		assertEquals(location, startingRoom);
	}
	
	@Test
	public void testFleeFail() {
		Room room = new Room(Terrain.cave, new ArrayList<Item>(), "");
		
		Character character = new Character(room);
		character.flee();
		
		Room location = character.getLocation();
		assertEquals(location, room);
	}
	
	@Test
	public void testIsDeadFalse() {
		//Room room = new Room(Terrain.cave, new ArrayList<Item>(), "");
		Entity attacker = new Entity(15, 10, 0, "");
		Entity enemy = new Entity(15, 0, 0, "");
		enemy.damage(attacker.getDamage());
		
		assertFalse(enemy.isDead());
	}
	
	@Test
	public void testIsDeadTrue() {
		//Room room = new Room(Terrain.cave, new ArrayList<Item>(), "");
		Entity attacker = new Entity(15, 10, 0, "");
		Entity enemy = new Entity(10, 0, 0, "");
		enemy.damage(attacker.getDamage());
		
		assertTrue(enemy.isDead());
	}
}
