package system;

import java.util.HashMap;
import java.util.Map;

public class SystemOutput {
	private static Map<String, String> descriptions;
	private static Map<String, String> actions;
	
	public SystemOutput() {
		descriptions = new HashMap<String, String>();
		actions = new HashMap<String, String>();
		
		//DESCRIPTIONS
		descriptions.put("sword", "A sharp thingy.");
		descriptions.put("south", "I always like going South, somehow it feels like going downhill.");
		descriptions.put("east", "Oh... east? I thought you said weast!");
		
		//ACTIONS
		actions.put("move north", "You move north.");
		actions.put("move south", "You move south.");
		actions.put("move east", "You move east.");
		actions.put("move west", "You move west.");
	}
	
	public String getDescription(String descriptionName) {
		return descriptions.get(descriptionName);
	}
	
	public String getAction(String commandAndSpecifier) {
		return actions.get(commandAndSpecifier);
	}
	
	
}
