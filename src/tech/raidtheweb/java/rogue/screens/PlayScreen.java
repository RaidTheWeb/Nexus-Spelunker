package tech.raidtheweb.java.rogue.screens;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import asciiPanel.AsciiPanel;
import tech.raidtheweb.java.dsl.Lang.Lexer;
import tech.raidtheweb.java.dsl.Lang.Parser;
import tech.raidtheweb.java.dsl.Lang.Token;
import tech.raidtheweb.java.dsl.Lang.ast.Statement;
import tech.raidtheweb.java.dsl.lib.ArrayValue;
import tech.raidtheweb.java.dsl.lib.Function;
import tech.raidtheweb.java.dsl.lib.Functions;
import tech.raidtheweb.java.dsl.lib.Value;
import tech.raidtheweb.java.rogue.Creature;
import tech.raidtheweb.java.rogue.FieldOfView;
import tech.raidtheweb.java.rogue.Main;
import tech.raidtheweb.java.rogue.SaveGameObject;
import tech.raidtheweb.java.rogue.SaveReadWriter;
import tech.raidtheweb.java.rogue.Tile;
import tech.raidtheweb.java.rogue.World;
import tech.raidtheweb.java.rogue.WorldBuilder;
import tech.raidtheweb.java.rogue.WorldFactory;
import tech.raidtheweb.java.rogue.Scripting.NPCValue;
import tech.raidtheweb.java.rogue.brains.creatures.BatAi;
import tech.raidtheweb.java.rogue.brains.creatures.FungusAi;
import tech.raidtheweb.java.rogue.brains.creatures.NPCAi;
import tech.raidtheweb.java.rogue.brains.creatures.NPCOffer;
import tech.raidtheweb.java.rogue.brains.creatures.PlayerAi;
import tech.raidtheweb.java.rogue.brains.creatures.ZombieAi;


public class PlayScreen implements Screen {
	private World world;
	private Creature player;
	private int screenWidth;
	private int screenHeight;
	private List<String> messages;
	private FieldOfView fov;
	public Screen subscreen;
	private String saveFileLoc = System.getProperty("user.home") + File.separator + "NexusSpelunkerRL" + File.separator + "roguelike.sav";
	private String progFileLoc = System.getProperty("user.home") + File.separator + "NexusSpelunkerRL" + File.separator + "program.dsl";
	private String dslScript = null;
	private JFrame frame;
	
	public PlayScreen(boolean isNewGame, JFrame frame){
		this.frame = frame;
		screenWidth = 90;
		screenHeight = 23;
		messages = new ArrayList<String>();
		
		if(!Files.isDirectory(Paths.get(System.getProperty("user.home") + File.separator + "NexusSpelunkerRL"))) {
			try {
				Files.createDirectory(Paths.get(System.getProperty("user.home") + File.separator + "NexusSpelunkerRL"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if(Files.exists(Paths.get(saveFileLoc)) && isNewGame == false) {
			SaveReadWriter loader = new SaveReadWriter(saveFileLoc);
			SaveGameObject save = (SaveGameObject) loader.readSave();
			this.world = save.getWorld();
			this.player = save.getPlayer();
		} else {
			createWorld();
		}
		
		
		if(Files.exists(Paths.get(this.progFileLoc))) {
			try {
				this.dslScript  = new String( Files.readAllBytes(Paths.get(this.progFileLoc)) );
				this.registerScriptConstants();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		fov = new FieldOfView(world);
		
		WorldFactory factory = new WorldFactory(world);
		createCreatures(factory);
		createItems(factory);
	}
	
	private void registerScriptConstants() {
		Functions.set("NotifyPlayer", (Function) (Value... args) -> {
			String output = "(!) ";
			for (Value arg : args) {
        		output += arg.asString() + " ";
        	}
        	player.notify(output);
			return null;
		});
		Functions.set("NewBat", (Function) (Value... args) -> {
			if (args.length != 3) return null;
			Creature bat = new Creature(world, 'b', AsciiPanel.yellow, "bat", 15, 5, 0);
			new BatAi(bat);
			bat.x = (int)args[0].asDouble();
			bat.y = (int)args[1].asDouble();
			bat.z = (int)args[2].asDouble();
			world.creatures.add(bat);
			return null;
		});
		Functions.set("NewZombie", (Function) (Value... args) -> {
			if (args.length != 3) return null;
			Creature zombie = new Creature(world, 'z', AsciiPanel.green , "zombie", 50, 10, 10);
			new ZombieAi(zombie, player);
			zombie.x = (int)args[0].asDouble();
			zombie.y = (int)args[1].asDouble();
			zombie.z = (int)args[2].asDouble();
			world.creatures.add(zombie);
			return null;
		});
		Functions.set("NewFungus", (Function) (Value... args) -> {
			if (args.length != 3) return null;
			Creature fungus = new Creature(world, 'f', AsciiPanel.green, "fungus", 10, 0, 0);
			new FungusAi(fungus);
			fungus.x = (int)args[0].asDouble();
			fungus.y = (int)args[1].asDouble();
			fungus.z = (int)args[2].asDouble();
			world.creatures.add(fungus);
			return null;
		});
		Functions.set("NewNPCOffer", (Function) (Value... args) -> {
			if (args.length != 2) return null;
			return new NPCValue(args[1], args[0]);
		});
		Functions.set("NewNPC", (Function) (Value... args) -> {
			if (args.length != 4) return null;
			Creature npc = new Creature(world, 'X', AsciiPanel.cyan, "merchant", 100, 0, 10);
			ArrayValue offersPre = (ArrayValue) args[3];
			List<NPCOffer> offers;
			offers = new ArrayList<>();
			for(Value offerPre : offersPre.elements) {
				NPCValue offer = (NPCValue) offerPre;
				offers.add(offer.getOffer());
			}
			new NPCAi(npc, offers);
			npc.x = (int)args[0].asDouble();
			npc.y = (int)args[1].asDouble();
			npc.z = (int)args[2].asDouble();
			world.creatures.add(npc);
			return null;
		});
	}

	private void createCreatures(WorldFactory factory){
		if(player == null) {
			player = factory.newPlayer(messages, fov);
		} else {
			new PlayerAi(player, messages, fov);
		}
		
		for (int z = 0; z < world.depth(); z++){
			for (int i = 0; i < 8; i++){
				factory.newFungus(z);
			}
			for (int i = 0; i < 20; i++){
				factory.newBat(z);
				factory.newNPC(z);
			}
			for (int i = 0; i < z + 3; i++){
		         factory.newZombie(z, player);
		    }
		}
	}

	private void createItems(WorldFactory factory) {
		for (int z = 0; z < world.depth(); z++){
			for (int i = 0; i < world.width() * world.height() / 20; i++){
				factory.newRock(z);
			}
			for (int i = 0; i < world.width() * world.height() / 60; i++){
				factory.randomWeapon(z);
			}
			factory.randomArmor(z);
		}
		factory.newVictoryItem(world.depth() - 1);
	}
	
	private void createWorld(){
		world = new WorldBuilder(120, 84, 40)
					.makeCaves()
					.build();
	}
	
	public int getScrollX() { return Math.max(0, Math.min(player.x - screenWidth / 2, world.width() - screenWidth)); }
	
	public int getScrollY() { return Math.max(0, Math.min(player.y - screenHeight / 2, world.height() - screenHeight)); }
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		terminal.clear();
		int left = getScrollX();
		int top = getScrollY(); 
		
		displayTiles(terminal, left, top);
		displayMessages(terminal, messages);
		
		terminal.write(String.format("+------------+"), 91, 1);
		String stats = String.format(" %3d/%3d hp", player.hp(), player.maxHp());
		terminal.write(stats, 91, 2);
		terminal.write(String.format(" attack:%d", player.attackValue()), 91, 3);
		terminal.write(String.format(" defense:%d", player.defenseValue()), 91, 4);
		
		terminal.write(String.format("+------------+"), 91, 5);
		
		terminal.write("    LEGEND        ", 91, 7, Color.BLACK, Color.gray);
		terminal.write(" b = Bat          ", 91, 8, Color.BLACK, Color.gray);
		terminal.write(" z = Zombie       ", 91, 9, Color.BLACK, Color.gray);
		terminal.write(" f = Fungus       ", 91, 10, Color.BLACK, Color.gray);
		terminal.write(" @ = Player       ", 91, 11, Color.BLACK, Color.gray);
		terminal.write(" X = Merchant     ", 91, 12, Color.BLACK, Color.gray);
		
		terminal.write(" [ESC] to open menu, [?] for help", 1, 23);
		
		
		if (subscreen != null)
			subscreen.displayOutput(terminal);
	}

	private void displayMessages(AsciiPanel terminal, List<String> messages) {
		int top = screenHeight - messages.size();
		for (int i = 0; i < messages.size(); i++){
			if ((top + i) > 24) {
				break;
			}
			else {
				try {
					terminal.writeCenter(messages.get(i), top + i);
				} catch(Exception e) {
					JOptionPane.showMessageDialog(this.frame, "Notify Message Overflow", "Fatal Error", JOptionPane.ERROR_MESSAGE);
					System.exit(0);
				}
			}
		}
		messages.clear();
	}
	
	public static String repeat(int count, String with) {
	    return new String(new char[count]).replace("\0", with);
	}
	
	public String getLongestString(List<String> array) {
		int index = 0; 
		int elementLength = array.get(0).length();
		for(int i=1; i< array.size(); i++) {
		    if(array.get(i).length() > elementLength) {
		        index = i; elementLength = array.get(i).length();
		    }
		}
		return array.get(index);
	}

	private void displayTiles(AsciiPanel terminal, int left, int top) {
		fov.update(player.x, player.y, player.z, player.visionRadius());
		
		for (int x = 0; x < screenWidth; x++){
			for (int y = 0; y < screenHeight; y++){
				int wx = x + left;
				int wy = y + top;

				if (player.canSee(wx, wy, player.z))
					terminal.write(world.glyph(wx, wy, player.z), x, y, world.color(wx, wy, player.z));
				else
					if(!(fov.tile(wx, wy, player.z) == Tile.FLOOR)) { 
						terminal.write(fov.tile(wx, wy, player.z).glyph(), x, y, Color.darkGray);
					} else {
						terminal.write(fov.tile(wx, wy, player.z).glyph(), x, y, Color.black);
					}
			}
		}
	}
	
	@Override
	public Screen respondToUserInput(KeyEvent key) {
		if (subscreen != null) {
			subscreen = subscreen.respondToUserInput(key);
		} else {
			switch (key.getKeyCode()){
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_H: player.moveBy(-1, 0, 0); break;
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_L: player.moveBy( 1, 0, 0); break;
			case KeyEvent.VK_UP:
			case KeyEvent.VK_K: player.moveBy( 0,-1, 0); break;
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_J: player.moveBy( 0, 1, 0); break;
			case KeyEvent.VK_D: subscreen = new DropScreen(player); break;
			case KeyEvent.VK_W: subscreen = new EquipScreen(player); break;
			case KeyEvent.VK_T: subscreen = new ThrowScreen(player,
			        player.x - getScrollX(),
			        player.y - getScrollY()); break;
			case KeyEvent.VK_B:
				Creature npc = null;
				if(player.creature(player.x + 1, player.y, player.z) != null && player.creature(player.x + 1, player.y, player.z).name().equalsIgnoreCase("merchant")) {
					npc = player.creature(player.x + 1, player.y, player.z);
					
				} else if(player.creature(player.x - 1, player.y, player.z) != null && player.creature(player.x - 1, player.y, player.z).name().equalsIgnoreCase("merchant")) {
					npc = player.creature(player.x - 1, player.y, player.z);
					
				} else if(player.creature(player.x, player.y - 1, player.z) != null && player.creature(player.x, player.y - 1, player.z).name().equalsIgnoreCase("merchant")) {
					npc = player.creature(player.x, player.y - 1, player.z);
					
				} else if(player.creature(player.x, player.y + 1, player.z) != null && player.creature(player.x, player.y + 1, player.z).name().equalsIgnoreCase("merchant")) {
					npc = player.creature(player.x, player.y + 1, player.z);
				}
				NPCAi ai = (NPCAi)npc.ai;
				subscreen = ai.onInteract(player);
				break;
			case KeyEvent.VK_F:
		        if (player.weapon() == null || player.weapon().rangedAttackValue() == 0)
		         player.notify("You don't have a ranged weapon equiped.");
		        else
		         subscreen = new FireWeaponScreen(player,
		             player.x - getScrollX(),
		             player.y - getScrollY()); break;
			case KeyEvent.VK_ESCAPE:
				subscreen = new MenuScreen(this);
				break;
			case KeyEvent.VK_F11:
				player.notify("DSL Script Engine: Running Script...");
				Main.log("[DSLHandler]: Running Script File...");
				if (this.dslScript == null) {
					player.notify("DSL Script Engine: No Script");
					break;
				}
				final List<Token> tokens = new Lexer(this.dslScript).tokenize();
				
				final Statement program = new Parser(tokens).parse();
		        program.execute();
		        player.notify("DSL Script Engine: Script Finished");
		        Main.log("[DSLHandler]: Finished Running Script");
				break;
			}
			
			switch (key.getKeyChar()){
			case 'g': player.pickup(); break;
			case 'm':
				if(world.tile(player.x, player.y, player.z) == Tile.STAIRS_UP) { player.moveBy( 0, 0, -1); break; }
				else if(world.tile(player.x, player.y, player.z) == Tile.STAIRS_DOWN) { player.moveBy( 0, 0, 1); break; }
				else { player.doAction("try to move levels but there is no staircase"); }
				break;
			case '?':
				subscreen = new HelpScreen();
				break;
			case 'x':
				subscreen = new ExamineScreen(player);
				break;
			case ';':
				subscreen = new LookScreen(player, "Looking", 
						player.x - getScrollX(), 
						player.y - getScrollY());
				break;
			}
		}
		
		if (subscreen == null)
			world.update(player.z);
		
		if (player.hp() < 1)
			return new LoseScreen(this.frame);
		
		return this;
	}
	
	public void saveGame() {
		SaveReadWriter saver = new SaveReadWriter(this.saveFileLoc);
		saver.writeSave(world, player);
	}
}



