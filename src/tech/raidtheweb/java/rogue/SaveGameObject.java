package tech.raidtheweb.java.rogue;

import java.io.Serializable;

public class SaveGameObject implements Serializable {

	/**
	 * IDFK
	 */
	private static final long serialVersionUID = 2851863960867097013L;
	
	private Creature player;
	private World world;
	
	public SaveGameObject(Creature player, World world) {
		this.player = player;
		this.world = world;
	}
	
	public Creature getPlayer() {
		return player;
	}
	
	public World getWorld() {
		return world;
	}
}
