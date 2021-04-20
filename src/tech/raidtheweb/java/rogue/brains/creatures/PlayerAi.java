package tech.raidtheweb.java.rogue.brains.creatures;

import java.util.List;

import tech.raidtheweb.java.rogue.Creature;
import tech.raidtheweb.java.rogue.FieldOfView;
import tech.raidtheweb.java.rogue.Tile;

public class PlayerAi extends CreatureAi {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3965759849302473929L;
	private List<String> messages;
	private FieldOfView fov;
	
	public PlayerAi(Creature creature, List<String> messages, FieldOfView fov) {
		super(creature);
		this.messages = messages;
		this.fov = fov;
	}

	public void onEnter(int x, int y, int z, Tile tile){
		if (tile.isGround()){
			creature.x = x;
			creature.y = y;
			creature.z = z;
		} else if (tile.isDiggable()) {
			creature.dig(x, y, z);
		}
	}
	
	public void onNotify(String message){
		messages.add(message);
	}
	
	public boolean canSee(int wx, int wy, int wz) {
		return fov.isVisible(wx, wy, wz);
	}
	
	public Tile rememberedTile(int wx, int wy, int wz) {
        return fov.tile(wx, wy, wz);
    }
}