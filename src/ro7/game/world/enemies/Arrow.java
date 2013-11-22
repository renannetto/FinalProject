package ro7.game.world.enemies;

import java.util.Map;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.engine.world.entities.MovingEntity;
import ro7.game.world.FinalEntity;

public class Arrow extends MovingEntity implements FinalEntity {

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
		FinalEntity otherEntity = (FinalEntity) collision.other;
		otherEntity.receiveDamage(1);
		world.removeEntity(name);
	}

	@Override
	public void onCollisionStatic(Collision collision) {
		FinalEntity otherEntity = (FinalEntity) collision.other;
		otherEntity.receiveDamage(1);
		world.removeEntity(name);
	}

	@Override
	public void receiveDamage(int damage) {
		// TODO Auto-generated method stub

	}

	@Override
	public void touchEnemy(Collision collision) {
		// TODO Auto-generated method stub

	}

	@Override
	public void receiveAttack(Collision collision) {
		// TODO Auto-generated method stub

	}

	@Override
	public void receiveAction() {
		// TODO Auto-generated method stub
		
	}

}
