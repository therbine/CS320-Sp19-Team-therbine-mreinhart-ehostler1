package worldTests;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import world.*;

public class ItemTest {
	private Item item1;
	private Item item2;
	private Item item3;
	
	@Before
	public void setUp() {
		item1 = new Item("", ItemType.armor, 0, 0, 3);
		item2 = new Item("", ItemType.potion, 0, 2, 0);
		item3 = new Item("", ItemType.weapon, 4, 0, 0);
	}
	
	@Test
	public void testGetVal() {
		int val1 = item1.getArmorValue();
		int val2 = item2.getHealingValue();
		int val3 = item3.getDamage();
		
		assertEquals(val1, 3);
		assertEquals(val2, 2);
		assertEquals(val3, 4);
	}
	
	@Test
	public void testGetType() {
		ItemType type1 = item1.getType();
		ItemType type2 = item2.getType();
		ItemType type3 = item3.getType();
		
		assertEquals(type1, ItemType.armor);
		assertEquals(type2, ItemType.potion);
		assertEquals(type3, ItemType.weapon);
	}
}
