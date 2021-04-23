package tech.raidtheweb.java.rogue;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import asciiPanel.AsciiPanel;

public class Items implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2698419064623553401L;
	private final Map<String, Item> items;
	
	public Items() {
		this.items = new HashMap<>();
		
		items.put("rock", new Item((char)174, AsciiPanel.yellow, "rock"));
		
		Item dagger = new Item((char)173, AsciiPanel.brightWhite, "sword");
    	dagger.modifyAttackValue(5);
		items.put("dagger", dagger);
		
		Item sword = new Item((char)173, AsciiPanel.brightWhite, "sword");
    	sword.modifyAttackValue(10);
		items.put("sword", sword);
		
		Item bow = new Item((char)175, AsciiPanel.yellow, "bow");
    	bow.modifyAttackValue(1);
    	bow.modifyRangedAttackValue(5);
		items.put("bow", bow);
		
		Item staff = new Item((char)172, AsciiPanel.yellow, "staff");
	    staff.modifyAttackValue(5);
	    staff.modifyDefenseValue(3);
	    items.put("staff", staff);
		
		Item tunic = new Item((char)171, AsciiPanel.green, "tunic");
    	tunic.modifyDefenseValue(2);
		items.put("tunic", tunic);
		
		Item chainmail = new Item((char)171, AsciiPanel.white, "chainmail");
    	chainmail.modifyDefenseValue(4);
		items.put("chainmail", chainmail);
	}
	
	public Item getItem(String name) {
		return items.get(name);
	}
	
	public boolean exists(String name) {
		return items.containsKey(name);
	}

}
