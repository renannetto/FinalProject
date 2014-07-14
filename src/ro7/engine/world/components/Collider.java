package ro7.engine.world.components;

import java.util.List;
import java.util.Set;

import ro7.engine.world.Collision;
import ro7.engine.world.Entity;
import ro7.engine.world.Ray;
import ro7.engine.world.components.colliders.Box;
import ro7.engine.world.components.colliders.Circle;
import ro7.engine.world.components.colliders.CompoundShape;
import ro7.engine.world.components.colliders.Polygon;
import ro7.engine.world.components.colliders.Range;
import ro7.engine.world.components.colliders.SeparatingAxis;
import cs195n.Vec2f;

public abstract class Collider extends Component {

	protected Collider(Entity entity) {
		super(entity);
		
		entity.world.addCollider(this);
	}

	public Collision collides(Collider shape) {
		Vec2f mtv = collisionMtv(shape);
		return new Collision(entity, shape.entity, mtv);
	}
	
	public abstract Vec2f collisionMtv(Collider shape);

	public abstract Vec2f collidesCircle(Circle circle);

	public abstract Vec2f collidesBox(Box aab);

	public abstract Vec2f collidesPolygon(Polygon polygon);

	public abstract Vec2f collidesCompoundShape(CompoundShape compound);

	public abstract Vec2f collidesRay(Ray ray);

	public Vec2f getPosition() {
		return entity.transform.position;
	}

	/**
	 * @param axes
	 * @param shape
	 * Find the mtv of this and shape on the set of separating axes.
	 * @return a vector representing the mtv, or null if there is no
	 * collision
	 */
	public Vec2f mtv(Set<SeparatingAxis> axes, Collider shape) {
		float minMagnitude = Float.MAX_VALUE;
		Vec2f mtv = new Vec2f(0.0f, 0.0f);
		for (SeparatingAxis axis : axes) {
			axis = axis.normalized();
			Range range1 = axis.project(this);
			Range range2 = axis.project(shape);
			if (!range1.overlaps(range2)) {
				return new Vec2f(0.0f, 0.0f);
			} else {
				float mtv1d = range1.mtv(range2);
				if (mtv1d==0) {
					return new Vec2f(0.0f, 0.0f);
				}
				if (Math.abs(mtv1d) < minMagnitude) {
					minMagnitude = Math.abs(mtv1d);
					mtv = axis.smult(mtv1d);
				}
			}
		}
		return mtv;
	}

	public abstract Vec2f center();

	public abstract List<Vec2f> getPoints();
	
	@Override
	public void update(long nanoseconds) {
		
	}
	
	public abstract Vec2f getDimensions();

}
