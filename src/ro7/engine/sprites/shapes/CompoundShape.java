package ro7.engine.sprites.shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import ro7.engine.world.entities.Ray;
import cs195n.Vec2f;

public class CompoundShape extends CollidingShape {

	private List<CollidingShape> shapes;

	public CompoundShape(Vec2f position, CollidingShape... shapes) {
		super(position);

		this.shapes = new ArrayList<CollidingShape>();
		for (CollidingShape shape : shapes) {
			this.shapes.add(shape);
		}
	}
	
	public CompoundShape(Vec2f position, List<CollidingShape> shapes) {
		super(position);
		this.shapes = shapes;
	}

	public List<CollidingShape> getShapes() {
		return new ArrayList<CollidingShape>(shapes);
	}

	@Override
	public Vec2f collides(CollidingShape shape) {
		return shape.collidesCompoundShape(this);
	}

	@Override
	public Vec2f collidesCircle(Circle circle) {
		for (CollidingShape shape : shapes) {
			Vec2f mtv = shape.collidesCircle(circle);
			if (mtv != null) {
				return mtv;
			}
		}
		return null;
	}

	@Override
	public Vec2f collidesAAB(AAB aab) {
		for (CollidingShape shape : shapes) {
			Vec2f mtv = shape.collidesAAB(aab);
			if (mtv != null) {
				return mtv;
			}
		}
		return null;
	}

	@Override
	public Vec2f collidesPolygon(Polygon polygon) {
		for (CollidingShape shape : shapes) {
			Vec2f mtv = shape.collidesPolygon(polygon);
			if (mtv != null) {
				return mtv;
			}
		}
		return null;
	}

	@Override
	public Vec2f collidesCompoundShape(CompoundShape compound) {
		for (CollidingShape shape : shapes) {
			Vec2f mtv = shape.collidesCompoundShape(compound);
			if (mtv != null) {
				return mtv;
			}
		}
		return null;
	}

	@Override
	public Vec2f collidesRay(Ray ray) {
		Vec2f closest = null;
		for (CollidingShape shape : shapes) {
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
	public void draw(Graphics2D g) {
		for (CollidingShape shape : shapes) {
			shape.draw(g);
		}
	}

	@Override
	public void changeBorderColor(Color color) {
		for (CollidingShape shape : shapes) {
			shape.changeBorderColor(color);
		}
	}

	@Override
	public void changeFillColor(Color color) {
		for (CollidingShape shape : shapes) {
			shape.changeFillColor(color);
		}
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
		for (CollidingShape shape : shapes) {
			points.addAll(shape.getPoints());
		}
		return points;
	}

	@Override
	public void updatePoints(Vec2f translation) {
		for (CollidingShape shape : shapes) {
			shape.updatePoints(translation);
		}
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
