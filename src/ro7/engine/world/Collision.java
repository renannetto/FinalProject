package ro7.engine.world;

import cs195n.Vec2f;

public class Collision {
	
	public final Entity thisEntity;
	public final Entity otherEntity;
	public final Vec2f mtv;

	public Collision(Entity thisEntity, Entity otherEntity, Vec2f mtv) {
		this.thisEntity = thisEntity;
		this.otherEntity = otherEntity;
		this.mtv = mtv;
	}

	public boolean validCollision() {
		return mtv.mag2()!=0.0f;
	}

}
