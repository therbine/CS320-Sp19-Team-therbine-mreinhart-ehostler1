package worldTests;

import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

import world.Character;
import world.*;

public class CharacterTest {
	private Character character;
	
	@Before
	public void setUp() {
		Room room = new Room(Terrain.beach, new ArrayList<Item>(), "");
		character = new Character(room);
	}
	
	@Test
	public void testPickUpItem() {
		Item item = new Item("", ItemType.potion, 0, 4, 0);
		character.pickUpItem(item);
		
		assertTrue(character.getInventory().contains(item));
	}
	
	@Test
	public void testPickUpItemWhenFull() {
		Item item = new Item("", ItemType.potion, 0, 4, 0);
		for(int i = 0; i < 5; i++) {
			character.addItem(item);
		}
		
		Item full = new Item("", ItemType.weapon, 2, 0, 0);
		character.addItem(full);
		
		assertFalse(character.getInventory().contains(full));
	}
	
	@Test
	public void testDropItem() {
		Item item = new Item("", ItemType.potion, 0, 4, 0);
		for(int i = 0; i < 5; i++) {
			character.addItem(item);
		}
		character.removeItem(oldItem);
		
		assertEquals(character.getInventory().size(), 4);
		assertTrue(character.getLocation().getInv().contains(item));
	}
	
	@Test
	public void testDropItemEmptyInv() {
		Item item = new Item("", ItemType.potion, 0, 4, 0);
		character.removeItem(oldItem);
		assertFalse(character.getLocation().getInv().contains(item));
	}
}
