package ro7.engine.world;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import cs195n.Vec2f;


public abstract class SaveFile {
	
	protected File saveFile;
	
	protected SaveFile(String filename) {
		saveFile = new File(filename);
	}
	
	public void save(GameWorld world) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile));
			writer.write(world.toString());
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Save file not found");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Failed to open save file");
		}
	}
	
	public abstract GameWorld load(Vec2f dimensions);

}
