package ro7.game.world.scenario;

import java.awt.Graphics2D;
import java.util.Map;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.GameWorld;
import ro7.game.world.entities.FinalEntity;
import ro7.game.world.entities.FinalStaticEntity;

public class Wall extends FinalStaticEntity implements FinalEntity {

	public Wall(GameWorld world, CollidingShape shape, String name,
			Map<String, String> properties) {
		super(world, shape, name, properties);
	}
	
	@Override
	public void draw(Graphics2D g) {
		
	}

}
