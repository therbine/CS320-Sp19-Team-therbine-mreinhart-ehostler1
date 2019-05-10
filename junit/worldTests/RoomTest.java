package worldTests;

import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

import world.*;

public class RoomTest {
	private Room room;
	
	@Before
	public void setUp() {
		Terrain terrain = Terrain.beach;
		ArrayList<Item> roomInv = new ArrayList<Item>();
		
		roomInv.add(new Item("", ItemType.armor, 0, 0, 5));
		roomInv.add(new Item("", ItemType.potion, 0, 6, 0));
		roomInv.add(new Item("", ItemType.weapon, 3, 0, 0));
		
		room = new Room(terrain, roomInv, "");
	}
	
	@Test
	public void testGetInv() {
		Item item1 = room.getInv().get(0);
		Item item2 = room.getInv().get(1);
		Item item3 = room.getInv().get(2);
		
		assertEquals(item1.getArmorValue(), 5);
		assertEquals(item2.getHealingValue(), 6);
		assertEquals(item3.getDamage(), 3);
	}
	
	@Test
	public void testGetTerrain() {
		Terrain terrain = room.getTerrain();
		assertEquals(terrain, Terrain.beach);
	}
	
	@Test
	public void testTakeItem() {
		int index = 1;
		Item toTake = room.getInv().get(index);
		int sizeBefore = room.getInv().size();
		
		room.removeItem(toTake);
		int sizeAfter = room.getInv().size();
		
		assertEquals(sizeBefore - 1, sizeAfter);
	}
	
	@Test
	public void testAddItem() {
		Item toAdd = new Item("", ItemType.potion, 0 , 1, 0);
		room.addItem(toAdd);
		
		assertEquals(toAdd, room.getInv().get(room.getInv().size() - 1));
	}
}
