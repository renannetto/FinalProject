package ro7.game.world.items;

import java.util.Map;
import java.util.Set;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.engine.world.io.Output;

public class StateItem extends GameItem {

	private int states;
	private int currentState;

	public StateItem(GameWorld world, CollidingShape shape, String name,
			Map<String, String> properties) {
		super(world, shape, name, properties);

		if (properties.containsKey("initialState")) {
			this.currentState = Integer
					.parseInt(properties.get("initialState"));
		} else {
			this.currentState = 0;
		}

		if (properties.containsKey("states")) {
			this.states = Integer.parseInt(properties.get("states"));
		} else {
			this.states = 2;
		}

		for (int i = 0; i < states; i++) {
			outputs.put("onState" + i, new Output());
		}
	}

	@Override
	public void receiveAction(Collision collision, Set<Item> inventory) {
		super.receiveAction(collision, inventory);
		outputs.get("onState" + currentState).run();

		currentState = (currentState + 1) % states;
	}

}
