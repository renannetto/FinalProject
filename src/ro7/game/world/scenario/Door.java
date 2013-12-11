package ro7.game.world.scenario;

import java.awt.Graphics2D;
import java.util.Map;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.engine.world.io.Output;
import ro7.game.world.FinalWorld;
import ro7.game.world.entities.FinalCollidableEntity;
import ro7.game.world.entities.FinalEntity;
import cs195n.Vec2f;

public class Door extends FinalCollidableEntity implements FinalEntity {
	
	private String nextRoom;
	private Vec2f nextPosition;

	public Door(GameWorld world, CollidingShape shape, String name,
			Map<String, String> properties) {
		super(world, shape, name, properties);
		
		nextRoom = properties.get("nextRoom");
		
		if (properties.containsKey("nextPosX") && properties.containsKey("nextPosY")) {
			nextPosition = new Vec2f(Float.parseFloat(properties.get("nextPosX")), Float.parseFloat(properties.get("nextPosY")));
		} else {
			nextPosition = null;
		}
		
		outputs.put("onCollision", new Output());
	}
	
	@Override
	public void onCollision(Collision collision) {
		outputs.get("onCollision").run();
		enter();
	}

	@Override
	public void onCollisionDynamic(Collision collision) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCollisionStatic(Collision collision) {
		// TODO Auto-generated method stub

	}

	public void enter() {
		world.loadLevel(nextRoom);
		if (nextPosition!=null) {
			((FinalWorld)world).setInitialPosition(nextPosition);
		}
	}

	@Override
	public void update(long nanoseconds) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void draw(Graphics2D g) {
		
	}

}
