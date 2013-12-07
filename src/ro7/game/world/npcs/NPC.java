package ro7.game.world.npcs;

import java.util.Map;
import java.util.Set;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.engine.world.io.Input;
import ro7.game.screens.GameScreen;
import ro7.game.world.entities.FinalEntity;
import ro7.game.world.entities.FinalStaticEntity;
import ro7.game.world.items.Item;

public class NPC extends FinalStaticEntity implements FinalEntity {

	private final float CUTSCENE_DELAY = 1.0f;

	private String cutscene;

	private float elapsedTime;

	public NPC(GameWorld world, CollidingShape shape, String name,
			Map<String, String> properties) {
		super(world, shape, name, properties);
		cutscene = properties.get("cutscene");

		elapsedTime = CUTSCENE_DELAY;
		
		inputs.put("doPlayCutscene", new Input() {
			
			@Override
			public void run(Map<String, String> args) {
				GameScreen.playCutscene(cutscene);
			}
		});
	}

	@Override
	public void receiveAction(Collision collision, Set<Item> inventory) {
		if (elapsedTime >= CUTSCENE_DELAY) {
			GameScreen.playCutscene(cutscene);
			elapsedTime = 0.0f;
		}
	}

	@Override
	public void receiveAttack(Collision collision) {
		receiveAction(collision, null);
	}

	@Override
	public void update(long nanoseconds) {
		elapsedTime += nanoseconds / 1000000000.0f;
		shape.update(nanoseconds);
	}

}
