package tech.raidtheweb.java.rogue.screens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;
import tech.raidtheweb.java.rogue.Main;

public class MenuScreen implements Screen {

	private PlayScreen playscreen;

	public MenuScreen(PlayScreen playscreen) {
		this.playscreen = playscreen;
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		terminal.writeCenter("+----- Menu -----+", 15);
		terminal.writeCenter("| [S]ave Game    |", 16);
		terminal.writeCenter("| [E]xit To Menu |", 17);
		terminal.writeCenter("| [Q]uit To OS   |", 18);
		terminal.writeCenter("+----------------+", 19);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		switch(key.getKeyCode()) {
			case KeyEvent.VK_S:
				return new SaveScreen(playscreen);
			case KeyEvent.VK_E:
				Main.restartApplication();
			case KeyEvent.VK_Q:
				System.exit(0);
				break;
		}
		return this;
	}

}
