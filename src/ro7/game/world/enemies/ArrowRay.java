package ro7.game.world.enemies;

import cs195n.Vec2f;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.engine.world.RayCollision;
import ro7.engine.world.entities.Ray;

public class ArrowRay extends Ray {

	public ArrowRay(GameWorld world, int categoryMask, int collisionMask,
			Vec2f position, Vec2f direction) {
		super(world, categoryMask, collisionMask, position, direction);
		// TODO Auto-generated constructor stub
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

}
