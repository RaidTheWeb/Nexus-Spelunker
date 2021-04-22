package tech.raidtheweb.java.rogue;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import tech.raidtheweb.java.rogue.brains.creatures.CreatureAi;

public class Creature implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2112039248451035534L;

	private World world;
	
	public int x;
	public int y;
	public int z;
	
	private char glyph;
	public char glyph() { return glyph; }
	
	private Color color;
	public Color color() { return color; }

	public CreatureAi ai;
	public void setCreatureAi(CreatureAi ai) { this.ai = ai; }
	
	private int maxHp;
	public int maxHp() { return maxHp; }
	
	private int hp;
	public int hp() { return hp; }
	
	private int attackValue;
	public int attackValue() {
	    return attackValue
	     + (weapon == null ? 0 : weapon.attackValue())
	     + (armor == null ? 0 : armor.attackValue());
	}
	
	private int defenseValue;
	public int defenseValue() {
	    return defenseValue
	     + (weapon == null ? 0 : weapon.defenseValue())
	     + (armor == null ? 0 : armor.defenseValue());
	}

	private List<Effect> effects;
	public List<Effect> effects(){ return effects; }


	private int visionRadius;
	public int visionRadius() { return visionRadius; }
	public void setVision(int distance) { this.visionRadius = distance; }
	
	private Inventory inventory;
    public Inventory inventory() { return inventory; }

	private String name;
	public String name() { return name; }
	
	private Item weapon;
	public Item weapon() { return weapon; }

	private Item armor;
	public Item armor() { return armor; }

	public void unequip(Item item){
	      if (item == null)
	         return;
	  
	      if (item == armor){
	          doAction("remove a " + item.name());
	          armor = null;
	      } else if (item == weapon) {
	          doAction("put away a " + item.name());
	          weapon = null;
	      }
	  }

	public void equip(Item item){
	      if (item.attackValue() == 0 && item.defenseValue() == 0)
	          return;
	  
	      if (item.attackValue() >= item.defenseValue()){
	          unequip(weapon);
	          doAction("wield a " + item.name());
	          weapon = item;
	      } else {
	          unequip(armor);
	          doAction("put on a " + item.name());
	          armor = item;
	      }
	  }

	
	public Creature(World world, char glyph, Color color, String name, int maxHp, int attack, int defense){
		this.world = world;
		this.glyph = glyph;
		this.color = color;
		this.maxHp = maxHp;
		this.hp = maxHp;
		this.attackValue = attack;
		this.defenseValue = defense;
		this.visionRadius = 9;
		this.name = name;
		this.inventory = new Inventory(20);
		this.effects = new ArrayList<>();
	}
	
	public void moveBy(int mx, int my, int mz){
		if (mx==0 && my==0 && mz==0)
			return;
		
		Tile tile = world.tile(x+mx, y+my, z+mz);
		
		if (mz == -1){
			if (tile == Tile.STAIRS_DOWN) {
				doAction("walk up the stairs to level %d", z+mz+1);
			} else {
				doAction("try to go up but are stopped by the cave ceiling");
				return;
			}
		} else if (mz == 1){
			if (tile == Tile.STAIRS_UP) {
				doAction("walk down the stairs to level %d", z+mz+1);
			} else {
				doAction("try to go down but are stopped by the cave floor");
				return;
			}
		}
		
		Creature other = world.creature(x+mx, y+my, z+mz);
		
		if (other == null)
			ai.onEnter(x+mx, y+my, z+mz, tile);
		else
			attack(other);
	}

	public void attack(Creature other){
		int amount = Math.max(0, attackValue() - other.defenseValue());
		
		amount = (int)(Math.random() * amount) + 1;
		
		doAction("attack the %s for %d damage", other.name, amount);
		
		other.modifyHp(-amount);
	}

	public void modifyHp(int amount) {
		hp += amount;
		
		if (hp < 1) {
			doAction("die");
			world.remove(this);
		}
	}
	
	public void dig(int wx, int wy, int wz) {
		world.dig(wx, wy, wz);
		doAction("dig");
	}
	
	public void update(){
		ai.onUpdate();
		updateEffects();
	}

	public boolean canEnter(int wx, int wy, int wz) {
		return world.tile(wx, wy, wz).isGround() && world.creature(wx, wy, wz) == null;
	}

	public void notify(String message, Object ... params){
		ai.onNotify(String.format(message, params));
	}
	
	public void doAction(String message, Object ... params){
		int r = 9;
		for (int ox = -r; ox < r+1; ox++){
			for (int oy = -r; oy < r+1; oy++){
				if (ox*ox + oy*oy > r*r)
					continue;
				
				Creature other = world.creature(x+ox, y+oy, z);
				
				if (other == null)
					continue;
				
				if (other == this)
					other.notify("You " + message + ".", params);
				else
					other.notify(String.format("The %s %s.", name, makeSecondPerson(message)), params);
			}
		}
	}
	
	private String makeSecondPerson(String text){
		String[] words = text.split(" ");
		words[0] = words[0] + "s";
		
		StringBuilder builder = new StringBuilder();
		for (String word : words){
			builder.append(" ");
			builder.append(word);
		}
		
		return builder.toString().trim();
	}
	
	public boolean canSee(int wx, int wy, int wz){
		return ai.canSee(wx, wy, wz);
	}
	
	public void pickup(){
        Item item = world.item(x, y, z);
    
        if (inventory.isFull() || item == null){
            doAction("grab at the ground");
        } else {
            doAction("pickup a %s", item.name());
            world.remove(x, y, z);
            inventory.add(item);
        }
    }
	
	public void drop(Item item){
	    if (world.addAtEmptySpace(item, x, y, z)){
	         doAction("drop a " + item.name());
	         inventory.remove(item);
	         unequip(item);
	    } else {
	         notify("There's nowhere to drop the %s.", item.name());
	    }
	}
	
	public String details() {
        return String.format("      attack:%d     defense:%d     hp:%d", attackValue(), defenseValue(), hp);
    }
	
	public Tile realTile(int wx, int wy, int wz) {
        return world.tile(wx, wy, wz);
    }


	public Tile tile(int wx, int wy, int wz) {
        if (canSee(wx, wy, wz))
            return world.tile(wx, wy, wz);
        else
            return ai.rememberedTile(wx, wy, wz);
    }


	public Creature creature(int wx, int wy, int wz) {
        if (canSee(wx, wy, wz))
            return world.creature(wx, wy, wz);
        else
            return null;
    }


	public Item item(int wx, int wy, int wz) {
        if (canSee(wx, wy, wz))
            return world.item(wx, wy, wz);
        else
            return null;
    }
	
	public void throwItem(Item item, int wx, int wy, int wz) {
        Point end = new Point(x, y, 0);
    
        for (Point p : new Line(x, y, wx, wy)){
            if (!realTile(p.x, p.y, z).isGround())
                break;
            end = p;
        }
    
        wx = end.x;
        wy = end.y;
    
        Creature c = creature(wx, wy, wz);
    
        if (c != null)
            throwAttack(item, c);
        else
            doAction("throw a %s", item.name());
    
        unequip(item);
        inventory.remove(item);
        world.addAtEmptySpace(item, wx, wy, wz);
    }
	
	
	@SuppressWarnings("unused")
	private void getRidOf(Item item){
        inventory.remove(item);
        unequip(item);
    }

    @SuppressWarnings("unused")
	private void putAt(Item item, int wx, int wy, int wz){
        inventory.remove(item);
        unequip(item);
        world.addAtEmptySpace(item, wx, wy, wz);
    }
    
    public void meleeAttack(Creature other){
        commonAttack(other, attackValue(), "attack the %s for %d damage", other.name);
    }

    private void throwAttack(Item item, Creature other) {
        commonAttack(other, attackValue / 2 + item.thrownAttackValue(), "throw a %s at the %s for %d damage", item.name(), other.name);
    }

    public void rangedWeaponAttack(Creature other){
        commonAttack(other, attackValue / 2 + weapon.rangedAttackValue(), "fire a %s at the %s for %d damage", weapon.name(), other.name);
    }

    private void commonAttack(Creature other, int attack, String action, Object ... params) {
    
        int amount = Math.max(0, attack - other.defenseValue());
    
        amount = (int)(Math.random() * amount) + 1;
    
        Object[] params2 = new Object[params.length+1];
        for (int i = 0; i < params.length; i++){
         params2[i] = params[i];
        }
        params2[params2.length - 1] = amount;
    
        doAction(action, params2);
    
        other.modifyHp(-amount);
    }
	public void modifyAttackValue(int i) {
		this.attackValue += i;
	}
	public void modifyDefenseValue(int i) {
		this.defenseValue += i;
	}
	
	public void quaff(Item item){
        doAction("quaff a " + item.name());
        consume(item);
    }
    
    private void consume(Item item){
            
        addEffect(item.quaffEffect());
        
        getRidOf(item);
    }
    
    private void addEffect(Effect effect){
        if (effect == null)
            return;
            
        effect.start(this);
        effects.add(effect);
    }
    
    private void updateEffects(){
        List<Effect> done = new ArrayList<Effect>();
            
        for (Effect effect : effects){
            effect.update(this);
            if (effect.isDone()) {
                effect.end(this);
                done.add(effect);
            }
        }
            
        effects.removeAll(done);
    }
}
