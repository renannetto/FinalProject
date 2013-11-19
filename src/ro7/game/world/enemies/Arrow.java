package ro7.game.world.enemies;

import java.util.Map;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.engine.world.entities.MovingEntity;
import ro7.game.world.Character;

public class Arrow extends MovingEntity {

	public Arrow(GameWorld world, CollidingShape shape, String name,
			Map<String, String> properties) {
		super(world, shape, name, properties);
	}

	@Override
	public void onCollision(Collision collision) {
		try {
			Character character = (Character) collision.other;
			character.receiveDamage(1);
		} catch (Exception e) {
			
		}
		world.removeEntity(name);
	}

	@Override
	public void onCollisionDynamic(Collision collision) {

	}

	@Override
	public void onCollisionStatic(Collision collision) {

	}

}
