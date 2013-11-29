package ro7.game.world.enemies.spawners;

import java.util.Map;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.GameWorld;
import ro7.game.world.enemies.Enemy;
import ro7.game.world.enemies.PrisonGuard;

public class PrisonGuardSpawner extends EnemySpawner {

	public PrisonGuardSpawner(GameWorld world, CollidingShape shape,
			String name, Map<String, String> properties) {
		super(world, shape, name, properties);
	}

	@Override
	public Enemy createEnemy() {
		return new PrisonGuard(world, shape, name, enemyProperties);
	}

}
