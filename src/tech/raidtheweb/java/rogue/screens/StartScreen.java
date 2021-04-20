package tech.raidtheweb.java.rogue.screens;

import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import asciiPanel.AsciiPanel;

public class StartScreen implements Screen {

	private JFrame frame;

	public StartScreen(JFrame frame) {
		this.frame = frame;
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		terminal.writeCenter("                                                              ", 1);
		terminal.writeCenter(" _____                    _____         _         _           ", 2);
		terminal.writeCenter("|   | |___ _ _ _ _ ___   |   __|___ ___| |_ _ ___| |_ ___ ___ ", 3);
		terminal.writeCenter("| | | | -_|_'_| | |_ -|  |__   | . | -_| | | |   | '_| -_|  _|", 4);
		terminal.writeCenter("|_|___|___|_,_|___|___|  |_____|  _|___|_|___|_|_|_,_|___|_|  ", 5);
		terminal.writeCenter("                               |_|                            ", 6);
		terminal.writeCenter("+----- Options -----+", 15);
		terminal.writeCenter("| [N]ew Game        |", 16);
		terminal.writeCenter("| [C]ontinue Game   |", 17);
		terminal.writeCenter("+-------------------+", 18);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		switch(key.getKeyCode()) {
		case KeyEvent.VK_N:
			return new PlayScreen(true, frame);
		case KeyEvent.VK_C:
			return new PlayScreen(false, frame);
		default:
			return this;
		}
	}

}
