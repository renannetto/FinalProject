package ro7.game.world.scenario;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.GameWorld;
import ro7.game.world.FinalWorld;
import ro7.game.world.items.Item;

public class InvisibleScenario extends Scenario {

	private boolean visible;
	private Item key;

	public InvisibleScenario(GameWorld world, CollidingShape shape,
			String name, Map<String, String> properties) {
		super(world, shape, name, properties);
		
		String keyName = properties.get("itemName");
		key = new Item(world, shape, keyName, new HashMap<String, String>());

		if (properties.containsKey("visible")) {
			this.visible = Boolean.parseBoolean(properties.get("visible"));
		} else {
			this.visible = false;
		}
	}

	@Override
	public void draw(Graphics2D g) {
		if (visible) {
			super.draw(g);
		}
	}
	
	@Override
	public void update(long nanoseconds) {
		if (((FinalWorld)world).playerHas(key)) {
			visible = true;
		} else {
			visible = false;
		}
		super.update(nanoseconds);
	}

}
