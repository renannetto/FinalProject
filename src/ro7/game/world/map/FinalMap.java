package ro7.game.world.map;

import java.util.List;

import ro7.engine.util.Graph;

public class FinalMap extends Graph<FinalNode> {
	
	private FinalAStar astar;
	
	public FinalMap() {
		astar = new FinalAStar(this);
	}
	
	public List<FinalNode> shortestPath(FinalNode start, FinalNode end) {
		return astar.shortestPath(start, end);
	}

}
