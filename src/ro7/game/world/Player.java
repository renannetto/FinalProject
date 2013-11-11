package ro7.game.world;

import java.util.Map;

import cs195n.Vec2f;
import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.GameWorld;
import ro7.engine.world.entities.MovingEntity;

public class Player extends MovingEntity {
	
	private final float VELOCITY = 100.0f;

	public Player(GameWorld world, CollidingShape shape, String name,
			Map<String, String> properties) {
		super(world, shape, name, properties);
	}

	public void move(Vec2f direction) {
		changeVelocity(direction.smult(VELOCITY));
	}

	public void stop() {
		changeVelocity(new Vec2f(0.0f, 0.0f));
	}

}
