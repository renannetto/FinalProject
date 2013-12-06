package ro7.game.world.enemies;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ro7.engine.ai.Action;
import ro7.engine.ai.BTNode;
import ro7.engine.ai.Composite;
import ro7.engine.ai.Condition;
import ro7.engine.ai.Selector;
import ro7.engine.ai.Sequence;
import ro7.engine.ai.Status;
import ro7.engine.sprites.AnimatedSprite;
import ro7.engine.sprites.SpriteSheet;
import ro7.engine.sprites.shapes.Circle;
import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.sprites.shapes.CollidingSprite;
import ro7.engine.world.GameWorld;
import ro7.engine.world.entities.Ray;
import ro7.game.world.FinalWorld;
import ro7.game.world.map.FinalMap;
import ro7.game.world.map.FinalNode;
import cs195n.Vec2f;
import cs195n.Vec2i;

public class PrisonArcher extends Enemy {

	private final SpriteSheet ARROW_SHEET;
	private final int FRAMES_ARROW;
	private final float TIME_TO_MOVE_ARROW;

	private float detectionRadius;

	public PrisonArcher(GameWorld world, CollidingShape shape, String name,
			Map<String, String> properties) {
		super(world, shape, name, properties);
		if (properties.containsKey("detectionRadius")) {
			this.detectionRadius = Float.parseFloat(properties
					.get("detectionRadius"));
		} else {
			this.detectionRadius = shape.getDimensions().x;
		}

		ARROW_SHEET = world.getSpriteSheet(properties.get("arrowSheet"));
		FRAMES_ARROW = Integer.parseInt(properties.get("framesArrow"));
		TIME_TO_MOVE_ARROW = Float
				.parseFloat(properties.get("timeToMoveArrow"));
	}

	@Override
	public void update(long nanoseconds) {
		super.update(nanoseconds);
	}

	@Override
	protected void buildBehaviorTree() {
		root = new Selector();
		Composite defense = new Sequence();
		Composite attack = new Sequence();

		BTNode playerClose = new PlayerClose();
		BTNode moveAway = new MoveAway();

		BTNode playerInRange = new PlayerInRange();
		BTNode shoot = new Shoot();

		BTNode walk = new Walk();

		defense.addChild(playerClose);
		defense.addChild(moveAway);

		attack.addChild(playerInRange);
		attack.addChild(shoot);

		//root.addChild(defense);
		root.addChild(attack);
		root.addChild(walk);
	}

	private class MoveAway extends Action {

		@Override
		public void reset() {
			// TODO Auto-generated method stub

		}

		@Override
		public Status act(float nanoseconds) {
			if (!path.isEmpty()) {
				return Status.RUNNING;
			}

			Vec2f playerPosition = ((FinalWorld) world).getPlayerPosition();
			Vec2f moveDirection = shape.getPosition().minus(playerPosition)
					.normalized();

			Vec2f targetPosition = shape.getPosition().plus(
					moveDirection.smult(actionRadius));
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

	private class PlayerInRange extends Condition {

		@Override
		public void reset() {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean checkCondition(float nanoseconds) {
			Vec2f playerPosition = ((FinalWorld) world).getPlayerPosition();
			Vec2f arrowDirection = playerPosition.minus(shape.getPosition())
					.normalized();

			Ray arrowRay = new ArrowRay(world, 16, 3, shape.getPosition().plus(
					shape.getDimensions().pmult(arrowDirection)),
					arrowDirection);
			boolean visible = ((FinalWorld) world).collidesPlayer(arrowRay);

			return shape.getPosition().dist(playerPosition) < detectionRadius
					&& visible;
		}

	}

	private class Shoot extends Action {

		private final float SHOOT_DELAY = 3.0f;

		private float elapsedTime = 0.0f;

		@Override
		public void reset() {
			elapsedTime = 0.0f;
		}

		@Override
		public Status act(float nanoseconds) {
			path.clear();
			stop(direction);
			
			if (elapsedTime < SHOOT_DELAY) {
				elapsedTime += nanoseconds / 1000000000.0f;
				return Status.RUNNING;
			}

			elapsedTime = 0.0f;

			Vec2f playerPosition = ((FinalWorld) world).getPlayerPosition();
			Vec2f arrowDirection = playerPosition.minus(shape.getPosition())
					.normalized();

			Map<String, String> arrowProperties = new HashMap<String, String>();
			arrowProperties.put("categoryMask", "8");
			arrowProperties.put("collisionMask", "3");
			arrowProperties.put("targetVelocity", "200");

			AnimatedSprite arrowSprite = new AnimatedSprite(
					shape.getPosition().plus(
							shape.getDimensions().pmult(arrowDirection)), ARROW_SHEET, new Vec2i(0, 0),
					FRAMES_ARROW, TIME_TO_MOVE_ARROW);
			CollidingShape arrowShape = new Circle(shape.getPosition().plus(
					shape.getDimensions().pmult(arrowDirection)), Color.RED,
					Color.RED, 5.0f);
			CollidingSprite arrowColldable = new CollidingSprite(arrowSprite, arrowShape);

			Arrow arrow = new Arrow(world, arrowColldable, name + "Arrow",
					arrowProperties);
			arrow.move(arrowDirection);
			world.addEntity(arrow);

			return Status.SUCCESS;
		}

	}

}
