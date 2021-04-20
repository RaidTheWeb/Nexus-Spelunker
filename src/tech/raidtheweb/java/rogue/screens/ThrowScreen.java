package tech.raidtheweb.java.rogue.screens;

import tech.raidtheweb.java.rogue.Creature;
import tech.raidtheweb.java.rogue.Item;

public class ThrowScreen extends InventoryBasedScreen {
    private int sx;
    private int sy;

    public ThrowScreen(Creature player, int sx, int sy) {
        super(player);
        this.sx = sx;
        this.sy = sy;
    }

    protected String getVerb() {
        return "throw";
    }

    protected boolean isAcceptable(Item item) {
        return true;
    }

    protected Screen use(Item item) {
        return new ThrowAtScreen(player, sx, sy, item);
    }
}