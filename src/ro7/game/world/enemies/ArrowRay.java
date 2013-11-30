package ro7.game.world.enemies;

import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.engine.world.RayCollision;
import ro7.game.world.entities.FinalEntity;
import ro7.game.world.entities.FinalRay;
import cs195n.Vec2f;

public class ArrowRay extends FinalRay implements FinalEntity {

	public ArrowRay(GameWorld world, int categoryMask, int collisionMask,
			Vec2f position, Vec2f direction) {
		super(world, categoryMask, collisionMask, position, direction);
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
