package tech.raidtheweb.java.rogue.brains.creatures;

import java.util.List;

import tech.raidtheweb.java.rogue.Creature;
import tech.raidtheweb.java.rogue.Point;
import tech.raidtheweb.java.rogue.brains.Path;

public class ZombieAi extends CreatureAi {
	  /**
	 * 
	 */
	private static final long serialVersionUID = 2524119427894189756L;
	private Creature player;

	  public ZombieAi(Creature creature, Creature player) {
		  super(creature);
		  this.player = player;
	  }
	  
	  public void onUpdate(){
	      if (Math.random() < 0.2)
	          return;
	  
	      if (creature.canSee(player.x, player.y, player.z))
	          hunt(player);
	      else
	          wander();
	  }
	  
	  public void hunt(Creature target){
	      List<Point> points = new Path(creature, target.x, target.y).points();
	  
	      int mx = points.get(0).x - creature.x;
	      int my = points.get(0).y - creature.y;
	  
	      creature.moveBy(mx, my, 0);
	  }
}