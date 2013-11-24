package ro7.game.world.enemies;

import java.util.Set;

import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.engine.world.RayCollision;
import ro7.engine.world.entities.Ray;
import ro7.game.world.FinalEntity;
import ro7.game.world.player.Item;
import cs195n.Vec2f;

public class ArrowRay extends Ray implements FinalEntity {

	public ArrowRay(GameWorld world, int categoryMask, int collisionMask,
			Vec2f position, Vec2f direction) {
		super(world, categoryMask, collisionMask, position, direction);
	}

	@Override
	public void onCollision(RayCollision collision) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCollision(Collision collision) {
		// TODO Auto-generated method stub
		
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
	public void receiveAction(Collision collision, Set<Item> inventory) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getItem(Item item) {
		// TODO Auto-generated method stub
		
	}

}
