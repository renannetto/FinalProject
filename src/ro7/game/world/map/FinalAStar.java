package ro7.game.world.map;

import ro7.engine.ai.AStar;
import ro7.engine.util.Graph;
import ro7.engine.util.Node;

public class FinalAStar extends AStar<FinalNode> {

	public FinalAStar(Graph<FinalNode> graph) {
		super(graph);
	}

	@Override
	public float heuristic(Node node, Node end) {
		FinalNode finalNode = (FinalNode) node;
		FinalNode endFinalNode = (FinalNode) end;
		return finalNode.distance(endFinalNode);
	}

}
