package world;

import java.io.Serializable;

public class Item implements Serializable {
	private static final long serialVersionUID = 5L;
	
	private String itemName;
	private ItemType type;
	private int dmg;
	private int healingVal;
	private int armorVal;
	
	public Item(String itemName, ItemType type, int dmg, int healingVal, int armorVal) {
		this.itemName = itemName;
		this.type = type;
		this.dmg = dmg;
		this.healingVal = healingVal;
		this.armorVal = armorVal;
	}
	
	public String getName() {
		return this.itemName;
	}
	
	public ItemType getType() {
		return this.type;
	}
	
	public int getDamage() {
		return this.dmg;
	}
	
	public int getHealingValue() {
		return this.healingVal;
	}
	
	public int getArmorValue() {
		return this.armorVal;
	}
}
