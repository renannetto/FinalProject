package ro7.game.world.enemies;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

	private List<Vec2f> path;

	public Enemy(GameWorld world, CollidingShape shape, String name,
			Map<String, String> properties) {
		super(world, shape, name, properties);

		this.path = new ArrayList<Vec2f>();
	}

	@Override
	public void update(long nanoseconds) {
		super.update(nanoseconds);
		if (path.isEmpty()) {
			List<FinalNode> nodePath = ((FinalWorld) world).pathToPlayer(shape
					.getPosition());
			if (nodePath != null) {
				for (FinalNode node : nodePath) {
					path.add(FinalMap.toWorldCoordinates(node.position));
				}
				direction = path.get(0).minus(shape.getPosition());
				if (direction.mag2() > 0) {
					direction = direction.normalized();
				}
				move(direction);
			}
		} else {
			Vec2f currentPosition = shape.getPosition();
			Vec2f currentTarget = path.get(0);
			if (currentPosition.dot(direction) >= currentTarget.dot(direction)) {
				path.remove(0);
				if (!path.isEmpty()) {
					currentTarget = path.get(0);
					Vec2f newDirection = currentTarget.minus(currentPosition);
					move(newDirection.normalized());
				}
			}
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
			Player player = (Player)collision.other;
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
