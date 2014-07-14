package ro7.engine.world.components.colliders;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ro7.engine.world.Edge;
import ro7.engine.world.Entity;
import ro7.engine.world.components.Collider;
import cs195n.Vec2f;

/**
 * @author ro7
 * A shape bounded by edges (AAB or Polygon)
 */
public abstract class EdgeShape extends Collider {

	protected EdgeShape(Entity entity) {
		super(entity);
	}
	
	protected EdgeShape(Entity entity, List<Vec2f> points) {
		super(entity);
	}
	
	/**
	 * @return the set of Edges of the shape
	 */
	public Set<Edge> edges() {
		List<Vec2f> points = getPoints();
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

}
