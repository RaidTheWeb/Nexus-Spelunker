package tech.raidtheweb.java.rogue;

public class RegenEffect extends Effect {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3704585001647340254L;

	public RegenEffect(int duration) {
		super(duration);
	}

	public void start(Creature creature){
        creature.doAction("look healthier");
    }
                    
    public void update(Creature creature){
        super.update(creature);
        creature.modifyHp(1);
    }
}
