package tech.raidtheweb.java.rogue;

public class HealthEffect extends Effect {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2901980825039054224L;

	public HealthEffect(int duration) {
		super(duration);
	}

	public void start(Creature creature){
        if (creature.hp() == creature.maxHp())
        	return;
                                
        creature.modifyHp(15);
    	creature.doAction("look healthier");
    }
}
