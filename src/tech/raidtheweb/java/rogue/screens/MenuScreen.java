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
		terminal.writeCenter("| [H]ome         |", 17);
		terminal.writeCenter("| [X]Set Home    |", 18);
		terminal.writeCenter("| [E]xit To Menu |", 19);
		terminal.writeCenter("| [Q]uit To OS   |", 20);
		terminal.writeCenter("+----------------+", 21);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		switch(key.getKeyCode()) {
			case KeyEvent.VK_S:
				return new SaveScreen(playscreen);
			case KeyEvent.VK_H:
				playscreen.player.goHome();
				return null;
			case KeyEvent.VK_X:
				playscreen.player.setHome(playscreen.player.x, playscreen.player.y, playscreen.player.z);
				return null;
			case KeyEvent.VK_E:
				Main.restartApplication();
				break;
			case KeyEvent.VK_Q:
				System.exit(0);
				break;
		}
		return this;
	}

}
