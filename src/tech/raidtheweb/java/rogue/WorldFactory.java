package tech.raidtheweb.java.rogue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import asciiPanel.AsciiPanel;
import tech.raidtheweb.java.rogue.brains.creatures.BatAi;
import tech.raidtheweb.java.rogue.brains.creatures.FungusAi;
import tech.raidtheweb.java.rogue.brains.creatures.NPCAi;
import tech.raidtheweb.java.rogue.brains.creatures.NPCOffer;
import tech.raidtheweb.java.rogue.brains.creatures.PlayerAi;
import tech.raidtheweb.java.rogue.brains.creatures.ZombieAi;

public class WorldFactory implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6768163841864338179L;
	private World world;
	
	public WorldFactory(World world){
		this.world = world;
	}
	
	// PLAYER
	public Creature newPlayer(List<String> messages, FieldOfView fov){
		Creature player = new Creature(world, '@', AsciiPanel.brightWhite, "player", 100, 20, 5);
		world.addAtEmptyLocation(player, 0);
		new PlayerAi(player, messages, fov);
		player.setVision(7);
		return player;
	}
	
	
	// ENEMIES
	public Creature newFungus(int depth){
		Creature fungus = new Creature(world, 'f', AsciiPanel.green, "fungus", 10, 0, 0);
		world.addAtEmptyLocation(fungus, depth);
		new FungusAi(fungus);
		return fungus;
	}
	
	public Creature newBat(int depth){
		Creature bat = new Creature(world, 'b', AsciiPanel.yellow, "bat", 15, 5, 0);
		world.addAtEmptyLocation(bat, depth);
		new BatAi(bat);
		return bat;
	}
	
	public Creature newZombie(int depth, Creature player){
	      Creature zombie = new Creature(world, 'z', AsciiPanel.green , "zombie", 50, 10, 10);
	      world.addAtEmptyLocation(zombie, depth);
	      new ZombieAi(zombie, player);
	      return zombie;
	}
	
	public Creature newNPC(int depth) {
		Creature npc = new Creature(world, 'X', AsciiPanel.cyan, "merchant", 100, 0, 10);
		world.addAtEmptyLocation(npc, depth);
		List<NPCOffer> offers = new ArrayList<>();
		offers.add(randomTrade());
		new NPCAi(npc, offers);
		return npc;
	}
	
	public NPCOffer randomTrade() {
		switch((int)(Math.random() * 5)) {
		case 0: return new NPCOffer(Items.getItem("dagger"), Items.getItem("bow"));
		case 1: return new NPCOffer(Items.getItem("bow"), Items.getItem("staff"));
		case 2: return new NPCOffer(Items.getItem("chainmail"), Items.getItem("sword"));
		case 3: return new NPCOffer(Items.getItem("tunic"), Items.getItem("bow"));
		default:
			return new NPCOffer(Items.getItem("staff"), Items.getItem("tunic"));
		}
	}
	
	public Item randomItem() {
		switch ((int)(Math.random() * 6)){
        case 0: return new Item((char)174, AsciiPanel.yellow, "rock");
        case 1: 
        	Item dagger = new Item((char)173, AsciiPanel.white, "dagger");
        	dagger.modifyAttackValue(5);
        	return dagger;
        case 2: return new Item((char)172, AsciiPanel.yellow, "staff");
        case 3:
        	Item sword = new Item((char)173, AsciiPanel.brightWhite, "sword");
        	sword.modifyAttackValue(10);
        	return sword;
        case 4: 
        	Item bow = new Item((char)175, AsciiPanel.yellow, "bow");
        	bow.modifyAttackValue(1);
        	bow.modifyRangedAttackValue(5);
        	return bow;
        case 5: 
        	Item tunic = new Item((char)171, AsciiPanel.green, "tunic");
        	tunic.modifyDefenseValue(2);
        	return tunic;
        default: 
        	Item chainmail = new Item((char)171, AsciiPanel.white, "chainmail");
        	chainmail.modifyDefenseValue(4);
        	return chainmail;
        }
	}
	
	
	// ITEMS
	public Item newRock(int depth){
        Item rock = new Item((char)174, AsciiPanel.yellow, "rock");
        world.addAtEmptyLocation(rock, depth);
        return rock;
    }
	
	// WEAPONS
	public Item newDagger(int depth){
	    Item item = new Item((char)173, AsciiPanel.white, "dagger");
	    item.modifyAttackValue(5);
	    world.addAtEmptyLocation(item, depth);
	    return item;
	}

	public Item newSword(int depth){
	    Item item = new Item((char)173, AsciiPanel.brightWhite, "sword");
	    item.modifyAttackValue(10);
	    world.addAtEmptyLocation(item, depth);
	    return item;
	}

	public Item newStaff(int depth){
	    Item item = new Item((char)172, AsciiPanel.yellow, "staff");
	    item.modifyAttackValue(5);
	    item.modifyDefenseValue(3);
	    world.addAtEmptyLocation(item, depth);
	    return item;
	}
	
	public Item newBow(int depth){
        Item item = new Item((char)175, AsciiPanel.yellow, "bow");
        item.modifyAttackValue(1);
        item.modifyRangedAttackValue(5);
        world.addAtEmptyLocation(item, depth);
        return item;
    }

	
	// ARMOUR
	public Item newLightArmor(int depth){
	    Item item = new Item((char)171, AsciiPanel.green, "tunic");
	    item.modifyDefenseValue(2);
	    world.addAtEmptyLocation(item, depth);
	    return item;
	}

	public Item newMediumArmor(int depth){
	    Item item = new Item((char)171, AsciiPanel.white, "chainmail");
	    item.modifyDefenseValue(4);
	    world.addAtEmptyLocation(item, depth);
	    return item;
	}

	public Item newHeavyArmor(int depth){
	    Item item = new Item((char)171, AsciiPanel.brightWhite, "platemail");
	    item.modifyDefenseValue(6);
	    world.addAtEmptyLocation(item, depth);
	    return item;
	}

	public Item randomWeapon(int depth){
        switch ((int)(Math.random() * 4)){
        case 0: return newDagger(depth);
        case 1: return newSword(depth);
        case 2: return newBow(depth);
        default: return newStaff(depth);
        }
    }

	public Item randomArmor(int depth){
	    switch ((int)(Math.random() * 3)){
	    case 0: return newLightArmor(depth);
	    case 1: return newMediumArmor(depth);
	    default: return newHeavyArmor(depth);
	    }
	}
	
	// POTIONS
	public Item newPotionOfHealth(int depth){
	    Item item = new Item((char)21, AsciiPanel.red, "health potion");
	    item.setQuaffEffect(new Effect(1){
	        public void start(Creature creature){
	            if (creature.hp() == creature.maxHp())
	                return;
	                                
	            creature.modifyHp(15);
	            creature.doAction("look healthier");
	        }
	    });
	                
	    world.addAtEmptyLocation(item, depth);
	    return item;
	}
	
	public Item newPotionOfRegen(int depth){
	    Item item = new Item((char)21, AsciiPanel.red, "regeneration potion");
	    item.setQuaffEffect(new Effect(20){
	        public void start(Creature creature){
	            creature.doAction("look healthier");
	        }
	                        
	        public void update(Creature creature){
	            super.update(creature);
	            creature.modifyHp(1);
	        }
	    });
	                
	    world.addAtEmptyLocation(item, depth);
	    return item;
	}
	
	public Item newPotionOfWarrior(int depth){
	    Item item = new Item((char)21, AsciiPanel.brightYellow, "warrior's potion");
	    item.setQuaffEffect(new Effect(20){
	        public void start(Creature creature){
	            creature.modifyAttackValue(5);
	            creature.modifyDefenseValue(5);
	            creature.doAction("look stronger");
	        }
	        public void end(Creature creature){
	            creature.modifyAttackValue(-5);
	            creature.modifyDefenseValue(-5);
	            creature.doAction("look less strong");
	        }
	    });
	                
	    world.addAtEmptyLocation(item, depth);
	    return item;
	}

	public Item randomPotion(int depth){
        switch ((int)(Math.random() * 3)){
        case 0: return newPotionOfHealth(depth);
        //case 1: return newPotionOfRegen(depth);
        default: return newPotionOfWarrior(depth);
        }
	}
	
}
