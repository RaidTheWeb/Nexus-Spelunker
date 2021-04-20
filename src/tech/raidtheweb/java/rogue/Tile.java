package tech.raidtheweb.java.rogue;

import java.awt.Color;

import asciiPanel.AsciiPanel;

public enum Tile {
	FLOOR((char)250, AsciiPanel.brightBlack, "A dirt and rock cave floor."),
    WALL((char)219, AsciiPanel.white, "A dirt and rock cave wall."),
    BOUNDS('x', AsciiPanel.brightBlack, "Beyond the edge of the world."),
    STAIRS_DOWN('>', AsciiPanel.white, "A stone staircase that goes down."),
    STAIRS_UP('<', AsciiPanel.white, "A stone staircase that goes up."),
    UNKNOWN(' ', AsciiPanel.white, "(unknown)");

    private String details;
    public String details(){ return details; }

    Tile(char glyph, Color color, String details){
        this.glyph = glyph;
        this.color = color;
        this.details = details;
    }
	
	private char glyph;
	public char glyph() { return glyph; }
	
	private Color color;
	public Color color() { return color; }
	
	public boolean isGround() {
		return this != WALL && this != BOUNDS;
	}

	public boolean isDiggable() {
		return this == Tile.WALL;
	}
}
