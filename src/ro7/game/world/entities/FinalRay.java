package ro7.game.world.entities;

import java.util.Set;

import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.engine.world.entities.Ray;
import ro7.game.world.items.Item;
import cs195n.Vec2f;

public abstract class FinalRay extends Ray implements FinalEntity {
	
	protected FinalRay(GameWorld world, int categoryMask, int collisionMask,
			Vec2f position, Vec2f direction) {
		super(world, categoryMask, collisionMask, position, direction);
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

	@Override
	public void fall(Collision collision) {
		// TODO Auto-generated method stub
		
	}

}
