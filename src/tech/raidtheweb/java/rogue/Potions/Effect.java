package tech.raidtheweb.java.rogue.Potions;

import tech.raidtheweb.java.rogue.Creature;

public interface Effect {
	
	boolean onTick(Creature player);
}
