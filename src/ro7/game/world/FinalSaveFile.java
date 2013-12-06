package ro7.game.world;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import ro7.engine.world.GameWorld;
import ro7.engine.world.SaveFile;
import ro7.game.world.items.Item;
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

			FinalWorld world = new FinalWorld(dimensions, this);
			world.initLevel(level);

			int lives = Integer.parseInt(reader.readLine());
			world.setPlayerLives(lives);

			int inventorySize = Integer.parseInt(reader.readLine());
			for (int i = 0; i < inventorySize; i++) {
				String itemName = reader.readLine();
				Item item = new Item(world, null, itemName);
				world.getItem(item);
			}

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
