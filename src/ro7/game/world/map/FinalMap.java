package ro7.game.world.map;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ro7.engine.util.Graph;
import cs195n.Vec2f;
import cs195n.Vec2i;

public class FinalMap extends Graph<FinalNode> {
	
	private FinalAStar astar;
	private static Vec2i tileDimensions;
	private Map<Vec2i, FinalNode> nodeMap;
	
	public FinalMap(Vec2i tileDimensions) {
		astar = new FinalAStar(this);
		FinalMap.tileDimensions = tileDimensions;
		nodeMap = new HashMap<Vec2i, FinalNode>();
	}
	
	@Override
	public void addNode(FinalNode node) {
		super.addNode(node);
		nodeMap.put(node.position, node);
	}
	
	public FinalNode getNode(Vec2f position) {
		Vec2i tilePos = toTileCoordinates(position);
		return nodeMap.get(tilePos);
	}
	
	public List<FinalNode> shortestPath(FinalNode start, FinalNode end) {
		return astar.shortestPath(start, end);
	}

	public Vec2i getTileDimensions() {
		return tileDimensions;
	}
	
	public static Vec2i toTileCoordinates(Vec2f position) {
		int gridX = (int) Math.floor((position.x+(tileDimensions.x/2))/tileDimensions.x);
		int gridY = (int) Math.floor((position.y+(tileDimensions.y/2))/tileDimensions.y);
		return new Vec2i(gridX, gridY);
	}
	
	public static Vec2f toWorldCoordinates(Vec2i tilePosition) {
		return new Vec2f(tilePosition.x*tileDimensions.x, tilePosition.y*tileDimensions.y);
	}

}
