package ro7.engine.world.components.colliders;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ro7.engine.world.Entity;
import ro7.engine.world.Ray;
import ro7.engine.world.components.Collider;
import cs195n.Vec2f;

public class Box extends EdgeShape {

	private Vec2f dimensions;

	public Box(Entity entity, Vec2f dimensions) {
		super(entity);
		this.dimensions = dimensions;
	}

	@Override
	public Vec2f collisionMtv(Collider shape) {
		Vec2f mtv = shape.collidesBox(this);
		return mtv;
	}

	@Override
	public Vec2f collidesCircle(Circle circle) {
		Vec2f center = circle.center();
		Vec2f minAAB = this.entity.transform.position.minus(dimensions
				.sdiv(2.0f));
		Vec2f maxAAB = minAAB.plus(dimensions);

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

		float radius = circle.getRadius();

		if (distance > (radius * radius)) {
			return new Vec2f(0.0f, 0.0f);
		}

		if (point.equals(center)) {
			return mtv(this.getAxes(), circle);
		}

		Vec2f circleCenter = circle.center();
		Vec2f dist = new Vec2f(Math.abs(point.x - circleCenter.x),
				Math.abs(point.y - circleCenter.y));
		Set<SeparatingAxis> axes = new HashSet<SeparatingAxis>();
		axes.add(new SeparatingAxis(dist));

		return mtv(axes, circle);
	}

	@Override
	public Vec2f collidesBox(Box aab) {
		Vec2f minThis = this.entity.transform.position.minus(dimensions
				.sdiv(2.0f));
		Vec2f maxThis = minThis.plus(this.dimensions);
		Vec2f minAAB = aab.entity.transform.position.minus(aab.dimensions
				.sdiv(2.0f));
		Vec2f maxAAB = minAAB.plus(aab.dimensions);

		if (!(minThis.x <= maxAAB.x && maxThis.x >= minAAB.x
				&& minThis.y <= maxAAB.y && maxThis.y >= minAAB.y)) {
			return new Vec2f(0.0f, 0.0f);
		}

		Set<SeparatingAxis> thisAxes = this.getAxes();

		return mtv(thisAxes, aab);
	}

	@Override
	public Vec2f collidesPolygon(Polygon polygon) {
		Set<SeparatingAxis> thisAxes = this.getAxes();
		Set<SeparatingAxis> thatAxes = polygon.getAxes();

		thisAxes.addAll(thatAxes);
		Vec2f shapeMtv = mtv(thisAxes, polygon);
		return shapeMtv;
	}

	@Override
	public Vec2f collidesCompoundShape(CompoundShape compound) {
		List<Collider> shapes = compound.getShapes();
		for (Collider shape : shapes) {
			Vec2f mtv = shape.collidesBox(this);
			if (mtv.mag2()!=0) {
				return mtv;
			}
		}
		return new Vec2f(0.0f, 0.0f);
	}

	/**
	 * Get the AAB separating axes (1, 0) and (0, 1).
	 * 
	 * @return
	 */
	public Set<SeparatingAxis> getAxes() {
		Set<SeparatingAxis> axes = new HashSet<SeparatingAxis>();
		axes.add(new SeparatingAxis(new Vec2f(1.0f, 0.0f)));
		axes.add(new SeparatingAxis(new Vec2f(0.0f, 1.0f)));
		return axes;
	}

	@Override
	public Vec2f collidesRay(Ray ray) {
		return ray.collidesAAB(this);
	}

	@Override
	public Vec2f getDimensions() {
		return dimensions;
	}

	@Override
	public Vec2f center() {
		return this.entity.transform.position;
	}

	@Override
	public List<Vec2f> getPoints() {
		Vec2f position = entity.transform.position;
		List<Vec2f> points = new ArrayList<Vec2f>();
		points.add(position.minus(dimensions.sdiv(2.0f)));
		points.add(position.plus(dimensions.x / 2.0f, -dimensions.y / 2.0f));
		points.add(position.plus(dimensions.sdiv(2.0f)));
		points.add(position.plus(-dimensions.x / 2.0f, dimensions.y / 2.0f));
		return points;
	}

}
