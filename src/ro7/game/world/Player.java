package ro7.game.world;

import java.util.Map;

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
