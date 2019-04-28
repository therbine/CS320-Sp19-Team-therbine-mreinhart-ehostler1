package world;

import java.io.Serializable;

public class Item implements Serializable {
	private static final long serialVersionUID = 2L;
	
	private ItemType type;
	private int dmg;
	private int healingVal;
	private int armorVal;
	
	public Item(ItemType type, int dmg, int healingVal, int armorVal) {
		this.type = type;
		this.dmg = dmg;
		this.healingVal = healingVal;
		this.armorVal = armorVal;
	}
	
	public ItemType getType() {
		return type;
	}
	
	// returns value based on what type of item it is
	public int getVal() {
		if(dmg != 0) {
			return dmg;
		}else if(healingVal != 0) {
			return healingVal;
		}else {
			return armorVal;
		}
	}
	
}
