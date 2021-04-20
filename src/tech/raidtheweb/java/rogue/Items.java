package tech.raidtheweb.java.rogue;

import java.util.HashMap;
import java.util.Map;

import asciiPanel.AsciiPanel;

public class Items {
	
	private static final Map<String, Item> items;
	
	static {
		items = new HashMap<>();
		
		items.put("rock", new Item(',', AsciiPanel.yellow, "rock"));
		
		Item dagger = new Item(')', AsciiPanel.white, "dagger");
    	dagger.modifyAttackValue(5);
		items.put("dagger", dagger);
		
		Item sword = new Item(')', AsciiPanel.brightWhite, "sword");
    	sword.modifyAttackValue(10);
		items.put("sword", sword);
		
		Item bow = new Item(')', AsciiPanel.yellow, "bow");
    	bow.modifyAttackValue(1);
    	bow.modifyRangedAttackValue(5);
		items.put("bow", bow);
		
		Item tunic = new Item('[', AsciiPanel.green, "tunic");
    	tunic.modifyDefenseValue(2);
		items.put("tunic", tunic);
		
		Item chainmail = new Item('[', AsciiPanel.white, "chainmail");
    	chainmail.modifyDefenseValue(4);
		items.put("chainmail", chainmail);
	}
	
	public static Item getItem(String name) {
		return items.get(name);
	}
	
	public static boolean exists(String name) {
		return items.containsKey(name);
	}

}
