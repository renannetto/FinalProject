package ro7.game.world.enemies;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ro7.engine.ai.Action;
import ro7.engine.ai.Composite;
import ro7.engine.ai.Condition;
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

public abstract class Enemy extends Character {

	private final float DEATH_DELAY = 0.1f;

	private float deadTime;

	protected float actionRadius;
	protected List<Vec2f> path;
	protected Composite root;

	protected Enemy(GameWorld world, CollidingShape shape, String name,
			Map<String, String> properties) {
		super(world, shape, name, properties);

		deadTime = -1.0f;

		if (properties.containsKey("actionRadius")) {
			this.actionRadius = Float
					.parseFloat(properties.get("actionRadius"));
		} else {
			this.actionRadius = shape.getDimensions().x;
		}

		this.path = new ArrayList<Vec2f>();

		buildBehaviorTree();
	}

	protected abstract void buildBehaviorTree();

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
				} else {
					return;
				}
			}
			Vec2f newDirection = currentTarget.minus(currentPosition);
			stop(this.direction);
			move(newDirection.normalized());
		}

		if (deadTime >= 0.0f) {
			deadTime += nanoseconds / 1000000000.0f;
			if (deadTime > DEATH_DELAY) {
				world.removeEntity(name);
			}
		}
	}

	@Override
	public void onCollision(Collision collision) {
		super.onCollision(collision);
		path.clear();
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
	public void onCollisionDynamic(Collision collision) {
		super.onCollisionDynamic(collision);
		path.clear();
	}

	@Override
	public void onCollisionStatic(Collision collision) {
		super.onCollisionStatic(collision);
		path.clear();
	}

	@Override
	public void receiveDamage(int damage) {
		super.receiveDamage(damage);
		if (lives <= 0) {
			deadTime = 0.0f;
		}
	}

	protected class PlayerClose extends Condition {

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

	protected class Walk extends Action {

		@Override
		public void reset() {

		}

		@Override
		public Status act(float nanoseconds) {
			if (!path.isEmpty()) {
				return Status.RUNNING;
			}

			Vec2f newDirection = new Vec2f(direction.y, -direction.x);
			Vec2f targetPosition = shape.getPosition().plus(
					newDirection.smult(actionRadius));

			List<FinalNode> nodePath = ((FinalWorld) world).shortestPath(
					shape.getPosition(), targetPosition);

			if (nodePath == null) {
				stop(direction);
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
