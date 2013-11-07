package ro7.engine.sprites.shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ro7.engine.world.entities.Ray;
import cs195n.Vec2f;

public class Polygon extends EdgeShape {

	public Polygon(Vec2f position, Color fillColor, Vec2f... points) {
		super(position, fillColor, fillColor, points);
	}
	
	public Polygon(Vec2f position, Color fillColor, List<Vec2f> points) {
		super(position, fillColor, fillColor, points);
	}

	@Override
	public Vec2f collides(CollidingShape shape) {
		return shape.collidesPolygon(this);
	}

	@Override
	public Vec2f collidesCircle(Circle circle) {
		Set<SeparatingAxis> thisAxes = this.getAxes();
		Set<SeparatingAxis> thatAxes = circle.getAxes(this);
		
		thisAxes.addAll(thatAxes);
		Vec2f shapeMtv = mtv(thisAxes, circle);
		if (shapeMtv != null) {	
			Vec2f centerDistance = circle.center().minus(this.center());
			if (shapeMtv.dot(centerDistance) < 0) {
				shapeMtv = shapeMtv.smult(-1.0f);
			}
		}
		return shapeMtv;
	}

	@Override
	public Vec2f collidesAAB(AAB aab) {
		Set<SeparatingAxis> thisAxes = this.getAxes();
		Set<SeparatingAxis> thatAxes = aab.getAxes();
		
		thisAxes.addAll(thatAxes);
		Vec2f shapeMtv = mtv(thisAxes, aab);
		if (shapeMtv != null) {	
			Vec2f centerDistance = aab.center().minus(this.center());
			if (shapeMtv.dot(centerDistance) < 0) {
				shapeMtv = shapeMtv.smult(-1.0f);
			}
		}
		return shapeMtv;
	}
	
	@Override
	public Vec2f collidesPolygon(Polygon polygon) {
		Set<SeparatingAxis> thisAxes = this.getAxes();
		Set<SeparatingAxis> thatAxes = polygon.getAxes();
		
		thisAxes.addAll(thatAxes);
		Vec2f shapeMtv = mtv(thisAxes, polygon);
		if (shapeMtv != null) {	
			Vec2f centerDistance = polygon.center().minus(this.center());
			if (shapeMtv.dot(centerDistance) < 0) {
				shapeMtv = shapeMtv.smult(-1.0f);
			}
		}
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
	public Range projectTo(SeparatingAxis axis) {
		return axis.project(this);
	}

	@Override
	public Vec2f collidesCompoundShape(CompoundShape compound) {
		List<CollidingShape> shapes = compound.getShapes();
		for (CollidingShape shape : shapes) {
			Vec2f mtv = shape.collidesPolygon(this);
			if (mtv != null) {
				return mtv;
			}
		}
		return null;
	}
	
	@Override
	public Vec2f collidesRay(Ray ray) {
		return ray.collidesPolygon(this);
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(fillColor);
		Path2D path = new Path2D.Float();
		path.moveTo(points.get(0).x, points.get(0).y);
		for (Vec2f point : points) {
			path.lineTo(point.x, point.y);
		}
		g.fill(path);
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
	public void updatePoints(Vec2f translation) {
		List<Vec2f> newPoints = new ArrayList<Vec2f>();
		for (Vec2f point : points) {
			newPoints.add(point.plus(translation));
		}
		points = newPoints;
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
