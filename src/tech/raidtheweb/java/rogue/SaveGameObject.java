package tech.raidtheweb.java.rogue;

import java.io.Serializable;

public class SaveGameObject implements Serializable {

	/**
	 * IDFK
	 */
	private static final long serialVersionUID = 2851863960867097013L;
	
	private Creature player;
	private World world;
	private FieldOfView fov;
	
	public SaveGameObject(Creature player, World world, FieldOfView fov) {
		this.player = player;
		this.world = world;
		this.fov = fov;
	}
	
	public Creature getPlayer() {
		return player;
	}
	
	public World getWorld() {
		return world;
	}
	
	public FieldOfView getFov() {
		return fov;
	}
}
