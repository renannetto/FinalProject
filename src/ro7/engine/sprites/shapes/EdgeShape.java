package ro7.engine.sprites.shapes;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ro7.engine.world.Edge;
import cs195n.Vec2f;

/**
 * @author ro7
 * A shape bounded by edges (AAB or Polygon)
 */
public abstract class EdgeShape extends SingleShape {
	
	protected List<Vec2f> points;

	protected EdgeShape(Vec2f position, Color borderColor, Color fillColor, Vec2f... points) {
		super(position, borderColor, fillColor);
		this.points = new ArrayList<Vec2f>();
		for (Vec2f point : points) {
			this.points.add(point);
		}
	}
	
	protected EdgeShape(Vec2f position, Color borderColor, Color fillColor, List<Vec2f> points) {
		super(position, borderColor, fillColor);
		this.points = new ArrayList<Vec2f>();
		for (Vec2f point : points) {
			this.points.add(point);
		}
	}
	
	/**
	 * @return the set of Edges of the shape
	 */
	public Set<Edge> edges() {
		Set<Edge> edges = new HashSet<Edge>();
		for (int i=0; i<points.size(); i++) {
			Vec2f startPoint = points.get(i);
			Vec2f endPoint;
			if (i<points.size()-1) {
				endPoint = points.get(i+1);
			} else {
				endPoint = points.get(0);
			}
			Edge edge = new Edge(startPoint, endPoint);
			edges.add(edge);
		}
		return edges;
	}
	
	public List<Vec2f> getPoints() {
		return points;
	}

}
