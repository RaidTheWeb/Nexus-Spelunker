package tech.raidtheweb.java.rogue.screens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

public class SaveScreen implements Screen {

	private PlayScreen playscreen;

	public SaveScreen(PlayScreen playscreen) {
		this.playscreen = playscreen;
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		terminal.writeCenter("                                                              ", 1);
		terminal.writeCenter(" _____                    _____         _         _           ", 2);
		terminal.writeCenter("|   | |___ _ _ _ _ ___   |   __|___ ___| |_ _ ___| |_ ___ ___ ", 3);
		terminal.writeCenter("| | | | -_|_'_| | |_ -|  |__   | . | -_| | | |   | '_| -_|  _|", 4);
		terminal.writeCenter("|_|___|___|_,_|___|___|  |_____|  _|___|_|___|_|_|_,_|___|_|  ", 5);
		terminal.writeCenter("                               |_|                            ", 6);
		terminal.writeCenter("+----- Save Game? -----+", 15);
		terminal.writeCenter("| This will overwrite  |", 16);
		terminal.writeCenter("| Any previous save    |", 17);
		terminal.writeCenter("| [C]ontinue?          |", 18);
		terminal.writeCenter("| (Press key to cancel)|", 19);
		terminal.writeCenter("+----------------------+", 20);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		switch(key.getKeyCode()) {
		case KeyEvent.VK_C:
			playscreen.saveGame();
			return null;
		default:
			return null;
		}
	}

}
