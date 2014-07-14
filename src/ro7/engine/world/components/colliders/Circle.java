package ro7.engine.world.components.colliders;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ro7.engine.world.Entity;
import ro7.engine.world.Ray;
import ro7.engine.world.components.Collider;
import cs195n.Vec2f;

/**
 * @author ro7 Sprite that represents a colored circle
 */
public class Circle extends Collider {

	private float radius;

	public Circle(Entity entity, float radius) {
		super(entity);
		this.radius = radius;
	}

	@Override
	public Vec2f center() {
		return entity.transform.position;
	}

	public float getRadius() {
		return radius;
	}

	@Override
	public Vec2f collisionMtv(Collider shape) {
		Vec2f mtv = shape.collidesCircle(this);
		return mtv;
	}

	@Override
	public Vec2f collidesCircle(Circle circle) {
		float distance = this.center().dist2(circle.center());
		if (distance > (this.radius + circle.radius)
				* (this.radius + circle.radius)) {
			return new Vec2f(0.0f, 0.0f);
		}

		Set<SeparatingAxis> axes = new HashSet<SeparatingAxis>();
		axes.add(new SeparatingAxis(this.center().minus(circle.center())));

		Vec2f shapeMtv = mtv(axes, circle);
		if (shapeMtv != null) {
			Vec2f centerDistance = circle.center().minus(this.center());
			if (shapeMtv.dot(centerDistance) < 0) {
				shapeMtv = shapeMtv.smult(-1.0f);
			}
		}
		return shapeMtv;
	}

	@Override
	public Vec2f collidesBox(Box aab) {
		Vec2f center = this.center();
		Vec2f aabDimensions = aab.getDimensions();
		Vec2f minAAB = aab.getPosition().minus(aabDimensions.sdiv(2.0f));
		Vec2f maxAAB = minAAB.plus(aab.getDimensions());

		float pointx;
		float pointy;
		if (center.x < minAAB.x) {
			pointx = minAAB.x;
		} else if (center.x > maxAAB.x) {
			pointx = maxAAB.x;
		} else {
			pointx = center.x;
		}

		if (center.y < minAAB.y) {
			pointy = minAAB.y;
		} else if (center.y > maxAAB.y) {
			pointy = maxAAB.y;
		} else {
			pointy = center.y;
		}

		Vec2f point = new Vec2f(pointx, pointy);
		float distance = point.dist2(center);

		if (distance > (radius * radius)) {
			return new Vec2f(0.0f, 0.0f);
		}

		if (point.equals(center)) {
			return mtv(aab.getAxes(), aab);
		}

		Vec2f circleCenter = this.center();
		Vec2f dist = new Vec2f(Math.abs(point.x - circleCenter.x),
				Math.abs(point.y - circleCenter.y));
		Set<SeparatingAxis> axes = new HashSet<SeparatingAxis>();
		axes.add(new SeparatingAxis(dist));

		return mtv(axes, aab);
	}

	@Override
	public Vec2f collidesPolygon(Polygon polygon) {
		Set<SeparatingAxis> thisAxes = this.getAxes(polygon);
		Set<SeparatingAxis> thatAxes = polygon.getAxes();

		thisAxes.addAll(thatAxes);
		Vec2f shapeMtv = mtv(thisAxes, polygon);
		return shapeMtv;
	}

	@Override
	public Vec2f collidesCompoundShape(CompoundShape compound) {
		List<Collider> shapes = compound.getShapes();
		for (Collider shape : shapes) {
			Vec2f mtv = shape.collidesCircle(this);
			if (mtv.mag2()!=0) {
				return mtv;
			}
		}
		return new Vec2f(0.0f, 0.0f);
	}

	/**
	 * @param polygon
	 *            Get the SeparatingAxis of the circle with a polygon
	 * @return
	 */
	public Set<SeparatingAxis> getAxes(Polygon polygon) {
		Set<SeparatingAxis> axes = new HashSet<SeparatingAxis>();

		List<Vec2f> points = polygon.getPoints();
		Vec2f center = this.center();
		Vec2f closest = new Vec2f(Float.MAX_VALUE, Float.MAX_VALUE);
		for (Vec2f point : points) {
			Vec2f distance = point.minus(center);
			if (distance.mag2() < closest.mag2()) {
				closest = distance;
			}
		}

		SeparatingAxis axis = new SeparatingAxis(closest);
		axes.add(axis);

		return axes;
	}

	@Override
	public Vec2f collidesRay(Ray ray) {
		return ray.collidesCircle(this);
	}

	@Override
	public List<Vec2f> getPoints() {
		Vec2f position = entity.transform.position.minus(radius, radius);
		List<Vec2f> points = new ArrayList<Vec2f>();
		points.add(position);
		points.add(position.plus(0.0f, 2 * radius));
		points.add(position.plus(2 * radius, 2 * radius));
		points.add(position.plus(2 * radius, 0.0f));
		return points;
	}

	public boolean inside(Vec2f position) {
		float distance = position.dist2(this.center());
		return distance < (radius * radius);
	}

	@Override
	public Vec2f getDimensions() {
		return new Vec2f(2 * radius, 2 * radius);
	}

}
