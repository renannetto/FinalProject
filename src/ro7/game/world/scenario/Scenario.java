package ro7.game.world.scenario;

import java.awt.Graphics2D;
import java.util.Map;
import java.util.Set;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.engine.world.entities.Entity;
import ro7.game.world.FinalEntity;
import ro7.game.world.player.Item;

public class Scenario extends Entity implements FinalEntity {

	public Scenario(GameWorld world, CollidingShape shape, String name, Map<String, String> properties) {
		super(world, shape, name);
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
	public void update(long nanoseconds) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics2D g) {
		shape.draw(g);
	}

	@Override
	public void remove() {
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
