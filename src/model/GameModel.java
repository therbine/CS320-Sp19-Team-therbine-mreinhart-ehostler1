package model;

import java.util.HashMap;
import java.util.Map;

public class GameModel {
	private static Map<String, String> descriptions = new HashMap<String, String>();
	private String Intro;
	public GameModel() {
		descriptions.put("Intro", "You wake up on a beach with nothing but a rusty sword and the clothes on your back."
				+" To the north there seams to be a dense jungle while to your east and west there’s"
				+" a bunch of washed up wreckage.");
	}
	
	public String getDescription(String name) {
		return descriptions.get(name);
	}
}
