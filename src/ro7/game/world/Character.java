package ro7.game.world;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import ro7.engine.sprites.shapes.AAB;
import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.GameWorld;
import ro7.engine.world.entities.MovingEntity;
import cs195n.Vec2f;

public class Character extends MovingEntity {

	protected int lives;
	protected Vec2f direction;
	protected String attackCategory;
	protected String attackCollision;
	protected Attack currentAttack;

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
		if (properties.containsKey("attackCategory")) {
			attackCategory = properties.get("attackCategory");
		} else {
			attackCategory = "-1";
		}
		if (properties.containsKey("attackCollision")) {
			attackCollision = properties.get("attackCollision");
		} else {
			attackCollision = "-1";
		}
	}
	
	@Override
	public void update(long nanoseconds) {
		super.update(nanoseconds);
		if (currentAttack != null) {
			float seconds = nanoseconds / 1000000000.0f;
			Vec2f translation = velocity.smult(seconds);
			currentAttack.move(translation);
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
	
	@Override
	public void stop(Vec2f direction) {
		super.stop(direction);
		if (this.velocity.mag2() > 0) {
			this.direction = this.velocity.normalized();
		}
	}
	
	public Attack attack(String name) {
		Map<String, String> attackProperties = new HashMap<String, String>();
		attackProperties.put("categoryMask", attackCategory);
		attackProperties.put("collisionMask", attackCollision);
		attackProperties.put("damage", "1");

		Vec2f attackDirection = direction;
		if (Math.abs(attackDirection.y) >= Math.abs(attackDirection.x)) {
			attackDirection = new Vec2f(0.0f, attackDirection.y).normalized();
		} else {
			attackDirection = new Vec2f(attackDirection.x, 0.0f).normalized();
		}
		Vec2f attackPosition = shape.getPosition().plus(
				shape.getDimensions().pmult(attackDirection));
		CollidingShape attackShape = new AAB(attackPosition, Color.BLUE,
				Color.BLUE, shape.getDimensions());
		currentAttack = new Attack(world, attackShape, name,
				attackProperties);
		return currentAttack;
	}

	public void receiveDamage(int damage) {
		this.lives -= damage;
	}
	
	public void push(Vec2f mtv) {
		Vec2f translation = mtv.normalized().pmult(shape.getDimensions()); 
		shape.move(translation);
	}

}
