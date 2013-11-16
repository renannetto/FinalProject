package ro7.game.world.map;

import ro7.engine.util.Node;
import cs195n.Vec2i;

public class FinalNode extends Node {
	
	public final Vec2i position;
	
	public FinalNode(Vec2i position) {
		super();
		this.position = position;
	}
	
	public float distance(FinalNode other) {
		float distX = this.position.x*other.position.x;
		float distY = this.position.y*other.position.y;
		return (float)Math.sqrt(distX + distY);
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
		} else {
			if (!this.position.equals(other.position))
				return false;
		}
		return true;
	}

}
