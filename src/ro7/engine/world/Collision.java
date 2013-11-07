package ro7.engine.world;

import cs195n.Vec2f;
import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.entities.CollidableEntity;

public class Collision {

	public final CollidableEntity other;
	public final Vec2f mtv;
	public final CollidingShape thisShape;
	public final CollidingShape otherShape;

	public Collision(CollidableEntity other, Vec2f mtv, CollidingShape thisShape,
			CollidingShape otherShape) {
		this.other = other;
		this.mtv = mtv;
		this.thisShape = thisShape;
		this.otherShape = otherShape;
	}

	public boolean validCollision() {
		return mtv != null;
	}

}
