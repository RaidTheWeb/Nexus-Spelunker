package tech.raidtheweb.java.rogue;

public class WarriorEffect extends Effect {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7307565050531084544L;
	public WarriorEffect(int duration) {
		super(duration);
	}
	public void start(Creature creature){
        creature.modifyAttackValue(5);
        creature.modifyDefenseValue(5);
        creature.doAction("look stronger");
    }
    public void end(Creature creature){
        creature.modifyAttackValue(-5);
        creature.modifyDefenseValue(-5);
        creature.doAction("look less strong");
    }
}
