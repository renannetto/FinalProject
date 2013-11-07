package ro7.engine.world;

import cs195n.Vec2f;

public class Edge {
	
	public final Vec2f pointA;
	public final Vec2f pointB;
	public final Vec2f m;
	public final Vec2f n;
	
	public Edge(Vec2f pointA, Vec2f pointB) {
		this.pointA = pointA;
		this.pointB = pointB;
		m = pointA.minus(pointB).normalized();
		n = new Vec2f(m.y, -m.x);
	}

}
