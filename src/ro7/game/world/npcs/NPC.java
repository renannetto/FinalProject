package ro7.game.world.npcs;

import java.util.Map;
import java.util.Set;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.engine.world.entities.StaticEntity;
import ro7.game.screens.GameScreen;
import ro7.game.world.FinalEntity;
import ro7.game.world.player.Item;

public class NPC extends StaticEntity implements FinalEntity {
	
	private String cutscene;

	public NPC(GameWorld world, CollidingShape shape, String name,
			Map<String, String> properties) {
		super(world, shape, name, properties);
		cutscene = properties.get("cutscene");
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
		GameScreen.playCutscene(cutscene);
	}
	
	@Override
	public void getItem(Item item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(long nanoseconds) {
		// TODO Auto-generated method stub
		
	}

}
