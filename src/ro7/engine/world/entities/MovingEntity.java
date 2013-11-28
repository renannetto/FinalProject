package ro7.engine.world.entities;

import java.util.Map;

import cs195n.Vec2f;
import ro7.engine.world.entities.DynamicEntity;
import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.GameWorld;

public class MovingEntity extends DynamicEntity {

	protected float targetVelocity;

	public MovingEntity(GameWorld world, CollidingShape shape, String name,
			Map<String, String> properties) {
		super(world, shape, name, properties);
		if (properties.containsKey("targetVelocity")) {
			targetVelocity = Float.parseFloat(properties.get("targetVelocity"));
		} else {
			targetVelocity = 0.0f;
		}
	}

	@Override
	public void applyForce(Vec2f force) {

	}

	@Override
	public void applyImpulse(Vec2f impulse) {

	}

	@Override
	public void update(long nanoseconds) {
		float seconds = nanoseconds / 1000000000.0f;
		Vec2f translation = velocity.smult(seconds);
		shape.move(translation);
	}

	public void move(Vec2f direction) {
		direction = direction.normalized();
		velocity = velocity.plus(direction.smult(targetVelocity));
		if (velocity.mag2() != 0) {
			velocity = velocity.normalized();
		}
		velocity = velocity.smult(targetVelocity);
	}
	
	public void moveTo(Vec2f newPosition) {
		shape.moveTo(newPosition);
	}

	public void stop(Vec2f direction) {
		if (direction.x*velocity.x > 0) {
			velocity = new Vec2f(0.0f, velocity.y);
		}
		
		if (direction.y*velocity.y > 0) {
			velocity = new Vec2f(velocity.x, 0.0f);
		}
		
		if (velocity.mag2() != 0) {
			velocity = velocity.normalized();
		}
		velocity = velocity.smult(targetVelocity);
	}

}
