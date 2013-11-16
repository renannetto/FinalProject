package ro7.game.world;

import java.util.Map;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.engine.world.entities.CollidableEntity;

public class Attack extends CollidableEntity {

	private final float TIME_LIMIT = 0.2f;

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

		this.elapsedTime = 0.0f;
	}

	@Override
	public void onCollision(Collision collision) {
		Character character = (Character) collision.other;
		character.receiveDamage(damage);
	}

	@Override
	public void onCollisionDynamic(Collision collision) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCollisionStatic(Collision collision) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(long nanoseconds) {
		elapsedTime += nanoseconds / 1000000000.0f;
		if (elapsedTime > TIME_LIMIT) {
			world.removeEntity(name);
		}
	}

}