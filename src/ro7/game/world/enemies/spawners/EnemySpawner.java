package ro7.game.world.enemies.spawners;

import java.awt.Graphics2D;
import java.util.Map;
import java.util.Set;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.engine.world.entities.Entity;
import ro7.game.world.FinalEntity;
import ro7.game.world.enemies.Enemy;
import ro7.game.world.player.Item;

public abstract class EnemySpawner extends Entity implements FinalEntity {
	
	protected Map<String, String> enemyProperties;
	private float timeToSpawn;
	private float elapsedTime;

	protected EnemySpawner(GameWorld world, CollidingShape shape, String name, Map<String, String> properties) {
		super(world, shape, name);
		
		this.enemyProperties = properties;
		this.timeToSpawn = Float.parseFloat(properties.get("timeToSpawn"));
		this.elapsedTime = 0.0f;
	}

	@Override
	public void update(long nanoseconds) {
		elapsedTime += nanoseconds / 1000000000.0f;
		if (timeToSpawn >= 0 && elapsedTime >= timeToSpawn) {
			spawnEnemy();
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

	@Override
	public void receiveDamage(int damage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void touchEnemy(Collision collision) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receiveAttack(Collision collision) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receiveAction(Collision collision, Set<Item> inventory) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getItem(Item item) {
		// TODO Auto-generated method stub
		
	}

}
