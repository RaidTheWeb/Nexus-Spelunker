package tech.raidtheweb.java.rogue.screens;

import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import asciiPanel.AsciiPanel;

public class LoseScreen implements Screen {
	
    private JFrame frame;

	public LoseScreen(JFrame frame) {
		this.frame = frame;
	}

	public void displayOutput(AsciiPanel terminal) {
        terminal.write("You lost.", 1, 1);
        terminal.writeCenter("-- press [enter] to continue --", 22);
    }

    public Screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER ? new StartScreen(this.frame) : this;
    }
}