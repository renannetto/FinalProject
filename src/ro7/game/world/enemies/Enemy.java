package ro7.game.world.enemies;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ro7.engine.ai.Action;
import ro7.engine.ai.BTNode;
import ro7.engine.ai.Composite;
import ro7.engine.ai.Condition;
import ro7.engine.ai.Selector;
import ro7.engine.ai.Sequence;
import ro7.engine.ai.Status;
import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.game.world.Character;
import ro7.game.world.FinalWorld;
import ro7.game.world.Player;
import ro7.game.world.map.FinalMap;
import ro7.game.world.map.FinalNode;
import cs195n.Vec2f;

public class Enemy extends Character {

//	private final String ATTACK_NAME = "enemyAttack";

	private float actionRadius;
	private List<Vec2f> path;
	private Composite root;

	public Enemy(GameWorld world, CollidingShape shape, String name,
			Map<String, String> properties) {
		super(world, shape, name, properties);

		if (properties.containsKey("actionRadius")) {
			this.actionRadius = Float
					.parseFloat(properties.get("actionRadius"));
		} else {
			this.actionRadius = shape.getDimensions().x;
		}

		this.path = new ArrayList<Vec2f>();

		buildBehaviorTree();
	}

	private void buildBehaviorTree() {
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

	@Override
	public void update(long nanoseconds) {
		super.update(nanoseconds);
		root.update(nanoseconds);

		if (!path.isEmpty()) {
			Vec2f currentPosition = shape.getPosition();
			Vec2f currentTarget = path.get(0);
			if (currentPosition.dot(direction) >= currentTarget.dot(direction)) {
				path.remove(0);
				if (!path.isEmpty()) {
					currentTarget = path.get(0);
				}
			}
			Vec2f newDirection = currentTarget.minus(currentPosition);
			move(newDirection.normalized());
		}
	}

	@Override
	public void onCollision(Collision collision) {
		super.onCollision(collision);
		if (collision.other instanceof Player) {
			Vec2f mtv = collision.mtv;
			Vec2f centerDistance = collision.otherShape.center().minus(
					collision.thisShape.center());
			if (mtv.dot(centerDistance) < 0) {
				mtv = mtv.smult(-1.0f);
			}
			Player player = (Player) collision.other;
			player.receiveDamage(1);
			player.push(mtv);
		}
	}

	@Override
	public void receiveDamage(int damage) {
		super.receiveDamage(damage);
		if (lives <= 0) {
			world.removeEntity(name);
		}
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

//		private final float ATTACK_DELAY = 1.0f;
//
//		private float closeTime = 0.0f;

		@Override
		public void reset() {

		}

		@Override
		public Status act(float nanoseconds) {
			if (!path.isEmpty()) {
				return Status.RUNNING;
			}

//			Vec2f playerPosition = ((FinalWorld) world).getPlayerPosition();
//			float distanceX = Math
//					.abs(playerPosition.x - shape.getPosition().x);
//			float distanceY = Math
//					.abs(playerPosition.y - shape.getPosition().y);
//			if ((distanceX < shape.getDimensions().x * 2 && distanceY < shape
//					.getDimensions().y)
//					|| (distanceY < shape.getDimensions().y * 2 && distanceX < shape
//							.getDimensions().x)) {
//				direction = playerPosition.minus(shape.getPosition())
//						.normalized();
//				if (closeTime > ATTACK_DELAY) {
//					closeTime = 0.0f;
//					Attack enemyAttack = attack(ATTACK_NAME);
//					world.addEntity(enemyAttack);
//					return Status.SUCCESS;
//				} else {
//					closeTime += nanoseconds / 1000000000.0f;
//					return Status.RUNNING;
//				}
//			}

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

			float minimumX = shape.getPosition().x - actionRadius;
			float maximumX = shape.getPosition().x + actionRadius;
			float posX = minimumX + (float) Math.random()
					* (maximumX - minimumX + 1);

			float minimumY = shape.getPosition().y - actionRadius;
			float maximumY = shape.getPosition().y + actionRadius;
			float posY = minimumY + (float) Math.random()
					* (maximumY - minimumY + 1);

			Vec2f targetPosition = new Vec2f(posX, posY);

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
