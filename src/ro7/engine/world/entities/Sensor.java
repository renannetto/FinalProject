package ro7.engine.world.entities;

import java.awt.Graphics2D;
import java.util.Map;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.engine.world.io.Output;

public class Sensor extends CollidableEntity {

	public Sensor(GameWorld world, CollidingShape shape, String name, Map<String, String> properties) {
		super(world, shape, name, properties);
		outputs.put("onCollision", new Output());
	}

	/* (non-Javadoc)
	 * @see ro7.engine.world.entities.CollidableEntity#onCollision(ro7.engine.world.Collision)
	 * Call its ouptut when a collision happens.
	 */
	@Override
	public void onCollision(Collision collision) {
		outputs.get("onCollision").run();
	}

	@Override
	public void update(long nanoseconds) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void draw(Graphics2D g) {
		
	}

}
