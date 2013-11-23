package ro7.game.world;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import ro7.engine.world.GameWorld;
import ro7.engine.world.SaveFile;
import cs195n.Vec2f;

public class FinalSaveFile extends SaveFile {

	public FinalSaveFile(String filename) {
		super(filename);
	}

	@Override
	public GameWorld load(Vec2f dimensions) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(saveFile));
			String level = reader.readLine();
			String map = reader.readLine();
			
			FinalWorld world = new FinalWorld(dimensions, this);
			world.initLevel(level);
			world.loadMap(map);
			
			reader.close();
			return world;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Save file not found");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Could not reaed from file");
		}
		return null;
	}

}
