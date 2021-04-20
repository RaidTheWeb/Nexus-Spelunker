package tech.raidtheweb.java.rogue.screens;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import asciiPanel.AsciiPanel;
import tech.raidtheweb.java.rogue.Creature;
import tech.raidtheweb.java.rogue.Inventory;
import tech.raidtheweb.java.rogue.Item;
import tech.raidtheweb.java.rogue.Main;
import tech.raidtheweb.java.rogue.brains.creatures.NPCOffer;

public class NPCBuySellScreen implements Screen {

	private Creature player;
	private List<NPCOffer> offers;
	private String letters = "abcdefghijklmnopqrstuvwxyz";
	private Map<String, NPCOffer> letterToOffer;

	public NPCBuySellScreen(Creature player, List<NPCOffer> offers) {
		this.player = player;
		this.offers = offers;
		this.letterToOffer = new HashMap<>();
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		Main.log("[NPCHandler]: Displaying offers for NPC[offers=" + offers.toString() + "]");
		int y = 23 - offers.size();
		int x = 4;

		if (offers.size() > 0)
			terminal.clear(' ', x, y, 20, offers.size());
		
		for (NPCOffer offer : offers) {
			String letter = getLetter();
			letterToOffer.put(letter, offer);
			terminal.write(letter + " - buy: " + offer.getSelling().name() + "   for: " + offer.getBuying().name(), x, y++);
		}
		
		terminal.clear(' ', 0, 23, 80, 1);
		terminal.write("Hello! What would you like to buy?", 2, 23);
		
		terminal.repaint();
		
	}
	
	public String getLetter() {
		String letter = letters.substring(0, 1);
		letters = letters.substring(1);
		return letter;
	}
	
	public boolean hasItem(Inventory inventory, Item item) {
		for (int i = 0; i < inventory.getItems().length; i++) {
			Item element = inventory.get(i);
		    if ((element != null) && (element.name() == item.name())) {
		        return true;
		    }
		}
		
		return false;

	}
	
	public int getItemIndex(Inventory inventory, Item item) {
		for (int i = 0; i < inventory.getItems().length; i++) {
			Item element = inventory.get(i);
		    if ((element != null) && (element.name() == item.name())) {
		        return i;
		    }
		}
		
		return -1;

	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		Character c = key.getKeyChar();
		
		if("abcdefghijklmnopqrstuvwxyz".indexOf(c) != -1) {
			if(this.letterToOffer.containsKey(c.toString())) {
				
				Item selling = letterToOffer.get(c.toString()).getSelling();
				Item buying = letterToOffer.get(c.toString()).getBuying();
				
				if(hasItem(player.inventory(), buying)) {
					player.inventory().remove(player.inventory().get(getItemIndex(player.inventory(), buying)));
					player.inventory().add(selling);
					player.notify("Thank you for your purchase!");
					Main.log("[NPCHandler]: Purchased Item[name=\"" + selling.name() + "\"] From NPC[offers=" + offers.toString() + "]");
					return null;
				} else {
					player.notify("You do not have the required item!");
					return null;
				}
				
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

}
