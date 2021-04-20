package tech.raidtheweb.java.rogue.screens;

import tech.raidtheweb.java.rogue.Creature;
import tech.raidtheweb.java.rogue.Item;

public class ExamineScreen extends InventoryBasedScreen {

    public ExamineScreen(Creature player) {
        super(player);
    }

    protected String getVerb() {
        return "examine";
    }

    @Override
    protected boolean isAcceptable(Item item) {
        return true;
    }

    @Override
    protected Screen use(Item item) {
        String article = "aeiou".contains(item.name().subSequence(0, 1)) ? "an " : "a ";
        player.notify("It's " + article + item.name() + "." + item.details());
        return null;
    }
}