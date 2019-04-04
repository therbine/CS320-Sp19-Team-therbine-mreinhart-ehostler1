package world;

import java.lang.Math;

public class Entity implements Combat {
	private Room location;
	private Room prevRoom;
	private int health;
	private int damage;
	private int armor;
	
	public Entity(Room location, int health, int damage, int armor) {
		this.location = location;
		this.prevRoom = null;
		this.health = health;
		this.damage = damage;
		this.armor = armor;
	}
	
	public Room getLocation() {
		return location;
	}
	
	public int getHealth() {
		return health;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public int getArmor() {
		return armor;
	}
	
	public void setLocation(Room room) {
		this.prevRoom = this.location;
		this.location = room;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	public void setArmor(int armor) {
		this.armor = armor;
	}
	
	// Combat methods
	public void attack(Entity other) {
		int remaining = other.getHealth() - (int)Math.ceil((double)this.damage / ((double)other.getArmor() + 1.0));
		other.setHealth(remaining);
	}
	
	public void flee() {
		if(this.prevRoom != null) {
			setLocation(prevRoom);
		}
	}
	
	public boolean isDone(Entity other) {
		if(this.health <= 0 || other.getHealth() <= 0) {
			return true;
		}else {
			return false;
		}
	}
}
