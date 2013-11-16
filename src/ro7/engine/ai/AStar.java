package ro7.engine.ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import ro7.engine.util.Graph;
import ro7.engine.util.Node;

public abstract class AStar<T extends Node> {

	protected Graph<T> graph;

	protected AStar(Graph<T> graph) {
		this.graph = graph;
	}

	/**
	 * Calculate the shortest path from one node to another
	 * @param start start node on the path
	 * @param end end node on the path
	 * @return a list of nodes with the shortest path or null
	 * if there is no path
	 */
	public List<T> shortestPath(T start, T end) {
		Map<T, T> predecessor = new HashMap<T, T>();
		Set<T> visited = new HashSet<T>();

		Map<T, Float> nodesCost = new HashMap<T, Float>();

		Queue<PathNode<T>> queue = new PriorityQueue<PathNode<T>>();
		queue.add(new PathNode<T>(start, heuristic(start, end)));

		PathNode<T> pathNode = queue.remove();
		visited.add(pathNode.node);
		queue = expandNode(queue, pathNode, predecessor, visited, nodesCost,
				end);
		while (!pathNode.node.equals(end) && queue.size() > 0) {
			pathNode = queue.remove();
			visited.add(pathNode.node);
			queue = expandNode(queue, pathNode, predecessor, visited,
					nodesCost, end);
		}

		if (!pathNode.node.equals(end)) {
			return null;
		}
		return reconstructPath(start, pathNode, predecessor);
	}

	/**
	 * Construct the path found by shortestPath
	 * @param start start node on the path
	 * @param pathNode end node on the path
	 * @param predecessor predecessor mapping
	 * @return list of nodes with the path
	 */
	private List<T> reconstructPath(T start, PathNode<T> pathNode,
			Map<T, T> predecessor) {
		List<T> path = new ArrayList<T>();
		path.add(0, pathNode.node);

		T pre = predecessor.get(pathNode.node);
		while (!pre.equals(start)) {
			path.add(0, pre);
			pre = predecessor.get(pre);
		}
		path.add(0, start);
		return path;
	}

	/**
	 * Get the neighbors of the current node and add them to the queue,
	 * if necessary
	 * @param queue current queue
	 * @param pathNode current visited node
	 * @param predecessor predecessor mapping
	 * @param visited visited nodes
	 * @param nodesCost cost of all the seen nodes
	 * @param end end node of the path
	 * @return the resulting queue
	 */
	private Queue<PathNode<T>> expandNode(Queue<PathNode<T>> queue,
			PathNode<T> pathNode, Map<T, T> predecessor,
			Set<T> visited, Map<T, Float> nodesCost, T end) {
		Map<T, Integer> neighbors = (Map<T, Integer>)pathNode.node.getNeighbors();
		for (Map.Entry<T, Integer> neighbor : neighbors.entrySet()) {
			T neighborNode = neighbor.getKey();
			float cost = pathNode.cost + neighbor.getValue()
					- heuristic(pathNode.node, end);
			PathNode<T> newPathNode = new PathNode<T>(neighborNode, cost
					+ heuristic(neighborNode, end));

			if (!visited.contains(newPathNode.node)) {
				Float nodeCost = nodesCost.get(newPathNode.node);
				if (nodeCost == null) {
					queue.add(newPathNode);
					predecessor.put(newPathNode.node, pathNode.node);
					nodesCost.put(newPathNode.node, cost);
				} else {
					if (cost <= nodeCost) {
						queue.remove(newPathNode);
						queue.add(newPathNode);
						nodesCost.put(newPathNode.node, cost);
						predecessor.put(newPathNode.node, pathNode.node);
					}
				}
			}
		}
		return queue;
	}

	/**
	 * Estimate the cost to get from one node to another
	 * @param node current node
	 * @param end target node
	 * @return the cost from node to end
	 */
	public abstract float heuristic(Node node, Node end);

	private class PathNode<PathT extends Node> implements Comparable<PathNode<PathT>> {

		private PathT node;
		private float cost;

		public PathNode(PathT node, float cost) {
			this.node = node;
			this.cost = cost;
		}

		@Override
		public int compareTo(PathNode<PathT> o) {
			if (cost < o.cost)
				return -1;
			if (cost > o.cost)
				return 1;
			return 0;
		}

		@Override
		public boolean equals(Object obj) {
			if (!obj.getClass().equals(this.getClass())) {
				return false;
			}
			PathNode<PathT> other = (PathNode<PathT>) obj;
			return this.node.equals(other.node);
		}

	}

}
