package ro7.game.world.items;

import java.awt.Graphics2D;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.GameWorld;
import ro7.game.world.entities.FinalEntity;
import ro7.game.world.entities.FinalEntityImp;

public class Item extends FinalEntityImp implements FinalEntity {
	
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

}
