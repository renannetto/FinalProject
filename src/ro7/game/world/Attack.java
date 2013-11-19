package ro7.game.world;

import java.util.Map;

import cs195n.Vec2f;
import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.engine.world.entities.CollidableEntity;
import ro7.engine.world.io.Output;

public class Attack extends CollidableEntity {

	private final float TIME_LIMIT = 0.1f;

	private int damage;
	private float elapsedTime;

	public Attack(GameWorld world, CollidingShape shape, String name,
			Map<String, String> properties) {
		super(world, shape, name, properties);
		if (properties.containsKey("damage")) {
			this.damage = Integer.parseInt(properties.get("damage"));
		} else {
			this.damage = 1;
		}
		
		outputs.put("onFinish", new Output());

		this.elapsedTime = 0.0f;
	}

	@Override
	public void onCollision(Collision collision) {
		try {
			Character character = (Character) collision.other;
			character.receiveDamage(damage);
			Vec2f mtv = collision.mtv;
			Vec2f centerDistance = collision.otherShape.center().minus(
					collision.thisShape.center());
			if (mtv.dot(centerDistance) < 0) {
				mtv = mtv.smult(-1.0f);
			}
			character.push(mtv);
		} catch (Exception e) {

		}
	}

	@Override
	public void onCollisionDynamic(Collision collision) {

	}

	@Override
	public void onCollisionStatic(Collision collision) {

	}

	@Override
	public void update(long nanoseconds) {
		elapsedTime += nanoseconds / 1000000000.0f;
		if (elapsedTime > TIME_LIMIT) {
			world.removeEntity(name);
			outputs.get("onFinish").run();
		}
	}

	public void moveTo(Vec2f position) {
		shape.moveTo(position);
	}

}
