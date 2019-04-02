package world;

public class Entity {
	private Room location;
	private int health;
	private int damage;
	private int armor;
	
	public Entity(Room location, int health, int damage, int armor) {
		this.location = location;
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
		location = room;
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
}
