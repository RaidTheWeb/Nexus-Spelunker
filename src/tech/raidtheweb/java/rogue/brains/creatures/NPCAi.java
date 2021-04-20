package tech.raidtheweb.java.rogue.brains.creatures;

import java.util.List;

import tech.raidtheweb.java.rogue.Creature;
import tech.raidtheweb.java.rogue.screens.NPCBuySellScreen;
import tech.raidtheweb.java.rogue.screens.Screen;

public class NPCAi extends CreatureAi {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7966007945685943022L;
	private List<NPCOffer> offers;

	public NPCAi(Creature creature, List<NPCOffer> offers) {
		super(creature);
		this.offers = offers;
	}
	
	public List<NPCOffer> getOffers() { return offers; }
	
	public void onUpdate() {}
	
	public Screen onInteract(Creature player) {
		return new NPCBuySellScreen(player, this.offers);
	}

}
