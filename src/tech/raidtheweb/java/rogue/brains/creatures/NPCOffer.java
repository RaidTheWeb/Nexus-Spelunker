package tech.raidtheweb.java.rogue.brains.creatures;

import java.io.Serializable;

import tech.raidtheweb.java.rogue.Item;

public class NPCOffer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6816966043301680029L;
	private Item selling;
	private Item buying;

	public NPCOffer(Item selling, Item buying) {
		this.selling = selling;
		this.buying = buying;
	}
	
	public Item getSelling() { return selling; }
	
	public Item getBuying() { return buying; }
	
	public String toString() {
		return "NPCOffer[selling=\"" + this.selling.name() + "\", buying=\"" + this.buying.name() + "\"]";
	}
}
