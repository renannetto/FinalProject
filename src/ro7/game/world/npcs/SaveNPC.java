package ro7.game.world.npcs;

import java.util.Map;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.GameWorld;
import ro7.game.world.FinalWorld;

public class SaveNPC extends NPC {

	public SaveNPC(GameWorld world, CollidingShape shape, String name,
			Map<String, String> properties) {
		super(world, shape, name, properties);
	}
	
	@Override
	public void receiveAction() {
		super.receiveAction();
		((FinalWorld)world).save();
	}

}
