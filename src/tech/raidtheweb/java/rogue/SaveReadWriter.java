package tech.raidtheweb.java.rogue;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;

public class SaveReadWriter {
	
	private String filename;
	
	
	public SaveReadWriter(String filename) {
		this.filename = filename;
	}
	
	public Object readSave() {
		try {
			 
			FileTime lastEdited = Files.getLastModifiedTime(Paths.get(this.filename));
			
			Main.log("[SaveHandler]: Attempting to load last save file: " + lastEdited.toString());
			
            FileInputStream fileIn = new FileInputStream(this.filename);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
 
            Object obj = objectIn.readObject();
 
            Main.log("[SaveHandler]: Save File Loaded");
            objectIn.close();
            return obj;
 
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
	}
	
	public void writeSave(World world, Creature player, FieldOfView fov) {
		try {
			Main.log("[SaveHandler]: Saving game...");
			Object serObj = new SaveGameObject(player, world, fov);
            FileOutputStream fileOut = new FileOutputStream(this.filename);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(serObj);
            objectOut.close();
            Main.log("[SaveHandler]: Save File Saved");
 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
}
