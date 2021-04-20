package tech.raidtheweb.java.rogue.brains.creatures;

import tech.raidtheweb.java.rogue.Creature;

public class BatAi extends CreatureAi {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6157370493529283401L;

	public BatAi(Creature creature) {
        super(creature);
    }

    public void onUpdate(){
        wander();
        wander();
    }
}
