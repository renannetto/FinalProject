package ro7.game.world;

import java.util.Map;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.GameWorld;
import ro7.engine.world.entities.MovingEntity;
import cs195n.Vec2f;

public class Character extends MovingEntity {

	protected int lives;
	protected Vec2f direction;

	public Character(GameWorld world, CollidingShape shape, String name,
			Map<String, String> properties) {
		super(world, shape, name, properties);
		if (properties.containsKey("lives")) {
			this.lives = Integer.parseInt(properties.get("lives"));
		} else {
			this.lives = 1;
		}
		if (properties.containsKey("directionX")
				&& properties.containsKey("directionY")) {
			this.direction = new Vec2f(Float.parseFloat(properties
					.get("directionX")), Float.parseFloat(properties
					.get("directionY")));
		} else {
			this.direction = new Vec2f(0.0f, -1.0f);
		}
	}
	
	@Override
	public void move(Vec2f direction) {
		super.move(direction);
		if (this.velocity.mag2() > 0) {
			this.direction = this.velocity.normalized();
		} else {
			this.direction = this.velocity;
		}
	}

	public void receiveDamage(int damage) {
		this.lives -= damage;
	}

}
