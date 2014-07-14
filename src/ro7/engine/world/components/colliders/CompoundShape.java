package ro7.engine.world.components.colliders;

import java.util.ArrayList;
import java.util.List;

import ro7.engine.world.Entity;
import ro7.engine.world.Ray;
import ro7.engine.world.components.Collider;
import cs195n.Vec2f;

public class CompoundShape extends Collider {

	private List<Collider> shapes;

	public CompoundShape(Entity entity, Collider... shapes) {
		super(entity);

		this.shapes = new ArrayList<Collider>();
		for (Collider shape : shapes) {
			this.shapes.add(shape);
		}
	}
	
	public CompoundShape(Entity entity, List<Collider> shapes) {
		super(entity);
		this.shapes = shapes;
	}

	public List<Collider> getShapes() {
		return new ArrayList<Collider>(shapes);
	}

	@Override
	public Vec2f collisionMtv(Collider shape) {
		Vec2f mtv = shape.collidesCompoundShape(this);
		return mtv;
	}

	@Override
	public Vec2f collidesCircle(Circle circle) {
		for (Collider shape : shapes) {
			Vec2f mtv = shape.collidesCircle(circle);
			if (mtv.mag2()!=0) {
				return mtv;
			}
		}
		return new Vec2f(0.0f, 0.0f);
	}

	@Override
	public Vec2f collidesBox(Box aab) {
		for (Collider shape : shapes) {
			Vec2f mtv = shape.collidesBox(aab);
			if (mtv.mag2()!=0) {
				return mtv;
			}
		}
		return new Vec2f(0.0f, 0.0f);
	}

	@Override
	public Vec2f collidesPolygon(Polygon polygon) {
		for (Collider shape : shapes) {
			Vec2f mtv = shape.collidesPolygon(polygon);
			if (mtv.mag2()!=0) {
				return mtv;
			}
		}
		return new Vec2f(0.0f, 0.0f);
	}

	@Override
	public Vec2f collidesCompoundShape(CompoundShape compound) {
		for (Collider shape : shapes) {
			Vec2f mtv = shape.collidesCompoundShape(compound);
			if (mtv.mag2()!=0) {
				return mtv;
			}
		}
		return new Vec2f(0.0f, 0.0f);
	}

	@Override
	public Vec2f collidesRay(Ray ray) {
		Vec2f closest = null;
		for (Collider shape : shapes) {
			Vec2f point = shape.collidesRay(ray);
			if (closest == null) {
				closest = point;
			} else {
				if (point != null) {
					float distance = ray.dist2(point);
					if (distance < ray.dist2(closest)) {
						closest = point;
					}
				}
			}
		}
		return closest;
	}

	@Override
	public Vec2f center() {
		float xCenter = 0;
		float yCenter = 0;
		List<Vec2f> points = getPoints();
		int npoints = points.size();
		for (Vec2f point : points) {
			xCenter += point.x;
			yCenter += point.y;
		}
		return new Vec2f(xCenter/npoints, yCenter/npoints);
	}

	@Override
	public List<Vec2f> getPoints() {
		List<Vec2f> points = new ArrayList<Vec2f>();
		for (Collider shape : shapes) {
			points.addAll(shape.getPoints());
		}
		return points;
	}
	
	@Override
	public Vec2f getDimensions() {
		float minX = Float.MAX_VALUE;
		float maxX = -Float.MAX_VALUE;
		float minY = Float.MAX_VALUE;
		float maxY = -Float.MAX_VALUE;
		List<Vec2f> points = getPoints();
		for (Vec2f point : points) {
			if (point.x < minX) {
				minX = point.x;
			}
			if (point.x > maxX) {
				maxX = point.x;
			}
			if (point.y < minY) {
				minY = point.y;
			}
			if (point.y > maxY) {
				maxY = point.y;
			}
		}
		return new Vec2f(maxX-minX, maxY-minY);
	}

}
