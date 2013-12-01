package ro7.game.world.enemies.spawners;

import java.awt.Graphics2D;
import java.util.Map;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.GameWorld;
import ro7.game.world.FinalWorld;
import ro7.game.world.enemies.Enemy;
import ro7.game.world.entities.FinalEntity;
import ro7.game.world.entities.FinalEntityImp;
import ro7.game.world.player.Item;

public abstract class EnemySpawner extends FinalEntityImp implements
		FinalEntity {

	protected Map<String, String> enemyProperties;
	private float timeToSpawn;
	private float elapsedTime;
	private Item trigger;

	protected EnemySpawner(GameWorld world, CollidingShape shape, String name,
			Map<String, String> properties) {
		super(world, shape, name);

		this.enemyProperties = properties;
		if (properties.containsKey("timeToSpawn")) {
			this.timeToSpawn = Float.parseFloat(properties.get("timeToSpawn"));
		} else {
			this.timeToSpawn = -1;
		}
		this.elapsedTime = 0.0f;
		
		if (properties.containsKey("trigger")) {
			this.trigger = new Item(world, null, properties.get("trigger"));
		} else {
			this.trigger = null;
		}
	}

	@Override
	public void update(long nanoseconds) {
		elapsedTime += nanoseconds / 1000000000.0f;
		if (timeToSpawn >= 0 && elapsedTime >= timeToSpawn) {
			spawnEnemy();
		}
		if (trigger != null && ((FinalWorld)world).playerHas(trigger)) {
			spawnEnemy();
			trigger = null;
		}
	}

	public void spawnEnemy() {
		Enemy enemy = createEnemy();
		world.addEntity(enemy);
	}

	public abstract Enemy createEnemy();

	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}

}
