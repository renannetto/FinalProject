package ro7.game.world.player;

import java.util.Map;
import java.util.Set;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.GameWorld;
import ro7.game.world.items.GameItem;
import ro7.game.world.items.Item;

public class DropAction extends Action {

	public DropAction(GameWorld world, CollidingShape shape, String name,
			Map<String, String> properties, Player player, Set<Item> inventory, String gameItemName) {
		super(world, shape, name, properties, player, inventory);
		
		GameItem gameItem = (GameItem)world.getEntity(gameItemName);
		gameItem.moveTo(shape.getPosition());
		inventory.remove(gameItem.getItem());
	}

}
