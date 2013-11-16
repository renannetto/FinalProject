package ro7.game.world;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import ro7.engine.sprites.shapes.AAB;
import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.game.world.enemies.Enemy;
import cs195n.Vec2f;

public class Player extends Character {

	public Player(GameWorld world, CollidingShape shape, String name,
			Map<String, String> properties) {
		super(world, shape, name, properties);
	}

	public Attack attack() {
		Map<String, String> attackProperties = new HashMap<String, String>();
		attackProperties.put("categoryMask", "1");
		attackProperties.put("collisionMask", "2");
		attackProperties.put("damage", "1");
		
		Vec2f attackPosition = shape.getPosition().plus(shape.getDimensions().pmult(direction));
		CollidingShape attackShape = new AAB(attackPosition, Color.BLUE, Color.BLUE, new Vec2f(36.0f, 36.0f));
		Attack attack = new Attack(world, attackShape, "player_attack", attackProperties);
		return attack;
	}

	public Vec2f getPosition() {
		return shape.getPosition();
	}
	
	@Override
	public void onCollision(Collision collision) {
		super.onCollision(collision);
		if (collision.other instanceof Enemy) {
			enemyCollision(collision);
		}
	}
	
	@Override
	public void onCollisionDynamic(Collision collision) {
		super.onCollisionDynamic(collision);
		if (collision.other instanceof Enemy) {
			enemyCollision(collision);
		}
	}

	private void enemyCollision(Collision collision) {
		receiveDamage(1);
		
		Vec2f mtv = collision.mtv;
		Vec2f centerDistance = collision.thisShape.center().minus(
				collision.otherShape.center());
		if (mtv.dot(centerDistance) < 0) {
			mtv = mtv.smult(-1.0f);
		}
		Vec2f translation = mtv.normalized().pmult(shape.getDimensions()); 
		shape.move(translation);
	}
	
	@Override
	public void receiveDamage(int damage) {
		super.receiveDamage(damage);
		if (lives <= 0) {
			((FinalWorld)world).lose();
		}
	}

}
