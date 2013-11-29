package ro7.game.world.enemies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ro7.engine.ai.Action;
import ro7.engine.ai.Composite;
import ro7.engine.ai.Condition;
import ro7.engine.ai.Status;
import ro7.engine.sprites.AnimatedSprite;
import ro7.engine.sprites.SpriteSheet;
import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.sprites.shapes.CollidingSprite;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.game.world.Character;
import ro7.game.world.FinalEntity;
import ro7.game.world.FinalWorld;
import ro7.game.world.map.FinalMap;
import ro7.game.world.map.FinalNode;
import ro7.game.world.player.Item;
import cs195n.Vec2f;
import cs195n.Vec2i;

public abstract class Enemy extends Character {

	private final float DEATH_DELAY = 0.8f;

	private float deadTime;

	protected float actionRadius;
	protected List<Vec2f> path;
	protected Composite root;

	private Map<Vec2f, AnimatedSprite> dying;

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

		Vec2i posDown = new Vec2i(Integer.parseInt(properties.get("posDownX")),
				Integer.parseInt(properties.get("posDownY"))).plus(0, 4);
		Vec2i posUp = new Vec2i(Integer.parseInt(properties.get("posUpX")),
				Integer.parseInt(properties.get("posUpY"))).plus(0, 4);
		;
		Vec2i posRight = new Vec2i(
				Integer.parseInt(properties.get("posRightX")),
				Integer.parseInt(properties.get("posRightY"))).plus(0, 4);
		;
		Vec2i posLeft = new Vec2i(Integer.parseInt(properties.get("posLeftX")),
				Integer.parseInt(properties.get("posLeftY"))).plus(0, 4);
		;

		SpriteSheet dyingSheet = world.getSpriteSheet(properties
				.get("walkingSheet"));
		int framesDying = Integer.parseInt(properties.get("framesWalking"));
		float timeToMoveDying = Float.parseFloat(properties
				.get("timeToMoveWalking"));
		dying = new HashMap<Vec2f, AnimatedSprite>();
		dying.put(new Vec2f(0.0f, 1.0f), new AnimatedSprite(
				shape.getPosition(), dyingSheet, posDown, framesDying,
				timeToMoveDying));
		dying.put(new Vec2f(0.0f, -1.0f),
				new AnimatedSprite(shape.getPosition(), dyingSheet, posUp,
						framesDying, timeToMoveDying));
		dying.put(new Vec2f(1.0f, 0.0f), new AnimatedSprite(
				shape.getPosition(), dyingSheet, posRight, framesDying,
				timeToMoveDying));
		dying.put(new Vec2f(-1.0f, 0.0f),
				new AnimatedSprite(shape.getPosition(), dyingSheet, posLeft,
						framesDying, timeToMoveDying));

		buildBehaviorTree();
	}

	protected abstract void buildBehaviorTree();

	@Override
	public void update(long nanoseconds) {
		super.update(nanoseconds);

		if (deadTime >= 0.0f) {
			deadTime += nanoseconds / 1000000000.0f;
			if (deadTime > DEATH_DELAY) {
				world.removeEntity(name);
			}
		} else if (damageTime >= DAMAGE_DELAY) {
			root.update(nanoseconds);
			if (!path.isEmpty()) {
				Vec2f currentPosition = shape.getPosition();
				Vec2f currentTarget = path.get(0);
				if (currentPosition.dot(direction) >= currentTarget
						.dot(direction)) {
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
		}

	}

	@Override
	protected void updateSprite(long nanoseconds) {
		if (deadTime >= 0.0f) {
			if (Math.abs(direction.y) >= Math.abs(direction.x)) {
				if (direction.y > 0) {
					((CollidingSprite) shape).updateSprite(dying.get(new Vec2f(
							0.0f, 1.0f)));
				} else {
					((CollidingSprite) shape).updateSprite(dying.get(new Vec2f(
							0.0f, -1.0f)));
				}
			} else {
				if (direction.x > 0) {
					((CollidingSprite) shape).updateSprite(dying.get(new Vec2f(
							1.0f, 0.0f)));
				} else {
					((CollidingSprite) shape).updateSprite(dying.get(new Vec2f(
							-1.0f, 0.0f)));
				}
			}

			deadTime += nanoseconds / 1000000000.0f;
			if (deadTime > DEATH_DELAY) {
				world.removeEntity(name);
			}

			this.shape.update(nanoseconds);
		} else {
			super.updateSprite(nanoseconds);
		}
	}

	@Override
	public void onCollision(Collision collision) {
		super.onCollision(collision);
		path.clear();
		FinalEntity otherEntity = (FinalEntity) collision.other;
		otherEntity.touchEnemy(new Collision(this, collision.mtv,
				collision.otherShape, collision.thisShape));
	}

	@Override
	public void receiveDamage(int damage) {
		if (damageTime >= DAMAGE_DELAY) {
			super.receiveDamage(damage);
			if (lives <= 0) {
				deadTime = 0.0f;
			}
		}
	}

	@Override
	public void touchEnemy(Collision collision) {

	}

	@Override
	public void receiveAttack(Collision collision) {
		Vec2f mtv = collision.mtv;
		Vec2f centerDistance = collision.thisShape.center().minus(
				collision.otherShape.center());
		if (mtv.dot(centerDistance) < 0) {
			mtv = mtv.smult(-1.0f);
		}
		path.clear();
		push(mtv);
		// move(mtv.normalized());
	}

	@Override
	public void getItem(Item item) {
		// TODO Auto-generated method stub

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
				direction = newDirection;
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
