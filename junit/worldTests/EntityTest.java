package worldTests;

import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

import world.*;

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
}
