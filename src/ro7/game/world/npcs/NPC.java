package ro7.game.world.npcs;

import java.util.Map;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.engine.world.entities.StaticEntity;
import ro7.game.screens.GameScreen;
import ro7.game.world.FinalEntity;

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
	public void receiveAction() {
		GameScreen.playCutscene(cutscene);
	}

	@Override
	public void update(long nanoseconds) {
		// TODO Auto-generated method stub
		
	}

}
