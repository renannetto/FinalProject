package ro7.game.world.map;

import ro7.engine.ai.AStar;
import ro7.engine.util.Graph;

public class FinalAStar extends AStar<FinalNode> {

	public FinalAStar(Graph<FinalNode> graph) {
		super(graph);
	}

	@Override
	public float heuristic(FinalNode node, FinalNode end) {
		return node.distance(end);
	}

}
