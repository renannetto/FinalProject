package ro7.engine.sprites.shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ro7.engine.world.entities.Ray;
import cs195n.Vec2f;

public class AAB extends EdgeShape {

	private Vec2f dimensions;

	public AAB(Vec2f position, Color borderColor, Color fillColor,
			Vec2f dimensions) {
		super(position, borderColor, fillColor, position.minus(dimensions.sdiv(2.0f)), position.plus(dimensions.x/2, -dimensions.y/2), 
				position.plus(dimensions.x/2, dimensions.y/2), position.plus(-dimensions.x/2,dimensions.y/2));
		this.dimensions = dimensions;
	}

	@Override
	public Vec2f collides(CollidingShape shape) {
		return shape.collidesAAB(this);
	}

	@Override
	public Vec2f collidesCircle(Circle circle) {
		Vec2f center = circle.center();
		Vec2f minAAB = this.position.minus(dimensions.sdiv(2.0f));
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
			return null;
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
	public Vec2f collidesAAB(AAB aab) {
		Vec2f minThis = this.position.minus(dimensions.sdiv(2.0f));
		Vec2f maxThis = minThis.plus(this.dimensions);
		Vec2f minAAB = aab.position.minus(aab.dimensions.sdiv(2.0f));
		Vec2f maxAAB = minAAB.plus(aab.dimensions);

		if (!(minThis.x <= maxAAB.x && maxThis.x >= minAAB.x
				&& minThis.y <= maxAAB.y && maxThis.y >= minAAB.y)) {
			return null;
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
		if (shapeMtv != null) {
			Vec2f centerDistance = polygon.center().minus(this.center());
			if (shapeMtv.dot(centerDistance) < 0) {
				shapeMtv = shapeMtv.smult(-1.0f);
			}
		}
		return shapeMtv;
	}

	@Override
	public Vec2f collidesCompoundShape(CompoundShape compound) {
		List<CollidingShape> shapes = compound.getShapes();
		for (CollidingShape shape : shapes) {
			Vec2f mtv = shape.collidesAAB(this);
			if (mtv != null) {
				return mtv;
			}
		}
		return null;
	}

	public Set<SeparatingAxis> getAxes() {
		Set<SeparatingAxis> axes = new HashSet<SeparatingAxis>();
		axes.add(new SeparatingAxis(new Vec2f(1.0f, 0.0f)));
		axes.add(new SeparatingAxis(new Vec2f(0.0f, 1.0f)));
		return axes;
	}

	@Override
	public Range projectTo(SeparatingAxis axis) {
		return axis.project(this);
	}
	
	@Override
	public Vec2f collidesRay(Ray ray) {
		return ray.collidesAAB(this);
	}

	@Override
	public void draw(Graphics2D g) {
		Rectangle2D rectangle = new Rectangle2D.Float(position.x-dimensions.x/2, position.y-dimensions.y/2,
				dimensions.x, dimensions.y);

		g.setColor(borderColor);
		g.draw(rectangle);

		if (fillColor != null) {
			g.setColor(fillColor);
			g.fill(rectangle);
		}
	}

	@Override
	public Vec2f getDimensions() {
		return dimensions;
	}

	public Shape getShape() {
		return new Rectangle2D.Float(position.x-dimensions.x/2, position.y-dimensions.y/2, dimensions.x,
				dimensions.y);
	}

	@Override
	public Vec2f center() {
		return position;
	}

	@Override
	public void updatePoints(Vec2f translation) {
		this.points = new ArrayList<Vec2f>();
		points.add(position.minus(dimensions.sdiv(2.0f)));
		points.add(position.plus(dimensions.x/2, -dimensions.y/2));
		points.add(position.plus(dimensions.x/2, dimensions.y/2));
		points.add(position.plus(-dimensions.x/2,dimensions.y/2));
	}

}
