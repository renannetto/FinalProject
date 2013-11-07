package ro7.engine.world;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.entities.CollidableEntity;
import cs195n.Vec2f;

public class RayCollision {
	
	public final CollidableEntity other;
	public final Vec2f point;
	public final CollidingShape otherShape;
	
	public RayCollision(CollidableEntity other, Vec2f point,
			CollidingShape otherShape) {
		this.other = other;
		this.point = point;
		this.otherShape = otherShape;
	}

	public boolean validCollision() {
		return point != null;
	}

}
