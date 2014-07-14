package ro7.engine.world;

import ro7.engine.world.components.Collider;
import ro7.engine.world.components.PhysicsComponent;
import cs195n.Vec2f;

public class RayCollision {
	
	public final PhysicsComponent other;
	public final Vec2f point;
	public final Collider otherShape;
	
	public RayCollision(PhysicsComponent other, Vec2f point,
			Collider otherShape) {
		this.other = other;
		this.point = point;
		this.otherShape = otherShape;
	}

	public boolean validCollision() {
		return point != null;
	}

}
