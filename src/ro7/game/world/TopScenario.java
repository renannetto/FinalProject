package ro7.game.world;

import java.awt.Graphics2D;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.GameWorld;
import ro7.engine.world.entities.Entity;

public class TopScenario extends Entity {

	protected TopScenario(GameWorld world, CollidingShape shape, String name) {
		super(world, shape, name);
		// TODO Auto-generated constructor stub
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

}
