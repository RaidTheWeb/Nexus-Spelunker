package tech.raidtheweb.java.rogue.screens;

import tech.raidtheweb.java.rogue.Creature;
import tech.raidtheweb.java.rogue.Item;

public class EquipScreen extends InventoryBasedScreen {

	  public EquipScreen(Creature player) {
	    super(player);
	  }

	  protected String getVerb() {
	    return "wear or wield";
	  }

	  protected boolean isAcceptable(Item item) {
	    return item.attackValue() > 0 || item.defenseValue() > 0;
	  }

	  protected Screen use(Item item) {
	    player.equip(item);
	    return null;
	  }
}