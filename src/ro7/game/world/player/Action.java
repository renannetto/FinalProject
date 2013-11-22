package ro7.game.world.player;

import java.awt.Graphics2D;
import java.util.Map;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.engine.world.entities.CollidableEntity;
import ro7.game.world.FinalEntity;

public class Action extends CollidableEntity implements FinalEntity {
	
	private final float ACTION_TIME = 0.1f;
	
	private float elapsedTime;

	public Action(GameWorld world, CollidingShape shape, String name,
			Map<String, String> properties) {
		super(world, shape, name, properties);
		
		elapsedTime = 0.0f;
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
	public void onCollision(Collision collision) {
		FinalEntity otherEntity = (FinalEntity) collision.other;
		otherEntity.receiveAction();
		world.removeEntity(name);
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
	public void draw(Graphics2D g) {
		
	}

	@Override
	public void update(long nanoseconds) {
		elapsedTime += nanoseconds / 1000000000.0f;
		if (elapsedTime > ACTION_TIME) {
			world.removeEntity(name);
		}
	}

	@Override
	public void receiveAction() {
		// TODO Auto-generated method stub
		
	}

}
