package tech.raidtheweb.java.rogue.screens;

import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import asciiPanel.AsciiPanel;

public class WinScreen implements Screen {

    private JFrame frame;

	public WinScreen(JFrame frame) {
		super();
		this.frame = frame;
	}

	public void displayOutput(AsciiPanel terminal) {
        terminal.write("You won.", 1, 1);
        terminal.writeCenter("-- press [enter] to continue --", 22);
    }

    public Screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER ? new StartScreen(this.frame) : this;
    }
}
