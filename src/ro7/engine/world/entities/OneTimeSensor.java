package ro7.engine.world.entities;

import java.util.Map;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;

public class OneTimeSensor extends Sensor {

	private boolean activated;

	public OneTimeSensor(GameWorld world, CollidingShape shape, String name,
			Map<String, String> properties) {
		super(world, shape, name, properties);

		this.activated = false;
	}

	@Override
	public void onCollision(Collision collision) {
		if (!activated) {
			super.onCollision(collision);
			activated = true;
		}
	}

	@Override
	public void onCollisionStatic(Collision collision) {
		if (!activated) {
			super.onCollisionStatic(collision);
			activated = true;
		}
	}
	
	@Override
	public void onCollisionDynamic(Collision collision) {
		if (!activated) {
			super.onCollisionDynamic(collision);
			activated = true;
		}
	}

}
