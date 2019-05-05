package world;

import java.util.Random;

public enum Terrain {
	cave,
	beach,
	jungle,
	forest,
	field,
	desert,
	swamp,
	graveyard,
	treasure;
	
	public static Terrain getRandomTerrain() {
		Random rand = new Random();
		return values()[rand.nextInt(values().length)];
	}
}
