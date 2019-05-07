package world;

import java.io.Serializable;

public class Entity implements Combat, Serializable {
	private static final long serialVersionUID = 6L;
	
	private int health;
	private int damage;
	private int armor;
	
	public Entity(int health, int damage, int armor) {
		this.health = health;
		this.damage = damage;
		this.armor = armor;
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
	public void damage(Integer damage) {
		this.health -= damage;
	}
	
	public void heal(Integer heal) {
		this.health += heal;
	}
}
