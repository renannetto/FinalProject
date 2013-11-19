package ro7.game.world;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import ro7.engine.sprites.AnimatedSprite;
import ro7.engine.sprites.ImageSprite;
import ro7.engine.sprites.SpriteSheet;
import ro7.engine.sprites.shapes.AAB;
import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.sprites.shapes.CollidingSprite;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.game.world.enemies.Enemy;
import cs195n.Vec2f;
import cs195n.Vec2i;

public class Player extends Character {

	private String attackCategory;
	private String attackCollision;
	private Attack currentAttack;

	private Map<Vec2f, ImageSprite> standing;
	private AnimatedSprite walkingDown;
	private AnimatedSprite walkingUp;

	public Player(GameWorld world, CollidingShape shape, String name,
			Map<String, String> properties) {
		super(world, shape, name, properties);
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

		standing = new HashMap<Vec2f, ImageSprite>();
		SpriteSheet standingSheet = world.getSpriteSheet(properties
				.get("standingSheet"));
		Vec2i standingDownPos = new Vec2i(Integer.parseInt(properties
				.get("posStandingDownX")), Integer.parseInt(properties
				.get("posStandingDownY")));
		Vec2i standingUpPos = new Vec2i(Integer.parseInt(properties
				.get("posStandingUpX")), Integer.parseInt(properties
				.get("posStandingUpY")));
		Vec2i standingLeftPos = new Vec2i(Integer.parseInt(properties
				.get("posStandingLeftX")), Integer.parseInt(properties
				.get("posStandingLeftY")));
		Vec2i standingRightPos = new Vec2i(Integer.parseInt(properties
				.get("posStandingRightX")), Integer.parseInt(properties
				.get("posStandingRightY")));
		standing.put(new Vec2f(0.0f, 1.0f), new ImageSprite(
				shape.getPosition(), standingSheet, standingDownPos));
		standing.put(new Vec2f(0.0f, -1.0f), new ImageSprite(
				shape.getPosition(), standingSheet, standingUpPos));
		standing.put(new Vec2f(1.0f, 0.0f), new ImageSprite(
				shape.getPosition(), standingSheet, standingLeftPos));
		standing.put(new Vec2f(-1.0f, 0.0f), new ImageSprite(
				shape.getPosition(), standingSheet, standingRightPos));

		SpriteSheet walkingSheet = world.getSpriteSheet(properties
				.get("walkingSheet"));
		int framesWalking = Integer.parseInt(properties.get("framesWalking"));
		float timeToMoveWalking = Float.parseFloat(properties
				.get("timeToMoveWalking"));

		Vec2i walkingDownPos = new Vec2i(Integer.parseInt(properties
				.get("posWalkingDownX")), Integer.parseInt(properties
				.get("posWalkingDownY")));
		walkingDown = new AnimatedSprite(shape.getPosition(), walkingSheet,
				walkingDownPos, framesWalking, timeToMoveWalking);

		Vec2i walkingUpPos = new Vec2i(Integer.parseInt(properties
				.get("posWalkingUpX")), Integer.parseInt(properties
				.get("posWalkingUpY")));
		walkingUp = new AnimatedSprite(shape.getPosition(), walkingSheet,
				walkingUpPos, framesWalking, timeToMoveWalking);
	}

	@Override
	public void update(long nanoseconds) {
		super.update(nanoseconds);

		if (velocity.y > 0) {
			((CollidingSprite) shape).updateSprite(walkingDown);
		} else if (velocity.y < 0) {
			((CollidingSprite) shape).updateSprite(walkingUp);
		} else {
			((CollidingSprite) shape).updateSprite(standing.get(direction));
		}

		if (currentAttack != null) {
			float seconds = nanoseconds / 1000000000.0f;
			Vec2f translation = velocity.smult(seconds);
			currentAttack.move(translation);
		}
		this.shape.update(nanoseconds);
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
		currentAttack = new Attack(world, attackShape, name, attackProperties);
		return currentAttack;
	}

	public Vec2f getPosition() {
		return shape.getPosition();
	}

	@Override
	public void onCollision(Collision collision) {
		super.onCollision(collision);
		if (collision.other instanceof Enemy) {
			receiveDamage(1);
			Vec2f mtv = collision.mtv;
			Vec2f centerDistance = collision.thisShape.center().minus(
					collision.otherShape.center());
			if (mtv.dot(centerDistance) < 0) {
				mtv = mtv.smult(-1.0f);
			}
			push(mtv);
		}
	}

	@Override
	public void receiveDamage(int damage) {
		super.receiveDamage(damage);
		((FinalWorld) world).decreaseLife();
		if (lives <= 0) {
			((FinalWorld) world).lose();
		}
	}

}
