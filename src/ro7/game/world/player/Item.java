package ro7.game.world.player;

import java.awt.Graphics2D;
import java.util.Set;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.engine.world.entities.Entity;
import ro7.game.world.FinalEntity;

public class Item extends Entity implements FinalEntity {
	
	public Item(GameWorld world, CollidingShape shape,
			String name) {
		super(world, shape, name);
	}
	
	@Override
	public void update(long nanoseconds) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove() {
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

	}

	@Override
	public void getItem(Item item) {
		// TODO Auto-generated method stub
		
	}

}
