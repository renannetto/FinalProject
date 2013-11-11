package ro7.engine.world.entities;

import java.util.Map;

import cs195n.Vec2f;
import ro7.engine.world.entities.DynamicEntity;
import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.GameWorld;

public class MovingEntity extends DynamicEntity {

	public MovingEntity(GameWorld world, CollidingShape shape, String name,
			Map<String, String> properties) {
		super(world, shape, name, properties);
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
	
	public void changeVelocity(Vec2f velocity) {
		this.velocity = velocity;
	}

}
