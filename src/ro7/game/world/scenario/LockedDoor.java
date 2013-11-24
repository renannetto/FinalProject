package ro7.game.world.scenario;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.engine.world.io.Input;
import ro7.game.screens.GameScreen;
import ro7.game.world.FinalWorld;
import ro7.game.world.player.Item;

public class LockedDoor extends Door {

	private Set<Item> locks;
	private Set<Item> unlocked;

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

		inputs.put("win", new Input() {

			@Override
			public void run(Map<String, String> args) {
				((FinalWorld) (LockedDoor.super.world)).win();
			}
		});
	}

	@Override
	public void onCollision(Collision collision) {

	}

	@Override
	public void receiveAction(Collision collision, Set<Item> inventory) {
		for (Item item : locks) {
			if (inventory.contains(item)) {
				unlocked.add(item);
			}
		}
		if (unlocked.size() == locks.size()) {
			super.onCollision(collision);
		} else {
			GameScreen.playCutscene("resources/cutscenes/lockedDoor.txt");
		}
	}

}
