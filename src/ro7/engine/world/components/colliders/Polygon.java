package ro7.engine.world.components.colliders;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ro7.engine.world.Entity;
import ro7.engine.world.Ray;
import ro7.engine.world.components.Collider;
import cs195n.Vec2f;

public class Polygon extends EdgeShape {
	
	private List<Vec2f> points;

	public Polygon(Entity entity, Vec2f... points) {
		super(entity);
		this.points = new ArrayList<Vec2f>();
		for (Vec2f point : points) {
			this.points.add(point);
		}
	}
	
	public Polygon(Entity entity, List<Vec2f> points) {
		super(entity);
		this.points = new ArrayList<Vec2f>();
		this.points.addAll(points);
	}

	@Override
	public Vec2f collisionMtv(Collider shape) {
		Vec2f mtv = shape.collidesPolygon(this);
		return mtv;
	}

	@Override
	public Vec2f collidesCircle(Circle circle) {
		Set<SeparatingAxis> thisAxes = this.getAxes();
		Set<SeparatingAxis> thatAxes = circle.getAxes(this);
		
		thisAxes.addAll(thatAxes);
		Vec2f shapeMtv = mtv(thisAxes, circle);
		return shapeMtv;
	}

	@Override
	public Vec2f collidesBox(Box aab) {
		Set<SeparatingAxis> thisAxes = this.getAxes();
		Set<SeparatingAxis> thatAxes = aab.getAxes();
		
		thisAxes.addAll(thatAxes);
		Vec2f shapeMtv = mtv(thisAxes, aab);
		return shapeMtv;
	}
	
	@Override
	public Vec2f collidesPolygon(Polygon polygon) {
		Set<SeparatingAxis> thisAxes = this.getAxes();
		Set<SeparatingAxis> thatAxes = polygon.getAxes();
		
		thisAxes.addAll(thatAxes);
		Vec2f shapeMtv = mtv(thisAxes, polygon);
		return shapeMtv;
	}
	
	/**
	 * @return
	 * Get the set of separating axes of the polygon
	 * by taking the vectors perpendicular to the edges
	 */
	public Set<SeparatingAxis> getAxes() {
		Set<SeparatingAxis> axes = new HashSet<SeparatingAxis>();
		for (int i=0; i<points.size(); i++) {
			Vec2f startPoint = points.get(i);
			Vec2f endPoint;
			if (i<points.size()-1) {
				endPoint = points.get(i+1);
			} else {
				endPoint = points.get(0);
			}
			Vec2f edgeVector = endPoint.minus(startPoint);
			SeparatingAxis axis = new SeparatingAxis(new Vec2f(edgeVector.y, -edgeVector.x));
			axes.add(axis);
		}
		return axes;
	}

	@Override
	public Vec2f collidesCompoundShape(CompoundShape compound) {
		List<Collider> shapes = compound.getShapes();
		for (Collider shape : shapes) {
			Vec2f mtv = shape.collidesPolygon(this);
			if (mtv.mag2()!=0) {
				return mtv;
			}
		}
		return new Vec2f(0.0f, 0.0f);
	}
	
	@Override
	public Vec2f collidesRay(Ray ray) {
		return ray.collidesPolygon(this);
	}

	@Override
	public Vec2f center() {
		float xCenter = 0;
		float yCenter = 0;
		int npoints = points.size();
		for (Vec2f point : points) {
			xCenter += point.x;
			yCenter += point.y;
		}
		return new Vec2f(xCenter/npoints, yCenter/npoints);
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

	@Override
	public List<Vec2f> getPoints() {
		Vec2f position = entity.transform.position;
		List<Vec2f> translatedPoints = new ArrayList<Vec2f>();
		for (Vec2f point : points) {
			translatedPoints.add(point.plus(position));
		}
		return translatedPoints;
	}

}
