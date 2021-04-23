package tech.raidtheweb.java.rogue;

import java.io.Serializable;

public class Effect implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2560507245762606712L;
	protected int duration;
    
    public boolean isDone() { return duration < 1; }
    
    public Effect(int duration){
    	this.duration = duration;
    }
    
    public void update(Creature creature){
    	duration--;
    }
    
    public void start(Creature creature){
            
    }
    
    public void end(Creature creature){
            
    }
}