package ro7.game.world.entities;

import java.util.Set;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.engine.world.entities.Entity;
import ro7.game.world.player.Item;

public abstract class FinalEntityImp extends Entity implements FinalEntity {

	protected FinalEntityImp(GameWorld world, CollidingShape shape, String name) {
		super(world, shape, name);
		// TODO Auto-generated constructor stub
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
