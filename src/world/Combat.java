package world;

public interface Combat {
	public void attack(Entity other);
	public boolean isDone(Entity other);
}
