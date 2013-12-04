package ro7.game.world.enemies.spawners;

import java.util.Map;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.GameWorld;
import ro7.game.world.enemies.Enemy;
import ro7.game.world.enemies.PrisonArcher;

public class PrisonArcherSpawner extends EnemySpawner {

	public PrisonArcherSpawner(GameWorld world, CollidingShape shape,
			String name, Map<String, String> properties) {
		super(world, shape, name, properties);
	}

	@Override
	public Enemy createEnemy() {
		return new PrisonArcher(world, shape, name, enemyProperties);
	}

}
