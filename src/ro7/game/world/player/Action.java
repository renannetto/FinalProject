package ro7.game.world.player;

import java.awt.Graphics2D;
import java.util.Map;
import java.util.Set;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.game.world.entities.FinalCollidableEntity;
import ro7.game.world.entities.FinalEntity;

public class Action extends FinalCollidableEntity implements FinalEntity {
	
	private final float ACTION_TIME = 0.1f;
	
	private Player player;
	private Set<Item> inventory;
	
	private float elapsedTime;

	public Action(GameWorld world, CollidingShape shape, String name,
			Map<String, String> properties, Player player, Set<Item> inventory) {
		super(world, shape, name, properties);
		
		this.player = player;
		this.inventory = inventory;
		
		elapsedTime = 0.0f;
	}

	@Override
	public void onCollision(Collision collision) {
		FinalEntity otherEntity = (FinalEntity) collision.other;
		otherEntity.receiveAction(new Collision(this, collision.mtv, collision.otherShape, collision.thisShape), inventory);
		world.removeEntity(name);
	}

	@Override
	public void onCollisionDynamic(Collision collision) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCollisionStatic(Collision collision) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void draw(Graphics2D g) {
		
	}

	@Override
	public void update(long nanoseconds) {
		elapsedTime += nanoseconds / 1000000000.0f;
		if (elapsedTime > ACTION_TIME) {
			world.removeEntity(name);
		}
	}

	@Override
	public void getItem(Item item) {
		player.getItem(item);
	}

}
