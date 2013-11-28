package ro7.game.world;

import java.util.Map;
import java.util.Set;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.engine.world.entities.MovingEntity;
import ro7.game.world.player.Item;
import cs195n.Vec2f;

public abstract class Character extends MovingEntity implements FinalEntity {

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
		}
	}
	
	@Override
	public void stop(Vec2f direction) {
		super.stop(direction);
		if (this.velocity.mag2() > 0) {
			this.direction = this.velocity.normalized();
		}
	}

	@Override
	public void receiveDamage(int damage) {
		this.lives -= damage;
	}
	
	@Override
	public void receiveAction(Collision collision, Set<Item> inventory) {
		// TODO Auto-generated method stub
		
	}
	
	public void push(Vec2f mtv) {
		Vec2f translation = mtv.normalized().pmult(shape.getDimensions().smult(1.5f)); 
		shape.move(translation);
	}

}
