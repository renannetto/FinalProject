package ro7.game.world.scenario;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.engine.world.io.Output;
import ro7.game.screens.GameScreen;
import ro7.game.world.items.Item;

public class LockedDoor extends Door {

	private Set<Item> locks;
	private Set<Item> unlocked;
	
	private String lockedCutscene;

	public LockedDoor(GameWorld world, CollidingShape shape, String name,
			Map<String, String> properties) {
		super(world, shape, name, properties);
		
		this.locks = new HashSet<Item>();
		this.unlocked = new HashSet<Item>();

		for (Map.Entry<String, String> entry : properties.entrySet()) {
			String key = entry.getKey();
			if (key.contains("lock")) {
				String value = entry.getValue();
				Item item = new Item(world, null, value);
				locks.add(item);
			}
		}
		
		if (properties.containsKey("lockedCutscene")) {
			lockedCutscene = properties.get("lockedCutscene");
		} else {
			lockedCutscene = "";
		}
		
		outputs.put("onUnlock", new Output());
	}

	@Override
	public void onCollision(Collision collision) {

	}

	@Override
	public void receiveAction(Collision collision, Set<Item> inventory) {
		for (Item item : locks) {
			if (inventory.contains(item)) {
				unlocked.add(item);
				outputs.get("onUnlock").run();
			}
		}
		if (unlocked.size() != locks.size() && !lockedCutscene.equals("")) {
			GameScreen.playCutscene(lockedCutscene);
		}
	}

}
