package ro7.game.world.enemies;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.GameWorld;
import ro7.game.world.Character;
import ro7.game.world.FinalWorld;
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
			List<FinalNode> nodePath = ((FinalWorld)world).pathToPlayer(shape.getPosition());
			for (FinalNode node : nodePath) {
				path.add(node.position);
			}
		} else {
			Vec2f currentPosition = shape.getPosition();
			Vec2f currentTarget = path.get(0);
			if (currentPosition.dot(direction) > currentTarget.dot(direction)) {
				path.remove(0);
				currentTarget = path.get(0);
				Vec2f newDirection = currentTarget.minus(currentPosition);
				move(newDirection.normalized());
			}
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
