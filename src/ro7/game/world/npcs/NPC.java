package ro7.game.world.npcs;

import java.util.Map;
import java.util.Set;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.game.screens.GameScreen;
import ro7.game.world.entities.FinalEntity;
import ro7.game.world.entities.FinalStaticEntity;
import ro7.game.world.player.Item;

public class NPC extends FinalStaticEntity implements FinalEntity {
	
	private String cutscene;

	public NPC(GameWorld world, CollidingShape shape, String name,
			Map<String, String> properties) {
		super(world, shape, name, properties);
		cutscene = properties.get("cutscene");
	}

	@Override
	public void receiveAction(Collision collision, Set<Item> inventory) {
		GameScreen.playCutscene(cutscene);
	}

	@Override
	public void update(long nanoseconds) {
		shape.update(nanoseconds);
	}

}
