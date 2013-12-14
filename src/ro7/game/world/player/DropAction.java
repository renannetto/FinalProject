package ro7.game.world.player;

import java.util.Map;
import java.util.Set;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.game.world.items.Item;

public class DropAction extends Action {
	
	private Set<Item> inventory;
	private Item item;
	private boolean collided;

	public DropAction(GameWorld world, CollidingShape shape, String name,
			Map<String, String> properties, Player player, Set<Item> inventory, Item item) {
		super(world, shape, name, properties, player, inventory);
		
		this.inventory = inventory;
		this.item = item;
		this.collided = false;
	}
	
	@Override
	public void onCollision(Collision collision) {
		collided = true;
		super.onCollision(collision);
	}
	
	@Override
	public void onCollisionStatic(Collision collision) {
		collided = true;
		super.onCollisionStatic(collision);
	}
	
	@Override
	public void onCollisionDynamic(Collision collision) {
		collided = true;
		super.onCollisionDynamic(collision);
	}
	
	@Override
	public void remove() {
		super.remove();
		if (!collided) {
			world.addEntity(item);
			item.moveTo(shape.getPosition());
			inventory.remove(item);
			player.dropItem(item);
		}
	}

}
