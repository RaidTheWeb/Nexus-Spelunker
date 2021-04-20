package tech.raidtheweb.java.rogue.screens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

public class HelpScreen implements Screen {

    public void displayOutput(AsciiPanel terminal) {
        terminal.clear();
        terminal.writeCenter("Help Menu", 1);
    
        int y = 6;
        terminal.write("[g] to pick up", 2, y++);
        terminal.write("[d] to drop", 2, y++);
        terminal.write("[w] to wear or wield", 2, y++);
        terminal.write("[?] for help", 2, y++);
        terminal.write("[x] to examine your items", 2, y++);
        terminal.write("[;] to look around", 2, y++);
        terminal.write("[t] to throw", 2, y++);
        terminal.write("[f] to fire a ranged weapon", 2, y++);
        terminal.write("[b] to interact", 2, y++);
        terminal.write("[ESC] to open menu", 2, y++);
    
        terminal.writeCenter("-- press any key to continue --", 22);
    }

    public Screen respondToUserInput(KeyEvent key) {
        return null;
    }
}
