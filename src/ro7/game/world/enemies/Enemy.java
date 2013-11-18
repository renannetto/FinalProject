package ro7.game.world.enemies;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ro7.engine.ai.Composite;
import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.game.world.Character;
import ro7.game.world.Player;
import cs195n.Vec2f;

public abstract class Enemy extends Character {

	protected float actionRadius;
	protected List<Vec2f> path;
	protected Composite root;

	protected Enemy(GameWorld world, CollidingShape shape, String name,
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
				}
			}
			Vec2f newDirection = currentTarget.minus(currentPosition);
			stop(this.direction);
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

}
