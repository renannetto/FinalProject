package ro7.game.world.items;

import java.util.HashMap;
import java.util.Map;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.game.world.FinalWorld;
import ro7.game.world.entities.FinalOneTimeSensor;

public class ItemSensor extends FinalOneTimeSensor {

	private Item item;

	public ItemSensor(GameWorld world, CollidingShape shape, String name,
			Map<String, String> properties) {
		super(world, shape, name, properties);

		String itemName = properties.get("itemName");
		item = new Item(world, null, itemName, new HashMap<String, String>());
	}

	@Override
	public void onCollision(Collision collision) {
		if (((FinalWorld) world).playerHas(item)) {
			super.onCollision(collision);
		}
	}

	@Override
	public void onCollisionStatic(Collision collision) {
		if (((FinalWorld) world).playerHas(item)) {
			super.onCollisionStatic(collision);
		}
	}

	@Override
	public void onCollisionDynamic(Collision collision) {
		if (((FinalWorld) world).playerHas(item)) {
			super.onCollisionDynamic(collision);
		}
	}

}
