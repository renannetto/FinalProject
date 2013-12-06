package ro7.game.world;

import java.util.HashMap;
import java.util.Map;

import ro7.engine.sprites.AnimatedSprite;
import ro7.engine.sprites.ImageSprite;
import ro7.engine.sprites.SpriteSheet;
import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.sprites.shapes.CollidingSprite;
import ro7.engine.world.GameWorld;
import ro7.game.world.entities.FinalEntity;
import ro7.game.world.entities.FinalMovingEntity;
import cs195n.Vec2f;
import cs195n.Vec2i;

public abstract class Character extends FinalMovingEntity implements
		FinalEntity {

	protected final float DAMAGE_DELAY = 0.2f;
	protected final float DAMAGE_VELOCITY = 200.0f;

	protected int lives;
	protected Vec2f direction;
	protected float originalVelocity;

	protected float damageTime;

	protected Map<Vec2f, ImageSprite> standing;
	protected Map<Vec2f, AnimatedSprite> walking;

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
		this.originalVelocity = targetVelocity;

		this.damageTime = DAMAGE_DELAY;

		SpriteSheet walkingSheet = world.getSpriteSheet(properties
				.get("walkingSheet"));

		standing = new HashMap<Vec2f, ImageSprite>();
		Vec2i posDown = new Vec2i(Integer.parseInt(properties.get("posDownX")),
				Integer.parseInt(properties.get("posDownY")));
		Vec2i posUp = new Vec2i(Integer.parseInt(properties.get("posUpX")),
				Integer.parseInt(properties.get("posUpY")));
		Vec2i posRight = new Vec2i(
				Integer.parseInt(properties.get("posRightX")),
				Integer.parseInt(properties.get("posRightY")));
		Vec2i posLeft = new Vec2i(Integer.parseInt(properties.get("posLeftX")),
				Integer.parseInt(properties.get("posLeftY")));
		standing.put(new Vec2f(0.0f, 1.0f), new ImageSprite(
				shape.getPosition(), walkingSheet, posDown));
		standing.put(new Vec2f(0.0f, -1.0f),
				new ImageSprite(shape.getPosition(), walkingSheet, posUp));
		standing.put(new Vec2f(1.0f, 0.0f), new ImageSprite(
				shape.getPosition(), walkingSheet, posRight));
		standing.put(new Vec2f(-1.0f, 0.0f),
				new ImageSprite(shape.getPosition(), walkingSheet, posLeft));

		int framesWalking = Integer.parseInt(properties.get("framesWalking"));
		float timeToMoveWalking = Float.parseFloat(properties
				.get("timeToMoveWalking"));
		walking = new HashMap<Vec2f, AnimatedSprite>();
		walking.put(new Vec2f(0.0f, 1.0f),
				new AnimatedSprite(shape.getPosition(), walkingSheet, posDown,
						framesWalking, timeToMoveWalking));
		walking.put(new Vec2f(0.0f, -1.0f),
				new AnimatedSprite(shape.getPosition(), walkingSheet, posUp,
						framesWalking, timeToMoveWalking));
		walking.put(new Vec2f(1.0f, 0.0f),
				new AnimatedSprite(shape.getPosition(), walkingSheet, posRight,
						framesWalking, timeToMoveWalking));
		walking.put(new Vec2f(-1.0f, 0.0f),
				new AnimatedSprite(shape.getPosition(), walkingSheet, posLeft,
						framesWalking, timeToMoveWalking));
	}

	@Override
	public void update(long nanoseconds) {
		if (damageTime >= DAMAGE_DELAY) {
			if (targetVelocity == DAMAGE_VELOCITY) {
				stop(velocity.normalized());
				targetVelocity = originalVelocity;
			}

			updateSprite(nanoseconds);
		} else {
			damageTime += nanoseconds / 1000000000.0f;
		}
		super.update(nanoseconds);
		shape.update(nanoseconds);
	}

	protected void updateSprite(long nanoseconds) {
		if (velocity.mag2() == 0) {
			Vec2f roundedDirection = new Vec2f(Math.round(direction.x),
					Math.round(direction.y));
			((CollidingSprite) shape).updateSprite(standing
					.get(roundedDirection));
		} else {
			if (Math.abs(velocity.y) >= Math.abs(velocity.x)) {
				if (velocity.y > 0) {
					((CollidingSprite) shape).updateSprite(walking
							.get(new Vec2f(0.0f, 1.0f)));
				} else {
					((CollidingSprite) shape).updateSprite(walking
							.get(new Vec2f(0.0f, -1.0f)));
				}
			} else {
				if (velocity.x > 0) {
					((CollidingSprite) shape).updateSprite(walking
							.get(new Vec2f(1.0f, 0.0f)));
				} else {
					((CollidingSprite) shape).updateSprite(walking
							.get(new Vec2f(-1.0f, 0.0f)));
				}
			}
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
		if (Math.abs(direction.y) > 0) {
			super.stop(new Vec2f(0.0f, direction.y));
			if (this.velocity.mag2() > 0) {
				this.direction = this.velocity.normalized();
			}
		}
		if (Math.abs(direction.x) > 0) {
			super.stop(new Vec2f(direction.x, 0.0f));
			if (this.velocity.mag2() > 0) {
				this.direction = this.velocity.normalized();
			}
		}
	}

	@Override
	public void receiveDamage(int damage) {
		this.lives -= damage;
	}

	public void push(Vec2f mtv) {
		targetVelocity = DAMAGE_VELOCITY;
		move(mtv.normalized());
		damageTime = 0.0f;
		// Vec2f translation = mtv.normalized().pmult(shape.getDimensions());
		// shape.move(translation);
	}

}
