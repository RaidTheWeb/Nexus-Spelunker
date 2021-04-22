package tech.raidtheweb.java.rogue.Potions;

import tech.raidtheweb.java.rogue.Creature;

public class HealthEffect implements Effect {

	private int duration;
	private int tickedDuration;
	private int strength;
	
	public HealthEffect(int duration, int strength) {
		this.duration = duration;
		this.strength = strength;
	}

	@Override
	public boolean onTick(Creature player) {
		this.tickedDuration++;
		
		if(this.tickedDuration > this.duration) {
			return true;
		} else {
			player.modifyHp(this.strength);
			return false;
		}
	}

}
