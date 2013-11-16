package ro7.game.world.map;

import java.util.List;

import ro7.engine.util.Graph;
import ro7.engine.util.Node;

public class FinalMap extends Graph {
	
	private FinalAStar astar;
	
	public FinalMap() {
		astar = new FinalAStar(this);
	}
	
	public List<Node> shortestPath(FinalNode start, FinalNode end) {
		return astar.shortestPath(start, end);
	}

}
