package ro7.engine.world.entities;

import java.util.Map;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import cs195n.Vec2f;

public abstract class StaticEntity extends PhysicalEntity {

	protected StaticEntity(GameWorld world, CollidingShape shape, String name,
			Map<String, String> properties) {
		super(world, shape, name, properties);
	}

	@Override
	public void applyForce(Vec2f force) {

	}

	@Override
	public void onCollision(Collision collision) {
		assert collision.validCollision();
		Vec2f mtv = collision.mtv;
		Vec2f centerDistance = collision.thisShape.center().minus(
				collision.otherShape.center());
		if (mtv.dot(centerDistance) < 0) {
			mtv = mtv.smult(-1.0f);
		}

		try {
			PhysicalEntity other = (PhysicalEntity) collision.other;
			other.onCollisionStatic(new Collision(this, mtv.smult(-1.0f),
					other.shape, this.shape));
		} catch (Exception e) {
		}
	}

	@Override
	public void onCollisionDynamic(Collision collision) {
		Vec2f mtv = collision.mtv;
		if (mtv.mag2() == 0) {
			return;
		}

		PhysicalEntity other = (PhysicalEntity) collision.other;
		other.shape.move(mtv.smult(-1.0f));

		mtv = mtv.normalized();

		float cor = this.cor(other);
		float ua = other.velocity.dot(mtv);
		float ub = this.velocity.dot(mtv);
		float ma = other.mass;

		float impulse = ma * (1 + cor) * (ub - ua);

		other.applyImpulse(mtv.smult(impulse).sdiv(2.0f));
	}

	@Override
	public void onCollisionStatic(Collision collision) {

	}

}
