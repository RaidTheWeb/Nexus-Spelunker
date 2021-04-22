package tech.raidtheweb.java.rogue.screens;

import tech.raidtheweb.java.rogue.Creature;
import tech.raidtheweb.java.rogue.Item;

public class QuaffScreen extends InventoryBasedScreen {

    public QuaffScreen(Creature player) {
            super(player);
    }

    protected String getVerb() {
            return "drink";
    }

    protected boolean isAcceptable(Item item) {
            return item.quaffEffect() != null;
    }

    protected Screen use(Item item) {
            player.quaff(item);
            return null;
    }
}