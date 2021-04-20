package tech.raidtheweb.java.rogue;

import java.awt.Color;
import java.io.Serializable;

public class Item implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6121604853068523260L;
	private int attackValue;
	public int attackValue() { return attackValue; }
	public void modifyAttackValue(int amount) { attackValue += amount; }

	private int defenseValue;
	public int defenseValue() { return defenseValue; }
	public void modifyDefenseValue(int amount) { defenseValue += amount; }

	private int thrownAttackValue;
    public int thrownAttackValue() { return thrownAttackValue; }
    public void modifyThrownAttackValue(int amount) { thrownAttackValue += amount; }
	
    private int rangedAttackValue;
    public int rangedAttackValue() { return rangedAttackValue; }
    public void modifyRangedAttackValue(int amount) { rangedAttackValue += amount; }

    
    private char glyph;
    public char glyph() { return glyph; }

    private Color color;
    public Color color() { return color; }

    private String name;
    public String name() { return name; }

    public Item(char glyph, Color color, String name){
        this.glyph = glyph;
        this.color = color;
        this.name = name;
    }
    
    public String details() {
        String details = "";

        if (attackValue != 0)
            details += "     attack:" + attackValue;

        if (defenseValue != 0)
            details += "     defense:" + defenseValue;
        
        return details;
    }
}
