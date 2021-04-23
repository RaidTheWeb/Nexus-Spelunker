package tech.raidtheweb.java.rogue.Scripting;

import tech.raidtheweb.java.dsl.lib.Value;
import tech.raidtheweb.java.rogue.Items;
import tech.raidtheweb.java.rogue.brains.creatures.NPCOffer;

public class NPCValue implements Value {
	
	private NPCOffer offer;
	private String selling;
	private String buying;
	private Items Items;

	public NPCValue(Value buying, Value selling) {
		String buy = buying.asString();
		String sell = selling.asString();
		this.Items = new Items();
		
		if(Items.exists(buy) && Items.exists(sell)) {
			this.selling = sell;
			this.buying = buy;
			
			this.offer = new NPCOffer(Items.getItem(sell), Items.getItem(buy));
		} else {
			this.offer = null;
			
		}
	}
	
	public NPCOffer getOffer() {
		return this.offer;
	}
	
	@Override
	public double asDouble() {
		return 0;
	}

	@Override
	public String asString() {
		return "NexusSpelunkerNPCOffer[selling=" + this.selling + ", buying=" + this.buying + "]";
	}

	@Override
	public int asInt() {
		return 0;
	}

}
