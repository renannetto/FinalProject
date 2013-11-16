package ro7.game.world.map;

import ro7.engine.util.Node;
import cs195n.Vec2f;

public class FinalNode extends Node {
	
	public final Vec2f position;
	
	public FinalNode(Vec2f position) {
		super();
		this.position = position;
	}
	
	public float distance(FinalNode other) {
		return this.position.dist(other.position);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((position == null) ? 0 : position.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		FinalNode other = (FinalNode) obj;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		return true;
	}

}
