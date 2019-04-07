package world;

public interface Combat {
	public void attack(Entity other);
	public void flee();
	public boolean isDone(Entity other);
}
