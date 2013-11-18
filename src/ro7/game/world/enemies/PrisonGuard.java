package ro7.game.world.enemies;

import java.util.List;
import java.util.Map;

import cs195n.Vec2f;
import ro7.engine.ai.Action;
import ro7.engine.ai.BTNode;
import ro7.engine.ai.Composite;
import ro7.engine.ai.Condition;
import ro7.engine.ai.Selector;
import ro7.engine.ai.Sequence;
import ro7.engine.ai.Status;
import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.GameWorld;
import ro7.game.world.FinalWorld;
import ro7.game.world.map.FinalMap;
import ro7.game.world.map.FinalNode;

public class PrisonGuard extends Enemy {

	public PrisonGuard(GameWorld world, CollidingShape shape, String name,
			Map<String, String> properties) {
		super(world, shape, name, properties);
		
		buildBehaviorTree();
	}
	
	@Override
	protected void buildBehaviorTree() {
		root = new Selector();
		Composite attack = new Sequence();

		BTNode closePlayer = new ClosePlayer();
		BTNode attackPlayer = new AttackPlayer();

		BTNode walk = new Walk();

		attack.addChild(closePlayer);
		attack.addChild(attackPlayer);

		root.addChild(attack);
		root.addChild(walk);
	}
	
	private class ClosePlayer extends Condition {

		@Override
		public void reset() {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean checkCondition(float nanoseconds) {
			Vec2f playerPosition = ((FinalWorld) world).getPlayerPosition();
			return playerPosition.dist(shape.getPosition()) <= actionRadius;
		}

	}

	private class AttackPlayer extends Action {

		@Override
		public void reset() {

		}

		@Override
		public Status act(float nanoseconds) {
			if (!path.isEmpty()) {
				return Status.RUNNING;
			}

			List<FinalNode> nodePath = ((FinalWorld) world).pathToPlayer(shape
					.getPosition());
			if (nodePath == null) {
				return Status.FAILURE;
			} else {
				path.clear();
				for (FinalNode node : nodePath) {
					path.add(FinalMap.toWorldCoordinates(node.position));
				}
			}

			return Status.RUNNING;
		}

	}

	private class Walk extends Action {

		@Override
		public void reset() {

		}

		@Override
		public Status act(float nanoseconds) {
			if (!path.isEmpty()) {
				return Status.RUNNING;
			}
			
			Vec2f newDirection = new Vec2f(direction.y, -direction.x);
			Vec2f targetPosition = shape.getPosition().plus(newDirection.smult(actionRadius));

			List<FinalNode> nodePath = ((FinalWorld) world).shortestPath(
					shape.getPosition(), targetPosition);
			
			if (nodePath == null) {
				return Status.FAILURE;
			} else {
				path.clear();
				for (FinalNode node : nodePath) {
					path.add(FinalMap.toWorldCoordinates(node.position));
				}
			}

			return Status.RUNNING;
		}
	}

}
