package ro7.game.world.enemies;

import java.util.Map;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.game.world.entities.FinalEntity;
import ro7.game.world.entities.FinalMovingEntity;

public class Arrow extends FinalMovingEntity implements FinalEntity {

	public Arrow(GameWorld world, CollidingShape shape, String name,
			Map<String, String> properties) {
		super(world, shape, name, properties);
	}

	@Override
	public void onCollision(Collision collision) {
		FinalEntity otherEntity = (FinalEntity) collision.other;
		otherEntity.receiveDamage(1);
		world.removeEntity(name);
	}

	@Override
	public void onCollisionDynamic(Collision collision) {

	}

	@Override
	public void onCollisionStatic(Collision collision) {

	}

}
