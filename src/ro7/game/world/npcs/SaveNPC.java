package ro7.game.world.npcs;

import java.util.Map;
import java.util.Set;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.game.world.FinalWorld;
import ro7.game.world.items.Item;

public class SaveNPC extends NPC {

	public SaveNPC(GameWorld world, CollidingShape shape, String name,
			Map<String, String> properties) {
		super(world, shape, name, properties);
	}
	
	@Override
	public void receiveAction(Collision collision, Set<Item> inventory) {
		super.receiveAction(collision, inventory);
		((FinalWorld)world).save();
	}

}
